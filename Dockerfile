FROM openjdk:17
VOLUME /tmp
COPY build/libs/*.jar app-1.0.0.jar
ENTRYPOINT ["java","-jar","/app-1.0.0.jar"]