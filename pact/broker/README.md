
```
docker-compose up postgres
docker-compose up pact_broker
echo `docker-machine ip docker-vm`"  pactbroker" >> /private/etc/hosts
docker-compose down #destroys all containers
```