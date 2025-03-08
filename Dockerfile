# Use Amazon Corretto as the base image
FROM amazoncorretto:17-alpine

# Set the working directory
WORKDIR /app

# Copy Jar file
COPY target/*.jar app.jar

# Set the Lambda runtime entry point
ENTRYPOINT ["java", "-jar", "app.jar"]