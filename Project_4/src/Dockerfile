FROM bellsoft/liberica-openjdk-alpine-musl:11 AS client-build
COPY . ~/Documents/Project_Four
WORKDIR ~/Documents/Project_Four
RUN javac KeyValueStore/*.java
RUN javac Servers/IServer.java
RUN javac PAXOS/*.java
RUN javac Servers/*.java
RUN javac Client/*.java

# cmd to run server locally - java server.ServerApp 1111 5555
ENTRYPOINT ["java", "Servers.StartServers"]
