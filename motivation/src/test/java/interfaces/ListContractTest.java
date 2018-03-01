package interfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Do not write collections tests yourself use <a href="https://github.com/google/guava/tree/master/guava-testlib">guava-testlib</a>.
 * <a href="https://blog.codefx.org/techniques/testing/test-collection-implementations-with-guava/">Codefx</a> has a blog post on how to use this.
 *
 * I think that <a href="https://github.com/Claudenw/junit-contracts">junit-contracts</a> does interface based contracts testing,
 * never used it myself
 */
class ListContractTest {

    private final List<String> list = new ArrayList<>();
    //private final List<String> list = new SurpriseList<>();

    @Test
    void newlyCreatedListShouldBeEmpty() {
        assertThat(list).isEmpty();
    }

    @Test
    void noLongerEmptyAfterAnElementWasAdded() {
        list.add("element");

        assertThat(list).isNotEmpty();
    }

    @Test
    void elementCanBeRetrievedImmediatelyAfterItWasAdded() {
        list.add("element");

        assertThat(list).contains("element");
    }

    @Test
    void orderIsPreservedInAList() {
        list.add("first");
        list.add("second");

        assertThat(list).containsExactly("first", "second");
    }

    @Test
    void elementsAreAddedAtTheSpecifiedLocation() {
        list.add("zero");
        list.add(0, "one");

        assertThat(list).containsExactly("one", "zero");
    }

    @Test
    void eachElementAddedIncreasesSizeByOne() {
        list.add("zero");

        assertThat(list).hasSize(1);

        list.add("one");

        assertThat(list).hasSize(2);

        list.add("two");

        assertThat(list).hasSize(3);
    }
}
