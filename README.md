# aps-quarkus-api

It's an API for Provision of Service Automation. It is built with Java and Quarkus. It's a toy-project, I code on it on my spare time.

APS stands for "Automação de Prestação de Serviço" in portuguese.


## Features

This API provides HTTP endpoint's for the following actions:

* List/Create/Update/Delete Categorias de Cliente
* List/Create/Update/Delete Cidades
* List/Create/Update/Delete Tipos de Telefone
* List/Create/Update/Delete Clientes

## Technologies

This project was developed with following technologies:

* Java 11 (OpenJDK Runtime Environment (build 11.0.9+11))
* Quarkus 1.8.3.Final
* Apache Maven 3.6.3
* JUnit 5
* PostgreSQL 12
* MapStruct 1.4.1.Final
* Flyway 6.5.7

## How to use it

### Setup

* Run the following command in order to clone the project to your local machine:
```shell script
git clone git@github.com:itiburski/aps-quarkus-api.git
```

* You need to have PostgreSQL database installed and a database named "aps" to run the application.
```shell script
CREATE database aps;
```

* Open project in your favorite editor and change application.properties file to point to your PostgreSQL database
```java
quarkus.datasource.username = your-username
quarkus.datasource.password = your-password
quarkus.datasource.jdbc.url = jdbc:postgresql://localhost:5432/aps
```

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

### Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `aps-quarkus-api-0.1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.
If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package build -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/aps-quarkus-api-0.1.0-SNAPSHOT-runner.jar`.

### Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/aps-quarkus-api-0.1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.


## Endpoints and documentation

By default, the API will be available at http://localhost:8080/

The Swagger documentation can be found at http://localhost:8080/swagger-ui/


## License

This API is licensed under the MIT License 

