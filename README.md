# person-collector

This is a service that receives the name and id of a person and 
saves it into a file. 
The person data is saved in a delimited google protobuf serialization format. 

## Getting Started

The service is packaged with maven and docker compose. To build this project, 
you need the following installed:

1. Docker 
2. Docker Compose
3. java 8
4. Maven 3

## Building the project 

~~~
git clone https://github.com/geraldoyudo/person-collector.git
cd person-collector
./build.sh
~~~

## Testing the project

~~~
mvn test
~~~

## Running the person-collector service

~~~
./start.sh
~~~

## Stopping the person-collector service

~~~
./stop.sh
~~~

## Consuming The Service

Assuming our service host is localhost, a post request to 
http://localhost:8080 with the person json data will add 
a person data.

### Example 

~~~
POST / HTTP/1.1
Host: localhost:8080
Content-Type: application/json
{
	"name":"Gerald",
	"id":123
}
~~~

## Configuring the person-collector service

### Changing the default output directory

The output directory is set to /root/output by default. You can 
customize this by setting the PERSON_COLLECTOR_HOME environment variable. 

Please note  that some terminals (eg. MinGW for windows) will append a path prefix 
to the environment variable value.

To prevent this from happening in MinGW (Git bash), set the MSYS_NO_PATHCONV 
environment variable to 1 

`export MSYS_NO_PATHCONV=1`

### Changing the Rollover Period

The person-collector service rolls over the file after a predefined time interval. 
This time interval is defined by the PERSON_COLLECTOR_ROLLOVER_CRON environment variable. 

Person controller, by default, rolls over a file every minute (useful for test purposes). 
To change this, set the PERSON_COLLECTOR_ROLLOVER_CRON variable to any cron value as 
described by the Cron Tab Pattern ( https://www.manpagez.com/man/5/crontab/)

Examples:
1. "0 0 * * * *" = the top of every hour of every day.
2. "*/10 * * * * *" = every ten seconds.
3. "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
4. "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
5. "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
6. "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
7. "0 0 0 25 12 ?" = every Christmas Day at midnight

### Changing the output file name

The person-collector writes to a "sample.txt" file as a default. 
You can change this value by setting the PERSON_COLLECTOR_FILE_NAME. 

NB: The file name should not have any paths. (valid files: person.txt)

### Summary(Environment Variables)

1. Output Directory:

    PERSON_COLLECTOR_HOME: /root/output
    
2. Output File name:

    PERSON_COLLECTOR_FILE_NAME: sample.txt

3. Rollover Period:

    PERSON_COLLECTOR_ROLLOVER_CRON: */60 * * * * *

    
