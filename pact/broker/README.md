Run to official docker image of the [pact-broker](https://github.com/pact-foundation/pact-broker-docker).

# optional one time setup
If you want to access the pact broker ui at http://pactbroker in addition to [http://127.0.0.1]([http://127.0.0.1](http://127.0.0.1))
```bash
echo "127.0.0.1       pactbroker" >> /private/etc/hosts
```

# start each container in isolation
```bash
docker-compose up postgres
docker-compose up pact_broker
docker-compose up jenkins
```
The pact broker ui should be available at [http://127.0.0.1](http://127.0.0.1).
Jenkins should be available at [http://localhost:8080](http://localhost:8080) 

# What do I have to do to clean existing data
1. Stop the containers
1. Stop containers `docker-compose down`
1. Delete the all stopped containers `docker rm $(docker ps -a | grep Exited | awk '{print $1}')` 
1. Delete all files in `postgres/var` and `jenkins/var` except `.gitignore`
