package interactions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ProviderTest {

    private int argument = anyAllowedInput();

    @Test
    void doWhatIWantNotWhatISay() {

        assertThat(providerResult().message).isEqualTo("provider(17)");
    }

    @Test
    @Disabled("We still have plenty of money")
    void noLongerWantToPayTheLicenseSorry() {
        argument = anyNegativeInput();
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, this::providerResult);

        assertThat(exception).hasMessage("Nope, sorry no more money for this");
    }

    private Result providerResult() {
        Provider provider = new Provider();

        return provider.interact(argument);
    }

    private int anyNegativeInput() {
        return -1;
    }

    private int anyAllowedInput() {
        return 17;
    }
}