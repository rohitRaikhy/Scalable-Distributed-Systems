FROM bellsoft/liberica-openjdk-alpine-musl:11 AS client-build
COPY . ~/Documents/Project_Three
WORKDIR ~/Documents/Project_Three
RUN javac RMI/*.java
RUN javac Server/*.java
RUN javac Client/*.java

# cmd to run server locally - java server.ServerApp 1111 5555
ENTRYPOINT ["java", "Server.StartServer"]
