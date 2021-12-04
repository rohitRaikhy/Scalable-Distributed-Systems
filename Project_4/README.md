# Running program Notes

rmiregistry

javac Servers/StartServers.java
java Servers/StartServers 3001 s1 localhost 196 3002,3003 s2,s3

javac Client/Client.java
java Client/Client localhost 3001 s1
