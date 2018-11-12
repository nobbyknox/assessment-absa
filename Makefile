.PHONY: all clean build run test

all: run

clean:
	mvn clean

build: clean
	mvn package -Dmaven.test.skip=true

run:
	mvn spring-boot:run -Dspring.profiles.active=production

test:
	mvn test -Dspring.profiles.active=test
