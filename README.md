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
* Quarkus 1.13.2.Final
* Apache Maven 3.6.3
* JUnit 5
* PostgreSQL 12
* MapStruct 1.4.1.Final
* Flyway 6.5.7

## How to use it

### GIT Repository

* Run the following command in order to clone the project to your local machine:
```shell script
git clone git@github.com:itiburski/aps-quarkus-api.git
```

### PostgreSQL Database

#### PostgreSQL installation

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

#### PostgreSQL in Docker

* If you have a Docker environment, just run the docker-compose script to start a PostgreSQL Docker container:
```shell script
docker-compose up -d
```

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```


## Endpoints and documentation

By default, the API will be available at http://localhost:8080/

The Swagger documentation can be found at http://localhost:8080/swagger-ui/


## License

This API is licensed under the MIT License 

