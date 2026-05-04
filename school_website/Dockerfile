# Build Stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Copy everything from the current directory to /app in the container
COPY . .
# Build the jar
RUN mvn clean package -DskipTests

# Run Stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]