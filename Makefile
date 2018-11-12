.PHONY: all clean build run receive send

all: run

clean:
	mvn clean

build: clean
	mvn package -Dmaven.test.skip=true

run:
	mvn spring-boot:run

receive: build
	java -jar target/absa-0.0.1.jar --spring.profiles.active=topics,paymentReceiver --tutorial.client.duration=60000

send: build
	java -jar target/absa-0.0.1.jar --spring.profiles.active=topics,sender --tutorial.client.duration=60000
