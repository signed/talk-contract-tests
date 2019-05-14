# 1st time setup
```bash
echo "127.0.0.1       pactbroker" >> /private/etc/hosts
```

# start each container in isolation
```bash
docker-compose up postgres
docker-compose up pact_broker
docker-compose down #destroys all containers
```