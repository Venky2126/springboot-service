# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /otp-one-time-password-service

# Copy the application JAR file to the container
COPY target/otp-one-time-password-service-*.jar /otp-one-time-password-service/otp-one-time-password-service.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/otp-one-time-password-service/otp-one-time-password-service.jar"]

#docker build -t venkydasari/otp .
#docker push venkydasari/otp