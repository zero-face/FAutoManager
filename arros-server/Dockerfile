FROM maven:3.8.4-openjdk-11-slim

EXPOSE 4567

COPY target/*.jar /app.jar
CMD ["java","-jar","/app.jar"]