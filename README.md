<h1>SOEN 343 Property Management </h1>

<h2>Prerequisites</h2>
    
    - git version 1.6.5 or higher
    - Docker/Docker-Compose (Recommended)
    or
    - git version 1.6.5 or higher
    - Maven
    
You will need the following installed on your computer:

    - git version 1.6.5 or higher
    - Docker and Docker-Compose
    
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

<h2>Testing</h2>

In order to run the tests, you will have to have to run them with either docker/docker-compose or with
maven.

<h4>Step 1 - Clone Repository</h4>

Open up a terminal or command prompt on your OS of choice (Windows/Linux/Mac), and 
enter the following commands in a directory where you want to project to be stored:

    git clone --recursive https://github.com/NicholasNagy/SOEN343Component.git
    cd SOEN343Component
    
<h5>Note: It is very import to specify to git to clone the repository recursively 
or else the submodules won't be cloned, and hence part of the project won't be cloned</h5>

<h3>Running Tests with Docker and Docker-Compose</h3>

<h4>Step 2 - Run Tests</h4>

Now in your terminal type the following command:

    docker-compose -f docker-compose.test.yml up
    
And the tests should run.

<h3>Running Tests with Maven</h3>

<h4>Step 2 - Run Tests</h4>

Now in your terminal type the following command:

    mvn test
    
And the tests should run.


<h2>Deploying the System</h2>



