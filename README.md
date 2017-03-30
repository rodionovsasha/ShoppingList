# Shopping list spring-boot web application

This is a simple project which is based on **spring-boot 1.4.2** and uses **H2** database, **JPA**, **Thymeleaf** template engine, **Bootstrap** and contains JUnit tests, BDD **Cucumber** tests (**Selenide** + **PhantomJs** driver).

#### Build the application
<pre>
mvn clean install
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

#### Rest API Documentation (Swagger)
<pre>
http://localhost:8000/swagger-ui.html
</pre>

#### Cucumber test results location
<pre>
.../target/cucumber-reports/cucumber-html-reports/overview-features.html
</pre>