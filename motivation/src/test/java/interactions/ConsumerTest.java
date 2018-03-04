package interactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class ConsumerTest {

    private final Provider provider = mock(Provider.class);
    private final Consumer consumer = new Consumer(provider);
    private int input = anyInput();

    @Test
    void returnTheProviderResultInUpperCase() {
        when(provider.interact(input)).thenReturn(new Result("mocked"));

        assertThat(consumerResult().message).isEqualTo("consumer(MOCKED)");
    }

    private Result consumerResult() {
        return consumer.upperCaseResult(input);
    }

    private int anyInput() {
        return -12;
    }
}