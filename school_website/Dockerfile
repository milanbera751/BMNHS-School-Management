# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy pom.xml and source code first (leveraging Docker cache for dependencies)
COPY pom.xml .
COPY src ./src

# Build the jar and skip tests to save time and resources on Render
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar file from the build stage
# Note: Using target/school-management.jar (or your actual jar name) is safer,
# but target/*.jar works if there is only one jar produced.
COPY --from=build /app/target/*.jar app.jar

# Expose the port Spring Boot runs on (Render defaults to 8080 or uses the PORT env variable)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]