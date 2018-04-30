package interactions;

class Consumer {

    private final Provider provider;

    Consumer(Provider provider) {
        this.provider = provider;
    }

    Result upperCaseResult(int input) {
        String providerReturned = provider.interact(input).message;
        String providerResult = providerReturned.toUpperCase();
        return new Result("consumer(" + providerResult + ")");
    }
}
