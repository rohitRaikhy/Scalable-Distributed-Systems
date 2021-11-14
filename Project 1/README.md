
# Scalable-Distributed-Systems

![image](https://user-images.githubusercontent.com/35156624/134996442-a93aae7b-0ff8-4292-bf96-d2d5de07c242.png)

To run TCPServer: Java TCPServer portnumber
<br/>
To run TCPClient: Java localhost portnumber
<br/>
To run UDPClient: Java UDPClient "message" localhost portnumber
<br/>
To run UDPServer: Java UDPServer portnumber

The scope of work includes an introduction to socket programming using Java; TCP and UDP protocols to send/receive data using client/server architecture. Introduction to networking and an understanding of basic operating systems was needed for this work. Ability to handle single threaded requests and a look into multi-threaded for future improvements, exception handling with TCP/UDP protocols was needed to complete this scope of work. The key concepts of intercommunication between programs running on different machines in a network is covered. Exposure to different packages such as java.net which was required for creating sockets were also covered. The basis of this work is to learn the concept behind UDP and TCP protocols. UDP protocol is used when delivery of data in order does not matter as much as the timely delivery of the data. For instance, video streaming on Netflix one would not care about some data of video being lost but the consistency of how well the video streams. Whereas, TCP protocol maintains data consistency in transport, more useful when email or an important document is needed to be sent.  

For the future, one can use better exception handling when handling the data requests from both UDP and TCP to make the server and client more robust to malformed data packets. One can also make the java code more modularized through helper methods from repeated code, (unable to do so due to time constraints). Using multi-threading can also help make the client server architecture more robust. As one request can correctly be handled at a time, using multi-threading one can process more than one request to allow the client/server architecture to speed up. A queue could also be used to handle requests through this multitier approach. One can go a step further and set priority on certain requests over others. Priority queue data structure can help in this regard. The scope of work also seems to include docker, (this was not included due to time constraints and seemed to not be on the project description).  This can be implemented in the future. 

## Screenshot of TCP Server
![Screen Shot 2021-09-28 at 12 19 29 AM](https://user-images.githubusercontent.com/35156624/134997975-6484e84c-f9f2-4897-8d57-94dbfdd87b24.png)

## Screenshot of TCP Client
![Screen Shot 2021-09-28 at 12 20 23 AM](https://user-images.githubusercontent.com/35156624/134998053-0d2c901c-fb9c-47b4-bdbb-536ee00bc80b.png)

## Screenshot of UDP Server
![Screen Shot 2021-09-28 at 12 23 11 AM](https://user-images.githubusercontent.com/35156624/134998253-58aff40a-8db1-4fe9-8c5c-8d575702b618.png)

## Screenshot of UDP Client
![Screen Shot 2021-09-28 at 12 23 55 AM](https://user-images.githubusercontent.com/35156624/134998302-64bcb1b7-90cd-49ea-b46a-3ce920870969.png)


