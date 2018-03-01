# Pact
## Introduction
[official documentation](https://docs.pact.io/)
[blog post intro to pact](https://dius.com.au/2016/02/03/pact-101-getting-started-with-pact-and-consumer-driven-contract-testing/)

## Javascript
## Java
The documentation for pact-jvm seems to be in the projects [github wiki](https://github.com/DiUS/pact-jvm/wiki)
### Examples
Pact-jvm with spring boot, have a look at
https://github.com/Mikuu/Pact-JVM-Example good documented and up to date

https://github.com/mstine/microservices-pact has not been updated since 2016


### Integration
#### Java
[Introduction workshop](https://github.com/DiUS/pact-workshop-jvm) into pact jvm.
There is [pact-jvm-consumer-java8](https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-consumer-java8) that provides a DSL to write consumer pacts.

- I do not like that I have to set the output directory for pact files via system property
- The pact-jvm project uses a mix of groovy, java, scala, kotlin code, bit daunting. Why is not clear to me.
- Documentation on jvm specific details is cluttered and all over the repository. Outdated links and code samples as well. 
- Why do I need the dsl to define json?
- Pact files seem to be merge when being written to a file with what is already there.

#### Gradle
[pact-jvm-provider-gradle](https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-provider-gradle)