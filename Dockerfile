# Stage 1: Build Java application using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml first (this helps with caching dependencies)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application and build
COPY src ./src
RUN mvn package dependency:copy-dependencies -DincludeScope=runtime -DoutputDirectory=target/dependency -DskipTests

# Stage 2: Use AWS Lambda Java 21 runtime
FROM public.ecr.aws/lambda/java:21

# Ensure the lib directory exists before copying
RUN mkdir -p ${LAMBDA_TASK_ROOT}/lib

# Copy compiled classes and dependencies from the builder stage
COPY --from=builder /app/target/classes ${LAMBDA_TASK_ROOT}
COPY --from=builder /app/target/dependency/* ${LAMBDA_TASK_ROOT}/lib/

# Set the AWS Lambda function handler
CMD ["customer.feedback.com.AWSHandler::handleRequest"]
