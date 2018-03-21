package uk.ac.uwe.complexmachine.service;

import com.github.javaparser.ast.CompilationUnit;
import org.springframework.stereotype.Service;
import uk.ac.uwe.complexmachine.exception.TransitionNotFoundException;
import uk.ac.uwe.complexmachine.model.Specification;
import uk.ac.uwe.complexmachine.model.Test;
import uk.ac.uwe.complexmachine.model.TestResult;
import uk.ac.uwe.complexmachine.model.Transition;

import java.util.Date;
import java.util.List;

import static uk.ac.uwe.complexmachine.model.Result.FAIL;
import static uk.ac.uwe.complexmachine.model.Result.PASS;

/**
 * @author Clare Daly
 * @version alpha-4.0
 * @since alpha-3.0
 */
@Service
public class MissingTransitionTestService extends AbstractTestService {
    /**
     * Compares the specification to the system to identify transitions
     * present in the specification that are not present in the system.
     * @param specification the specification to test against
     * @param system the system to look for transitions in
     * @return the results of the test
     */
    @Override
    public TestResult runTest(Specification specification, CompilationUnit system) {
        List<Transition> systemMethods = getMethodsFromSystem(system);
        List<Transition> specTransitions = getTransitionsFromSpecification(specification);

        TestResult testResult = new TestResult();
        testResult.setTimeStamp(new Date());

        int testId = 1;
        for(Transition expected: specTransitions) {
            Test test = new Test();
            test.setTestID("Test" + testId++);
            test.setExpectedTransition(expected.toString());
            try {
                test.setActualTransition(getActualTransition(expected, systemMethods).toString());
                test.setResult(PASS);
            } catch (TransitionNotFoundException exception) {
                test.setResult(FAIL);
            }
            testResult.addTest(test);
        }

        return testResult;
    }

    /**
     * Get the expected transition object from the system methods. If
     * the transition cannot be found, an error is thrown
     * @param expected the transition to fine
     * @param systemMethods the methods in the system to look in
     * @return the found method in the system
     */
    private Transition getActualTransition(Transition expected, List<Transition> systemMethods) throws TransitionNotFoundException {
        for(Transition actual: systemMethods) {
            if(null != expected && expected.toString().equals(actual.toString())) {
                return actual;
            }
        }

        throw new TransitionNotFoundException("Could not find " + expected.toString());
    }
}
