Problem statement:

`Implement Online Game Gratification system using Kafka Streams.
Product Offering
--There could be multiple active games at the same time.
--A trialUser registers and plays a particular game [or could be multiple games at the same time]
--Users registered in a game can have different roles eg: Admin Player
We want to design a system which can analyse trialUser game progress and perform some action based on certain criteria.
Eg: When trialUser achieves certain level in the game we want to send email notification (or perform any other action).
We will implement this in 2 Parts.
Part 1:
Learning Objectives:
1-Understand Kafka and why it is not just a replacement of another messaging system.
2-Learn to use Spring Abstraction framework (Spring cloud stream) for writing Kafka based projects.
This video is a good starting point to learn Kafka.
https://www.youtube.com/watch?v=JalUUBKdcA0&t=36s
1-Create a Spring Boot Application which produces User Game Progress Event.
Consider below sample event message and publish this to a Kafka Topic, when the trialUser completes a particular level.
It contains the time spent by the trialUser in completing that level and the overall game completion percent.
Sample Event
{
	userId : 1,                      //0<userId<=100
	gameId: GAME1,                   //GAME1<=gameId<=GAME10
	level:1                          //0<level<=10
	userRole: PLAYER,    			//Assume it could be either ADMIN or PLAYER
	timeSpent :40       			//in Mins 
	gameCompletionPercent: 10        
}
You can implement a processor, which produces randomised event data with the given constraints.
2-Create another Spring Boot project which consumes the User Game Progress Event and logs the event 
Use below dependencies
--Spring Cloud Stream
--Spring Cloud Kafka Binder.
Deadline to Complete Part 1: 20th Oct
Part 2:
We will extend Part 1 to accept the incoming messages as a message stream and perform real time analysis on it.
Will share the exact scenarios at the end of Part 1.`

Setup:
App runs on 8080

Download [Kafka](https://www.apache.org/dyn/closer.cgi?path=/kafka/2.6.0/kafka-2.6.0-src.tgz) and extract the content into a folder.

Change directory in the Kafka project directory.

Build the Kafka project if not already : `./gradlew jar -PscalaVersion=2.13.2`

Run Zookeeper : `bin/zookeeper-server-start.sh config/zookeeper.properties`

Run Kafka server : `bin/kafka-server-start.sh config/server.properties`

Create a trial topic: `bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic trial-topic`

Listen to the producer : `bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic trial-topic --from-beginning`
`mvn clean install tomcat7:run`

Run the Spring Boot Application : `java -jar target/gamification-producer-0.0.1-SNAPSHOT.jar`

cURL: 
`curl --location --request POST 'localhost:8080/apis/trial/json-message' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstname": "Pradipta",
    "lastname": "Sarma"
}'`

You can see the message on the consumer terminal

Game APIs Postman [collection](https://www.getpostman.com/collections/d0dac0ccc156ba8606fd)