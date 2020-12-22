# Sun Roof
This is a semestral work for course BI-TJV using modified database model from course BI-DBS.

## Installation
First, download this repository and run

`mvn install`

Then make sure you have docker installed. After that go to the root directory of this project and run 

`docker volume create --name=postgres_data`

Now everytime you want to start this project you will need to run

`docker-compose up`

from the root directory of this project.

## Client side

Client side of this project is in its own repository [here](https://gitlab.fit.cvut.cz/havasiva/sun-roof-client).

## Update

Added new REST operation, which updates all orders with given name and after given date to a certain price.

### Author
Ivan Havasi