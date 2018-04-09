[![Build Status](https://travis-ci.org/rodionovsasha/ShoppingList.svg?branch=master)](https://travis-ci.org/rodionovsasha/ShoppingList)
[![Coverage Status](https://coveralls.io/repos/github/rodionovsasha/ShoppingList/badge.svg?branch=master)](https://coveralls.io/github/rodionovsasha/ShoppingList?branch=master)

# Shopping list spring-boot web application

This is a simple project which is based on **spring-boot 2.0.0.RELEASE** and uses **H2** database, **JPA**, **Thymeleaf** template engine, **Bootstrap** and contains JUnit tests, BDD **Cucumber** tests (**Selenide** + **Chrome** headless web driver) and **Docker**.

#### Build the application
<pre>
mvn clean install
</pre>
or using wrapper
<pre>
./mvnw clean install
</pre>
##### Skip running tests
<pre>
mvn clean install -DskipTests
</pre>
##### Add running docker
<pre>
mvn clean install -DskipDocker=false
</pre>

#### Run the application
<pre>
mvn spring-boot:run
</pre>
or simply run the application in your IDE using public static void main method.

Open the following URL in browser:
<pre>
http://localhost:8000
</pre>
Port is configurable in application.properties.

This application uses H2 database and so should run locally.
Remote database connections can also be configured in application.properties.

#### Run the application with docker
<pre>
docker-compose up
</pre>

#### Rest JSON API v1 Documentation (with swagger)
<pre>
http://localhost:8000/v1/api
http://localhost:8000/swagger-ui.html
</pre>
