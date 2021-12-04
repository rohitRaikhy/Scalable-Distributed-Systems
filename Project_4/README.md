# Project 4 - PAXOS

## Commands to run docker:
1. chmod +x start.sh 
2. ./start.sh - creates the docker image for the server, builds 3 docker containers and runs them in background. 
3. docker ps - check to see if the docker containers are running 
4. docker run -p 3000:3000 -ti --rm client host.docker.internal 3001 s1

## Image of client

![Screen Shot 2021-12-04 at 10 59 03 AM](https://user-images.githubusercontent.com/35156624/144716134-8f31a060-3383-48ab-bdb1-f959a2f4db35.png)

## Image of server running in foreground

![Screen Shot 2021-12-04 at 10 58 36 AM](https://user-images.githubusercontent.com/35156624/144716114-22a437b6-5f80-4e73-b27b-b4b1c205127f.png)

## To run locally
rmiregistry

javac Servers/StartServers.java

java Servers/StartServers 3001 s1 localhost 196 3001,3002,3003 s1,s2,s3

javac Client/Client.java

java Client/Client localhost 3001 s1
