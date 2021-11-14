# Scalable-Distributed-Systems

## Running the key value store using docker on terminal

### Commands to run docker:
1. ./start.sh - creates the docker image for the server, builds 5 docker containers and runs them in background. 
2. docker ps - check to see if the docker containers are running 
3. docker run -p 2000:2000 -ti --rm coord 2000 1001,1002,1003,1004,1005 - run the coordinator in the foreground. Shows failure of PUT request after one server is down.
4. docker run -p 3000:3000 -ti --rm client host.docker.internal 1002 server_two 2000 - run the client program. Changing the port number from 1002 to another between {1001, 1002, 1003, 1004, 1005} will work to connect to another server.

### Issues to be resolved 
1. The coordinator needs to be in this format {2000 1001,1002,1003,1004,1005} for the coordinator port and servers ports. 2000 is the coordinator port number and the others are the servers. The input to the command line must follow this exacy syntax. 
2. host.docker.internal must be used, client connects with local host in the source code. 
3. servers names are hard coded so cannot be changed from the shell script.

### Summary 

The scope of work includes learning of the two phase commit protocol and using the RMI framework in java to achieve RPC. RMI framework is used to perform remote method calls on another machine to send/receive data using client/server/coordinator architecture. Networking, operating systems and RMI was needed for the project. Docker was used to create an image for the server, and 5 server containers are created. Port mapping is used for the containers to communicate with one another using TCP. The server has the ability to handle multi threaded requests, expception handling was needed to complete this scope of work. The key concepts of performing RPC is covered, understanding that RPC can be used to utilize greater resources on remote machine to perform computationally extensive work is realized. The two phase commit protocol is used to create conistency among the 5 replicated servers, if a PUT or DELETE request is given from the client. If a server is down or a request cannot be handled, then a rollback method is called to undo any changes on the distributed system. 

For the future one can use better exception handling when dealing with data requests and better parsing of user data to make the program more robust. This is a introduction to RPC as well as the the two phase commit protocol and in the scope of assignemnt the program may throw some errors. A queue can be used to priortize requests coming into the server from multiple clients. A message service can also be used like message queues through a framework like rabbitmq to post to clients messages needed about the service. 

#### Build docker image and run containers using shell script
![Screen Shot 2021-11-14 at 12 05 48 PM](https://user-images.githubusercontent.com/35156624/141690781-0be22900-702c-4e5b-9812-d763b52a2842.png)
#### Check to see if containers running
![Screen Shot 2021-11-14 at 12 06 17 PM](https://user-images.githubusercontent.com/35156624/141690800-8f2becc9-13b9-4894-84d4-8204693701a0.png)
#### Run the coordinator
![Screen Shot 2021-11-14 at 12 08 09 PM](https://user-images.githubusercontent.com/35156624/141690875-ead17bd7-6b01-4186-b5dc-95f09f4ecbc2.png)
#### Run the client
![Screen Shot 2021-11-14 at 12 11 04 PM](https://user-images.githubusercontent.com/35156624/141691002-7280a78f-6330-4847-96bd-7641cb83ab82.png)
