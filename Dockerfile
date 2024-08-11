FROM eclipse-temurin:22-jdk

# INFORMAR EL PUERTO DONDE SE EJECUTA EL CONTENEDOR (INFORMATIVO)
EXPOSE 8080

# DEFINIR DIRECTORIO RAIZ DE NUESTRO CONTENEDOR
WORKDIR /root

# COPIAR Y PEGAR ARCHIVOS DENTRO DEL CONTENEDOR
COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root

# DESCARGAR LAS DEPENDENCIAS
RUN ./mvnw dependency:go-offline

# COPIAR EL CODIGO FUENTE DENTRO DEL CONTENEDOR
COPY ./src /root/src

# CONSTRUIR NUESTRA APLICACION
RUN ./mvnw clean install -DskipTests

# LEVANTAR NUESTRA APLICACION CUANDO EL CONTENEDOR INICIE
ENTRYPOINT ["java","-jar","/root/target/travel-agency-manager-API-0.0.1-SNAPSHOT.jar"]






#FROM maven:3.8.6 AS build
#WORKDIR /app
#COPY pom.xml /app
#RUN mvn dependecy:resolve
#COPY . /app
#RUN mvn clean
#RUN mvn package -DskiptTests

#FROM eclipse-temurin:22-jdk
#COPY --from=build /app/target/*.jar app.jar
#EXPOSE 8080
#CMD ["/bin/sh -c mvn","java","-jar","app.jar"]