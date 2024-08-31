FROM openjdk:8-jdk-alpine
RUN apk add ttf-dejavu
RUN mkdir -p /tmp/logs
VOLUME /tmp
ADD target/edelflex-integration-service-1.0.0.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -Duser.timezone=America/Argentina/Buenos_Aires -Djava.security.egd=file:/dev/./urandom -Djava.awt.headless=true -jar /app.jar" ]