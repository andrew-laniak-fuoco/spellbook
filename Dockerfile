#
# Build stage
#
FROM maven:3.6-jdk-8-slim AS build
COPY src /build/src
COPY pom.xml /build/
RUN mvn -f /build/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:8-alpine
COPY --from=build /build/target/spell-book-1.0-SNAPSHOT.jar /usr/local/app/trading/lib/spell-book.jar
ENTRYPOINT ["java","-jar","/usr/local/app/trading/lib/spell-book.jar"]
