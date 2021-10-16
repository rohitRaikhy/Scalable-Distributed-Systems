# Running the program
<br>
The following will be needed to run the program. Download and install Rabbitmq server, code editor, terminal, jar files. Jar files are provided in class path in project src file. 
The following commands need to be inputted in seperate terminal windows. 
<br>
## Commands to compile and run the program
<br>
javac -cp amqp-client-5.7.1.jar Send.java Receive.java
<br>
java -cp .:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar Receive
<br>
java -cp .:amqp-client-5.7.1.jar:slf4j-api-1.7.26.jar:slf4j-simple-1.7.26.jar Send
<br>
## Screenshots of output
<br>
## Running the rabbitmq server instance
<br>
![Screen Shot 2021-10-16 at 2 46 51 PM](https://user-images.githubusercontent.com/35156624/137598851-50852275-149e-437f-ab16-b73766ee2df3.png)
<br>
## Running the receive command on seperate terminal window
![Screen Shot 2021-10-16 at 2 47 49 PM](https://user-images.githubusercontent.com/35156624/137598883-4ec09205-08df-4b40-be08-443757ff425c.png)
<br>
## Running the send command on seperate terminal window
![Screen Shot 2021-10-16 at 2 48 19 PM](https://user-images.githubusercontent.com/35156624/137598897-677fcf60-7e76-4269-9174-702d9c14d5c4.png)
