# README

This repository serves as my assessment submission to ABSA, as requested on 8 November 2018.

## Preface

I had many questions going into this assessment and I event contacted my recruiter, who put me in touch with ABSA, to ask for clarification. However, I was told that I had to complete the assessment with no further clarification. For the record, I include the questions I requested clarify on below.

### First Section

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

### NB Section

1. Should I only show the XML structure or should I write the XML parser as well?

## Installation

## System Deployment

## Assessment Answers

In this section I answer the questions as they appear on the assessment document, numbered accordingly.

### Section 1

#### 1. Prepare a Spring Boot application with relevant dependencies that consumes the validated message from the queue and run through the process.

blah

#### 2. How will you handle exceptions in the application?

blah

#### 3. Prepare jUnit test cases to validate the input throughout the flow

blah

#### 4. Using Apache Maven, how will you structure your *pom* file ensure all relevant dependencies are included?

blah

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

1. What are the SLAs in terms of:
    - Uptime
    - Fault tolerance
    - Software defect (bug) resolution

### NB Section

#### 1. Define the internal XML document that mirrors the Swift MT101 file

```xml
<?xml version="1.0"?>
<mt101>
    <!-- from :20: -->
    <sender>
        <ref>123456789</ref>
    </sender>

    <!-- from section :28D: -->
    <meta>
        <index>1</index>
        <total>1</total>
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

---

## Dev Notes

### Spring Boot Application
Bootstrap your Spring Boot application with https://start.spring.io and add the following dependencies:

* Web
* RabbitMQ
* JPA
* DevTools
* H2

### RabbitMQ

Use this docker command:

```bash
$ docker run -d --name rabbitmq --restart unless-stopped -p 8080:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=password rabbitmq:3-management
```

Access the management interface on http://34.205.16.148:8080

### Swift MT101

Check out http://www.sepaforcorporates.com/swift-for-corporates/quick-guide-swift-mt101-format/
