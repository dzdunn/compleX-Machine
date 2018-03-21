package uk.ac.uwe.complexmachine.service;

import com.github.javaparser.ast.CompilationUnit;
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
 * @since alpha-4.0
 */
public class ExtraTransitionTestService extends AbstractTestService {
    /**
     * Compares the specification to the system to identify transitions
     * present in the system that are not present in the specification.
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
        for(Transition actual: systemMethods) {
            Test test = new Test();
            test.setTestID("Test" + testId++);
            test.setActualTransition(actual.toString());
            try {
                test.setExpectedTransition(getExpectedTransition(actual, specTransitions).toString());
                test.setResult(PASS);
            } catch (TransitionNotFoundException exception) {
                test.setResult(FAIL);
            }
            testResult.addTest(test);
        }

        return testResult;
    }

    /**
     * Get the actual transition object from the specification methods. If
     * the transition cannot be found, an error is thrown
     * @param actual the transition to find
     * @param specMethods the methods in the spec to look in
     * @return the found method in the spec
     */
    private Transition getExpectedTransition(Transition actual, List<Transition> specMethods) throws TransitionNotFoundException {
        for(Transition expected: specMethods) {
            if(null != actual && actual.toString().equals(expected.toString())) {
                return expected;
            }
        }

        throw new TransitionNotFoundException("Could not find " + actual.toString());
    }
}
