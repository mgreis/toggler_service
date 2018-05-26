# Toggler POC
## Getting Started
This project is meant to be a proof of concept for a toggler service.

It features a toggler service built using the ff4j Feature Toggle framework and the Spring Boot Framework.
To allow persistency, all togglers are stored in Cassandra running on a Docker container.

Information regarding ff4j can be found at [ff4j.org](http://ff4j.org).

Information regarding Spring Boot can be found at [projects.spring.io/spring-boot](https://projects.spring.io/spring-boot/).

Information regardin Apache Cassandra can be found at [cassandra.apache.org](http://cassandra.apache.org/).

### Configure all necessary repositories 

Edit '~/.m2/setting.xml' and add all the missing repositories.

### Build the  and images for the project

The file already features a Makefile that allows the docker containers meant to test the concept to be launched.

| Action                        | Command              |
|-------------------------------|----------------------|
|Build the project              | $make install        |
|Build Docker images            | $make docker-install |
|Start the docker environment   | $make docker-start   |
|Stop the docker environment    | $make docker-stop    |
|Restart the docker environment | $make docker-restart |
|Run smoke tests                | $make install-tests  |

