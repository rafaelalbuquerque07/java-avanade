# ===== Stage 1: Build com Gradle =====
FROM gradle:jdk17 AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos do projeto
COPY build.gradle settings.gradle ./
COPY src ./src

# Build da aplicação (pulando testes para agilizar o build em CI/CD)
RUN gradle build --no-daemon -x test

# ===== Stage 2: Runtime com JRE mínimo =====
FROM eclipse-temurin:17-jre-alpine

# Configurar variáveis de ambiente
ENV SPRING_PROFILES_ACTIVE=prod

# Criar diretório de trabalho
WORKDIR /app

# Copiar o JAR criado na etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Configurar ponto de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]

# Metadata
LABEL maintainer="Seu Nome <seu.email@example.com>"
LABEL version="1.0"
LABEL description="Eco-Diapers API - Sistema de Venda de Fraldas Ecológicas com Afiliados"