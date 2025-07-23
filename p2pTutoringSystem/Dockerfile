# === Build stage ===
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# Set working dir
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# === Runtime stage ===
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
