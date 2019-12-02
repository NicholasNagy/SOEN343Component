<h1>SOEN 343 Property Management </h1>

<h2>Prerequisites</h2>
    
    - git version 1.6.5 or higher
    - Docker/Docker-Compose (Recommended)
    or
    - git version 1.6.5 or higher
    - Maven
    - NPM
    
For installation of these software applications see the below links:

For git: 

https://git-scm.com/book/en/v2/Getting-Started-Installing-Git

For docker and docker-compose on Windows:

    https://docs.docker.com/docker-for-windows/install/

For docker and docker-compose on Mac:

    https://docs.docker.com/docker-for-mac/install/
    
For docker on Ubuntu:

    https://docs.docker.com/install/linux/docker-ce/ubuntu/
    
For docker-compose on Ubuntu:

    https://docs.docker.com/compose/install/
    
For installing Maven:

    https://maven.apache.org/install.html
    
For installing npm:

    https://nodejs.org/en/

<h2>Testing</h2>

In order to run the tests, you will have to have to run them with either docker/docker-compose or with
maven.

<h4>Step 1 - Clone Repository</h4>

Open up a terminal or command prompt on your OS of choice (Windows/Linux/Mac), and 
enter the following commands in a directory where you want to project to be stored:

    git clone --recursive https://github.com/NicholasNagy/SOEN343Component.git
    git submodule update --remote
    cd SOEN343Component
    
<h5>Note: It is very import to specify to git to clone the repository recursively 
or else the submodules won't be cloned, and hence part of the project won't be cloned.</h5>

<h3>Running Tests with Docker and Docker-Compose</h3>

You will need the following installed on your computer:

    - git version 1.6.5 or higher
    - Docker and Docker-Compose

<h4>Step 2 - Run Tests</h4>

Now in your terminal type the following command:

    docker-compose -f docker-compose.test.yml up
    
And the tests should run.

<h3>Running Tests with Maven</h3>

<h4>Step 2 - Run Tests</h4>

You will need the following installed on your computer:

    - git version 1.6.5 or higher
    - Maven

Now in your terminal type the following command:

    mvn test
    
And the tests should run.


<h2>Deploying the System</h2>

<h3>With Docker and Docker Compose (Recommended)</h3>

You will need the following installed on your computer:

    - git version 1.6.5 or higher
    - Docker and Docker-Compose

<h4>Step 1 - Clone Repository</h4>

Open up a terminal or command prompt on your OS of choice (Windows/Linux/Mac), and 
enter the following commands in a directory where you want to project to be stored:

    git clone --recursive https://github.com/NicholasNagy/SOEN343Component.git
    git submodule update --remote
    cd SOEN343Component
    
<h5>Note: It is very import to specify to git to clone the repository recursively 
or else the submodules won't be cloned, and hence part of the project won't be cloned</h5>

<h4>Step 2 - Deploy</h4>

In the terminal run the following command in your terminal or command prompt and
enter the following command in the directory of the project:

    docker-compose up
    
At this point docker will build and deploy the containers and the web server will be hosted
on port 4200 (obligatory for our CORS setup). One the containers are running, SPRING will 
display notify you in the terminal/command prompt.
    
<h3>Deploying the System Manually (Not Recommended)</h3>

You will need the following installed on your computer:

    - git version 1.6.5 or higher
    - Maven
    - NPM

<h4>Step 1 - Clone Repository</h4>

Open up a terminal or command prompt on your OS of choice (Windows/Linux/Mac), and 
enter the following commands in a directory where you want to project to be stored:

    git clone --recursive https://github.com/NicholasNagy/SOEN343Component.git
    git submodule update --remote
    cd SOEN343Component
    
<h5>Note: It is very import to specify to git to clone the repository recursively 
or else the submodules won't be cloned, and hence part of the project won't be cloned</h5>


<h4>Step 2 - Build and run Java Project</h4>

In the the root directory of the project where the pom.xml is run the following:

    mvn package

Afterwards, run the jar from the root directory by entering the following:

    java -jar /ws/component/target/PM-0.0.1-SNAPSHOT.jar
    
<h4>Step 3 - Build and Run UI</h4>

Navigate in a NEW terminal or command prompt to the directory of the project and enter 
the subdirectory found in src/main/UI. Once there enter the following commands:

    npm install
    npm install -g @angular/cli

At this point, you should be able to run the project with the following command:

    ng serve
 
At this point, the server should be running on port 4200 (obligatory for our CORS setup), and
you should be able to view the webserver on port 4200.
