#
# Build
#
FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
#
# Package stage
#
FROM openjdk:17-alpine3.14
ARG JAR_FILE=*.jar
COPY --from=build /home/app/target/${JAR_FILE} /usr/local/lib/app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /usr/local/lib/app.jar"]