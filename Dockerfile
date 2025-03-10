# Use Amazon Corretto as the base image
FROM public.ecr.aws/lambda/java:17

# Set the working directory
WORKDIR /app

# Copy Jar file
COPY target/*.jar app.jar

# Set the Lambda runtime entry point
CMD ["java", "-cp", "app.jar", "org.springframework.boot.loader.JarLauncher"]