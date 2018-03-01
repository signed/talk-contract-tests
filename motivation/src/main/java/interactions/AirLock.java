package interactions;

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
        System.out.println("Better hold you breath.");
    }

    private final Consumer consumer;

    AirLock(Consumer consumer) {
        this.consumer = consumer;
    }

    private boolean lock() {
        int inputFromDomainExpert = -43;
        Result result = consumer.upperCaseResult(inputFromDomainExpert);
        //do things with te result
        return true;
    }
}
