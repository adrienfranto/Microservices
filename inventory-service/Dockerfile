# -------------------------------
# Étape 1 : build avec Maven et JDK
# -------------------------------
FROM maven:3.9.2-eclipse-temurin-21 AS build

WORKDIR /app

# Copier tous les fichiers du projet
COPY . .

# Donner la permission au wrapper Maven (si tu l’utilises)
RUN chmod +x mvnw

# Compiler le projet et générer le JAR (skip tests pour gagner du temps)
RUN ./mvnw clean package -DskipTests

# -------------------------------
# Étape 2 : image runtime légère
# -------------------------------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copier le JAR généré depuis l'étape de build
COPY --from=build /app/target/inventory-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]
