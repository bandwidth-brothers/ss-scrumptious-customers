FROM maven:latest
COPY target/ss-scrumptious-customers-0.0.1-SNAPSHOT.jar /home/customer-backend.jar
ENTRYPOINT java -jar /home/customer-backend.jar
