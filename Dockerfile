# Use Amazon Corretto as the base image
FROM public.ecr.aws/lambda/java:17

# Set the working directory
WORKDIR /app

# Set the Lambda runtime entry point
CMD ["app.jar"]