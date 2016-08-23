#Shopping list spring-boot web application

This is a simple project which is based on spring-boot and uses H2 database, JPA, Thymeleaf template engine, Twitter Bootstrap and contains JUnit tests, BDD Cucumber tests (Selenide + PhantomJs Driver).

Build with tests: mvn clean install

Running the application: mvn spring-boot:run or simply run the application in your IDE using public static void main.
Open the following URl in browser: http://localhost:8000. Port is configurable in application.properties.

This application uses H2 database and so should run locally. Remote database connections can also be configured in application.properties.