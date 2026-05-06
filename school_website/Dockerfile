# ==========================================
# Stage 1: Build the application using Maven
# ==========================================
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Compile and package the application into a JAR file
# skipping tests to speed up the Railway build process
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Run the application using JRE 17
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR file from the build stage
# Using *.jar safely copies the generated executable archive
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 (standard for Spring Boot)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]