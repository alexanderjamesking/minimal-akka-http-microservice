Minimal Akka HTTP Service with Pact contract
============================================

Run the server
```
sbt run
```

Publish the pact contract

```
sbt pact-publish
```

Verify the server meets the contract

```
sbt "pact-verify --host localhost --protocol http --port 9000 --source target/pacts"
```

Assemble the jar

```
sbt assembly
```

Run in docker
```
# build a container
docker build -t alex/akka-ping .

# run the container and forward port 9000 on your mac to port 9000 on the docker container
docker run -t -i -p 9000:9000 alex/akka-ping /bin/bash
```

Ping it on a different terminal to test (where 192.168.99.100 is your docker machine IP on a mac)

```
curl http://192.168.99.100:9000/ping -v
```

