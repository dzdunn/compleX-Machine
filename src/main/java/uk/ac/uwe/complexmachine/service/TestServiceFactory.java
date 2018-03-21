package uk.ac.uwe.complexmachine.service;


import org.springframework.stereotype.Component;

/**
 * @author Clare Daly
 * @author Alex Mewha
 * @version alpha-6.0
 * @since alpha-4.0
 */
@Component
public class TestServiceFactory {
    /**
     * Creates the test service for the test type.
     * @param testType the type of test to get the test service for
     * @return the test service for the test type
     * @throws IllegalArgumentException if an invalid test type is passed
     */
    public AbstractTestService getTestService(String testType) throws IllegalArgumentException {
        AbstractTestService testService;
        switch(testType) {
            case "missingTransitions":
                testService = new MissingTransitionTestService();
                break;
            case "extraTransitions":
                testService = new ExtraTransitionTestService();
                break;
            case "misdirectedTransitions":
                testService = new MisdirectedTransitionsTestService();
                break;
            default:
                throw new IllegalArgumentException("Invalid test type selected");
        }

        return testService;
    }
}
