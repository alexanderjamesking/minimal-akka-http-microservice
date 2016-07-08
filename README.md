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

