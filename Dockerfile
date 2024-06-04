FROM openjdk:17-oracle

ADD /build/libs/Project-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
