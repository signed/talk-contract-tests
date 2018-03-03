Whats the difference between a Consumer and a Producer
Why do I need to use this clunky lambda dsl to define the json body.
How to use a pact broker

How does a provider communicate breaking changes back to the client?
How valuable are those tests, if they are only run against parts of the service? (Unit test style fashion)

Are client-libraries solving the same problem?

Failed verification results are not uploaded. Is this correct?
If provider validation fails the pact broker switches to red with 405 Method not allowed message? What does this mean?

I added a new property on the provider side that breaks the consumer. How is the consumer informed about this? Right now everything looks peachy in pact broker
There seems to be a bug in the pact broker where I'm not allowed to access the verification results

can I upload pacts from within pact-jvm-consumer-junit without using pact gradle plugin.?

Battery example to showcase provider states

https://en.wikipedia.org/wiki/Robustness_principle

How to switch out the code highlighter theme
How to do hot reloading of the slides

How to bring technical details from the provider into the consumer, like offline because of no battery.
How does one get them to think about this? (talk to each other)


To read:

http://rea.tech/enter-the-pact-matrix-or-how-to-decouple-the-release-cycles-of-your-microservices/
https://dius.com.au/resources/contract-testing/
https://dius.com.au/2018/01/21/closing-the-loop-with-pact-verifications/

Ideas:

Feed verification responses from the provider back into the consumer test

record retry information in the pact as well
record connect / read timeout information
record git repository information in the pact
record contact information into the pact

report on which fields are no longer accessed by anyone, candidates for removal!