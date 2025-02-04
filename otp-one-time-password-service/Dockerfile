# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Copy the application JAR file to the container
COPY target/otp-service-1.0.0.jar /otp-one-time-password-service/otp-service-1.0.0.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/otp-one-time-password-service/otp-service-1.0.0.jar"]

#docker build -t venkydasari/otp .
#docker push venkydasari/otp