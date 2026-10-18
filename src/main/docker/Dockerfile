FROM eclipse-temurin:25-jre
WORKDIR /app
COPY target/shoppinglist.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
