Why do I need to use this clunky lambda dsl to define the json body.
How does a provider communicate breaking changes back to the client?
How valuable are those tests, if they are only run against parts of the service? (Unit test style fashion)
Are client-libraries solving the same problem?
can I upload pacts from within pact-jvm-consumer-junit without using pact gradle plugin.?
How to bring technical details from the provider into the consumer, like offline because of no battery.

Ideas:

feed verification responses from the provider back into the consumer test
report on which fields are no longer accessed by anyone, candidates for removal!
record retry information in the pact as well
record connect / read timeout information
record git repository information in the pact
record contact information into the pact
