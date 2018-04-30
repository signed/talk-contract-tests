package interactions;

import static java.lang.String.format;

public class Provider {

    Result interact(int argument) {
        if (argument < 0) {
            throw new RuntimeException("Nope, sorry no more money for this");
        }
        return new Result(format("provider(%s)", argument));
    }
}
