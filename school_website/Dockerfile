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

# Inform Docker that the container uses the dynamic PORT environment variable
EXPOSE ${PORT}

# Run the application and force Spring Boot to use the Railway-assigned PORT
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]