package interactions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProviderTest {

    @Test
    void doWhatIWantNotWhatISay() {
        Provider provider = new Provider();

        assertThat(provider.interact(anyInput()).message).isEqualTo("provider(-17)");
    }

    private int anyInput() {
        return -17;
    }
}