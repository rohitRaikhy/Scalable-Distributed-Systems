# Client Server RPC Key Value Store

![image](https://user-images.githubusercontent.com/35156624/138009363-50ee78f5-4b03-42db-950c-3821d9f72dc1.png)


## Running the program

Run: rmiregistry
<br>
To compile Server: javac Server/ServerStart.java 
<br>
To run Server: java Server/ServerStart 1000
<br>
To run Client: java Client/RmiClient.java localhost 1000

## Scope of Work

The scope of work includes an introduction to Remote Procedural Calls and using the RMI framework in java to achieve RPC. RMI framework is used to perform remote method calls on another machine (in the scope of this assignment we use the same machine but works nonetheless) to send/receive data using client/server architecture. Networking, operating systems and RMI was needed for the project. Ability to handle multi threaded requests, expception handling was needed to complete this scope of work. The key concepts of performing RPC is covered, understanding that RPC can be used to utilize greater resources on remote machine to perform computationally extensive work is realized. For instance Amazon EC2 can be considered as a way of using compute resources from the cloud in order to to complete resource heavy tasks, which isvery similar to RPC. 

For the future one can use better exception handling when dealing with data requests and better parsing of user data to make the program more robust. This is a introduction to RPC and in the scope of assignemnt the program may throw some errors. A queue can be used to priortize requests coming into the server from multiple clients. A message service can also be used like message queues through a framework like rabbitmq to post to clients messages needed about the service. The scope of work does not contain docker, but may be added soon. 

## Screenshots
### Terminal 1
![Screen Shot 2021-10-19 at 8 09 31 PM](https://user-images.githubusercontent.com/35156624/138006918-c1699ca5-9db4-436c-abdb-3e67cc06468e.png)
### Terminal 2
![Screen Shot 2021-10-19 at 8 10 23 PM](https://user-images.githubusercontent.com/35156624/138006972-c7ab51cd-6db3-471c-b83f-4b301abc3500.png)
### Terminal 3
![Screen Shot 2021-10-19 at 8 11 41 PM](https://user-images.githubusercontent.com/35156624/138007047-2edcd54f-7eff-4d46-ab19-c7800fb7492b.png)
