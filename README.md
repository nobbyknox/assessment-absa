# README

This repository serves as my assessment submission to ABSA, as requested on 8 November 2018.

## Preface

I had many questions going into this assessment and I even contacted my recruiter to ask for clarification. However, I was told that I had to complete the assessment with no further clarification. For the record, I include the questions I requested clarity on, below.

**First Section**

1. Regarding point #1, should I only process the task "Receive Validated Payment Message" in a new Spring Boot application or should I implement the entire workflow in this new application?

1. Should my assessment submission be a fully working application, complete with message queues and sample messages flowing through the entire flow diagram?

1. Must I implement a working message queue server? If so, which one do you prefer? Can it be run in a Docker container?

1. Must I implement a REST web service that will supply the settlement engine details?

1. Must I implement a sample database that will house the routing rules? If so, which database do you prefer? H2, perhaps? Can I design my own simplified schema?

1. What form should my submission take? Would a GitHub repository be acceptable?

1. Regarding point #3. The initial message is in format MT101, correct? Can you please provide details as to the format and structure of all messages that will flow through the system? I am aware that I need to define the internal XML structure, but it is not clear if this internal XML message will flow through the entire system or if different MQ consumers will expect different messages. Can you please clarify?

1. How long does it normally take to complete this assessment? The answer will give me an indication as to the complexity and scope of this assessment.

1. Will the assessor expect a deployable artifact with the intention to run my project or will he/she merely review my submission? It is not entirely clear what the scope of this assessment is.

1. Regarding point #5. Can I include my list of questions to the System Analyst as part of my submission, maybe include them in the README file of a GitHub repository along with the rest of the project?

**NB Section**

1. Should I only show the XML structure or should I write the XML parser as well?

## Installation

Installation is really easy. Please follow along below:

```bash
# clone the git repository
$ git clone git@github.com:nobbyknox/assessment-absa.git
```

## Run the Application

All the usual `maven` commands will work. A `Makefile` has also been provided for your convenience. Below is a table with comparable `maven` and `make` commands:

| Action                | Make       | Maven                                                   |
| :-------------------- | :--------- | :------------------------------------------------------ |
| Clean build artifacts | make clean | mvn clean                                               |
| Compile source        | make build | mvn compile                                             |
| Package jars          | make build | mvn package -Dmaven.test.skip=true                      |
| Run application       | make run   | mvn spring-boot:run -Dspring.profiles.active=production |
| Run unit tests        | make test  | mvn test -Dspring.profiles.active=test                  |

Run the application either with `make run` or `mvn spring-boot:run -Dspring.profiles.active=production`. Once the application has started, it will initiate the workflow by placing the initial MT101 message unto the queue. The message will be picked up, validated, processed and again placed on the queue where it will flow through the rest of the process.

> NOTE: The application connects to a RabbitMQ server running on an Amazon EC2 instance in the North Virginia region, so please bear with some sluggishness when running the application. The reason for hosting a RabbitMQ server is so that you don't have to worry about it. The application will *just* work out of the box.

## Database Console

When the application is running (with `make run` or maven equivalent), a H2 database console is exposed on http://127.0.0.1:8080/console

Use the following details to enter the console:

| Field        | Value            |
| :----------- | :--------------- |
| Driver Class | org.h2.Driver    |
| JDBC URL     | jdbc:h2:mem:absa |
| User Name    | sa               |
| Password     |                  |

## RabbitMQ Console

The RabbitMQ console can be accessed on http://34.205.16.148:8080 with the username "user" and password "password".

## Assessment Answers

In this section I answer the questions as they appear on the assessment document, numbered accordingly.

### Section 1

#### 1. Prepare a Spring Boot application with relevant dependencies that consumes the validated message from the queue and run through the process.

This submission contains four queue receivers which pass their payloads on to dedicated services that validate and process the messages. The services contain some logic, but this submission does not dive too deep into the details. Mostly for the reason that the business rules have not been specified.

#### 2. How will you handle exceptions in the application?

There are a few approaches to exception handling which I'll cover briefly.

The first is a matter of programming style where the entry point to an action, be it REST controller or message listener, is responsible for the delegation of work to services or other workers, as well as for catching and prober handling of any exceptions that might be thrown (checked or unchecked) by said workers.

