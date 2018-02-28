With docker-machine on mac

````
docker-machine create --driver virtualbox docker-vm
docker-machine start docker-vm
docker-machine env docker-vm
eval $(docker-machine env docker-vm)
docker-compose up
docker-compose down #destroys all containers
echo `docker-machine ip docker-vm`"  pactbroker" >> /private/etc/hosts
````