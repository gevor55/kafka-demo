###Demo Kafka Application

**This is a demo Kafka application that demonstrates the integration of Kafka messaging system with a Spring Boot application.**

**Introduction**

This application showcases the usage of Apache Kafka for asynchronous messaging between different components of a Spring Boot application. It includes producers that publish messages to Kafka topics and consumers that consume these messages and process them accordingly.

**Prerequisites**

Before running this application, make sure you have the following prerequisites installed:
- Java17 or higher
- Apache Kafka (Make sure Kafka server is up and running)

**Setup**

Clone the Repository:

```shell
git clone <repository_url>
```
**Build the Project:**

```shell
cd demo-kafka
mvn clean install
```
**Configuration**:

Start Kafka Server: Ensure your Kafka server is up and running.

**Running the Application**
Once the setup is complete, you can run the application using the following command:

```shell
mvn spring-boot:run
```
