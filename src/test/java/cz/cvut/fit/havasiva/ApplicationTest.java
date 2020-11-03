package cz.cvut.fit.havasiva;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApplicationTest {

    @BeforeEach
    void beforeEach() {
        System.out.println("Running test...");
    }

    @Test
    void veryUsefulTest() {
        Assertions.assertEquals(1,1);
    }
}
