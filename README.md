<h1>SOEN 343</h1> :neckbeard: 

<h2>Directory Structure and Main File</h2>

<h4>Development</h4>

For development, write your code in src/main/java. For example, you can create a package
named packageOne and put your files in that package.

<h4>Main File</h4>

For choosing which main method to execute, you must modify the pom.xml file. Simply look
for all instances of \<mainClass>\</mainClass> and insert the location of the main file from
the src/main/java directory.

<h4>Testing</h4>

For writing tests, you can write them similarly to when you're developing. Simply create 
directories in src/test/java for packages and develop from there.


<h2>Using Maven</h2>

All the following commands need to be run from within the root directory of the project.

<h4>Compiling</h4>

    mvn compile
    
<h4>Running Main File Declared in pom.xml</h4>

    mvn exec:java

<h4>Running Tests</h4>

    mvn test

<h4>Packaging into a single JAR file</h4>

    mvn package
    
<h4>Running JAR file</h4>

    java -jar ./target/java-project-1.0.jar
    

<h2>Deploying the development environment with Docker</h2>

Step 1. Make sure you have both docker-compose and docker installed:

    For Ubuntu users: https://docs.docker.com/install/linux/docker-ce/ubuntu/

    for docker-compose: https://docs.docker.com/compose/install/
    
    For Windows users: https://docs.docker.com/docker-for-windows/install/

    For Mac OS users: https://docs.docker.com/docker-for-mac/install/
    
    Note that docker-compose is installed by default on Windows and Mac os

Step 2. Run the container

Navigate to this folder within terminal/command prompt and type the following:

    docker-compose up -d

This will will build and start the container in a detached state from the current instance.

Step 3. Access the container

Within the terminal/command prompt type the following:

    docker exec -it mvndevenv bash

This will begin a bash terminal instance within the docker container, and you will have the necessary
environment to compile and run Maven and Java

