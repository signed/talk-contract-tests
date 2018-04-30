package interactions;

import static java.lang.String.format;

public class Provider {

    Result interact(int argument) {
        return new Result(format("provider(%s)", argument));
    }
}
