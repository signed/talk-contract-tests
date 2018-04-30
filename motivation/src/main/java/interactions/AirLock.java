package interactions;

/**
 * There is <a href="https://github.com/robindanzinger/chadojs/blob/master/example/example.md">chado.js</a> for javascript
 * to perform these checks on unit test level.
 *
 * I do not know about a similar tool for java.
 */
public class AirLock {

    public static void main(String[] args) {
        try{
            AirLock airLock = new AirLock(new Consumer(new Provider()));
            if(airLock.lock()){
                takeOfYourHelmet();
            }else{
                betterHoldYourBreath();
            }
        }catch (RuntimeException ex){
            betterHoldYourBreath();
        }
    }

    private static void takeOfYourHelmet() {
        System.out.println("You can take off your helmet");
    }

    private static void betterHoldYourBreath() {
        System.out.println("Better hold your breath.");
    }

    private final Consumer consumer;

    private AirLock(Consumer consumer) {
        this.consumer = consumer;
    }

    private boolean lock() {
        int inputFromDomainExpert = -43;
        Result result = consumer.upperCaseResult(inputFromDomainExpert);
        //do things with te result
        return true;
    }
}
