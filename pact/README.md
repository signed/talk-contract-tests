

# Local file system

# Pact broker
1. follow `broker/README.md` to start the pact-broker
1. run `./gradlew pactPublish` in `consumer/java` to publish a pact
1. run `./gradlew build` in `provider/jvm-provider` to publish a verification
   alternatively execute `PactJunitIntegrationTest` in your ide