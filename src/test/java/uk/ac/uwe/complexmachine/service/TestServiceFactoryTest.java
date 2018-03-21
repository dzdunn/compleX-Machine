package uk.ac.uwe.complexmachine.service;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Clare Daly
 * @author Alex Mewha
 * @version alpha-6.0
 * @since alpha-4.0
 */
public class TestServiceFactoryTest {
    /**
     * Tests that the missingTransitions test type creates the MissingTransitionService.
     * @throws IllegalArgumentException if an invalid test type has been passed to the factory
     */
    @Test
    public void shouldReturnMissingTransitionTestServiceForTestType() throws IllegalArgumentException {
        // Given the test service factory and the missing transitions test type
        TestServiceFactory factory = new TestServiceFactory();
        String testType = "missingTransitions";

        // When getting the test service
        AbstractTestService result = factory.getTestService(testType);

        // Then the test service should be a MissingTransitionTestService
        assertThat("Test service should be MissingTransitionTestService", result, instanceOf(MissingTransitionTestService.class));
    }

    /**
     * Tests that the extraTransitions test type creates the ExtraTransitionService.
     * @throws IllegalArgumentException if an invalid test type has been passed to the factory
     */
    @Test
    public void shouldReturnExtraTransitionTestServiceForTestType() throws IllegalArgumentException {
        // Given the test service factory and the missing transitions test type
        TestServiceFactory factory = new TestServiceFactory();
        String testType = "extraTransitions";

        // When getting the test service
        AbstractTestService result = factory.getTestService(testType);

        // Then the test service should be a ExtraTransitionTestService
        assertThat("Test service should be ExtraTransitionTestService", result, instanceOf(ExtraTransitionTestService.class));
    }
    /**
     * Tests that the misdirectedTransitions test type creates the MisdirectedTransitionsService.
     * @throws IllegalArgumentException if an invalid test type has been passed to the factory
     */
    @Test
    public void shouldReturnMisdirectedTransitionTestServiceForTestType() throws IllegalArgumentException {
        // Given the test service factory and the missing transitions test type
        TestServiceFactory factory = new TestServiceFactory();
        String testType = "misdirectedTransitions";

        // When getting the test service
        AbstractTestService result = factory.getTestService(testType);

        // Then the test service should be a ExtraTransitionTestService
        assertThat("Test service should be MisdirectedTransitionsTestService", result, instanceOf(MisdirectedTransitionsTestService.class));
    }

    /**
     * Tests that an invalid argument exception is thrown if an invalid test type is passed to the factory.
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionForInvalidTestType() throws IllegalArgumentException {
        // Given the test service factory and the missing transitions test type
        TestServiceFactory factory = new TestServiceFactory();
        String testType = "notAValidTestType";

        // When getting the test service
        factory.getTestService(testType);

        // Then the test service should throw an invalid argument exception
    }
}
