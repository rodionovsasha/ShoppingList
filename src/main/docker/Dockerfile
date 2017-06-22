FROM openjdk:8-jre-alpine
VOLUME /tmp/docker
ADD *.jar shoppinglist.jar
#You can use a RUN command to "touch" the jar file so that it has a file modification time (Docker creates all container files in an "unmodified" state by default).
#This actually isn’t important for the simple apps, but any static content (e.g. "index.html") would require the file to have a modification time.
RUN sh -c 'touch /shoppinglist.jar'
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java -jar /shoppinglist.jar -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS"]