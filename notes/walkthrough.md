* Motivation (code samples)
  * Multiple implementations that have to fulfill a contract to be used interchangeably
  * Message passing on unit test level that can drift apart
    * Integration tests vs. Testdouble
    * Within the same codebase I usually do not have the problem  
  * Between Consumer and Provider Service, different Teams
* Inter Service
  * Communication is key
    * people to people
      * disrupted by hand overs
      * hindered by timezones
    * documentation  
  * initial integration
    * what is already there
    * what does the consumer need
  * api evolution
    * lets do a /v2/
    * how do you let your client
    * how do you get your clients to migrate
  * Know who uses your service
  * Know what part is used

* What is your understanding of contract tests

* Integration test to the rescue?
  * Costly integration environments
    * The whole system has to be in the right state for your test to succeed
    * Transitive service dependencies make this brittle and slow 
  * http://blog.thecodewhisperer.com/permalink/integrated-tests-are-a-scam
    * Contract tests
      * Ensure interaction between two service work as expected
      * Consumer only sends requests Provider can handle
      * Provider only sends responses Consumer can handle
      * If this holds between all services in the system that can talk to each other, the whole system should work
    * m*n tests become m+n tests

* How do you currently tackle those problems?
  * Hand written Testdoubles
  * v2 api
  * internal open source with pull requests 

* Pact
  * Replace integration test with fast running unit tests
  * Implement what the Consumer requires
  * applicable if you own Consumer and Provider code, not for public api (Swagger documentation a better approach)
  * cross language  

* Concepts
 * Consumer
 * Provider
 * Pact
   * Interactions
   * Fragment
   
   
* Code Samples
  * Existing approach with integration tests
  * Consumer requests new functionality
    * Operations client wanting timing information on operations
  * Provider breaks existing functionality
  * Provider wants to make breaking changes
  * Consumer breaks contract


- renaming a field / remove a field
  Who are our clients?
  Who actually uses this field?
- Spring upgrades

- Consumer can can change its test if they are breaking
- Provider can can change its test if they are breaking
- Contract can only be changed if booth agree 