The second approach covers the build-in exception handing tooling provided by the Spring Boot framework itself that deals with exceptions on a global level. For REST controllers, use a `Controller Advice` class to intercept and handle exceptions. This submission does just this by implementing its own [CustomResponseExceptionHandler](https://github.com/nobbyknox/assessment-absa/blob/master/src/main/java/com/nobbyknox/absa/controllers/CustomResponseExceptionHandler.java)

For the record, you might also implement a REST endpoint on the path `/error`. This will allow you greater control over the response that will be presented to the user.

#### 3. Prepare JUnit test cases to validate the input throughout the flow

Unit tests have been provided that test each queue receiver and associated service. Please see the unit test source files for details.

#### 4. Using Apache Maven, how will you structure your *pom* file to ensure all relevant dependencies are included?

I mostly start with https://start.spring.io where I specify the main dependencies along with some other details. The site then generates a skeleton application that is used as the base for the project. [View the project's pom.xml file](https://github.com/nobbyknox/assessment-absa/blob/master/pom.xml)

Additionally, the site https://mvnrepository.com/ provides useful snippets for adding additional dependencies to your `pom.xml`.

#### 5. What questions will you ask the System Analyst to ensure these requirements are clear and concise?

My questions to the System Analyst are:

1. When will the project start?

1. What is the deadline of the project?

1. Who are the stakeholders and what are their responsibilities?

1. What is the budget for the project (if I'm authorized to know such details)?

1. How many resources will be allocated to the project (number of developers and testers)?

1. What message queue (MQ) implementation is used?
    - Is it hosted on-site, on premise of business partner or in the cloud?

1. Will the system integrate with external or 3rd party systems? If so:
    - Define all integration points
    - For each integration point:
        * Define integration mechanism (message queue, TCP socket, REST endpoint, etc.)
        * Format and structure of message. Please provide sample message.
        * Error handling procedure, along with error codes in case of HTTP REST endpoints.

1. What are the message formats for:
    - `Retrieve Payment Message Status`
    - `Routing Rules Service` REST endpoint:
        * Define input message format and structure
        * Define output message format, structure and HTTP response codes

    - `Send Payment to Settlement Engine`
    - `Target Settlement Engine Adaptor with AckNak`

1. There is no error handling indicated on the flow diagram. Please provide details of how every step in the process should handle errors and how process flow might be affected.

1. How will the initial message arrive on the queue?

1. To what and where will the final MT195 message be sent?

1. What are the performance requirements in terms of:
    - Speed/throughput for each incoming message?
    - Load on the system? How many incoming messages is expected per day, hour, minute?

1. What are the security considerations in terms of:
    - Authentication
    - Authorization
    - Service accessibility (network demarcation)

1. What are the deployment requirements in terms of:
    - Straight deployment on bare metal, ie. `java -jar TheAwesomePaymentProcessor.jar`
    - Virtualized environment such as, Kubernetes, Amazon AWS, straight Docker or other

1. How will the health of the system be monitored?

1. How will the performance of the system be monitored?

1. How will errors be surfaced and brought to the attention of the responsible parties?

1. What are the SLAs in terms of:
    - Uptime
    - Fault tolerance
    - Software defect (bug) resolution

### NB Section

#### 1. Define the internal XML document that mirrors the Swift MT101 file

The following XML sample file was constructed after studying the Swift MT101 file specification. Element naming and grouping is that of my own making and might have little resemblance to implementations in the real world. However, the file below is serviceable and will get the job done.

```xml
<?xml version="1.0"?>
<mt101>
    <!-- from :20: -->
    <sender>
        <ref>123456789</ref>
    </sender>

    <!-- mostly from section :28D: -->
    <meta>
        <index>1</index>
        <total>1</total>
        <status>approved</status>
        <destination></destination>
    </meta>

    <!-- from :50H: -->
    <ordering-customer>
        <account>/GB12SEPA12341234123412</account>
        <name>Big Corp</name>
        <address-1>123 Victory Lane</address-1>
        <address-2>Johannesburg</address-2>
        <address-3>South Africa</address-3>
    </ordering-customer>

    <!-- from :52A: -->
    <servicing-institution>
        <ref>BANKZA01XXX</ref>

        <!-- from :57A: -->
        <account>BANKZA02XXX</account>
    </servicing-institution>

    <!-- from :30: -->
    <execution-date>181112</execution-date>

    <!-- from :21: -->
    <trans-ref>11FEB2016INV1</trans-ref>

    <!-- from :23E: -->
    <instr-code>URGP</instr-code>

    <!-- from :32B: -->
    <amount>ZAR1500.00</amount>

    <!-- from :59: -->
    <beneficiary>
        <account>/GB12SEPA12341234123498</account>
        <name>Beneficiary name</name>
        <address-1>13 Adderly Street</address-1>
        <address-2>Cape Town</address-2>
        <address-3>South Africa</address-3>
    </beneficiary>

    <!-- from :70: -->
    <remittance>
        <ref>SUPPLIER-INV-REF1</ref>
    </remittance>

    <!-- from :77B: -->
    <regulatory>/BENEFRES/ZA</regulatory>

    <!-- from :71A: -->
    <details-of-change>SHA</details-of-change>

</mt101>
```

## Parting Thoughts

I was unsure about the scope of the assessment and fear that I might have spent too much time on the infrastructural elements. However, the result is that of a working system that connects to a hosted message queue server and in-memory seeded database which makes the project runnable right out of the box.

I trust that this body of work meets with your approval.
