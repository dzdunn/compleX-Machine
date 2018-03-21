package uk.ac.uwe.complexmachine.testutils;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import uk.ac.uwe.complexmachine.model.Result;
import uk.ac.uwe.complexmachine.model.Test;
import uk.ac.uwe.complexmachine.model.TestResult;
import uk.ac.uwe.complexmachine.model.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-3.0
 */
public final class TestResultMatcher {
    /**
     * Private constructor to prevent instantiation.
     */
    private TestResultMatcher() {
        // Private constructor
    }

    /**
     * Checks that all the expected transitions are present in the TestResult object.
     *
     * @param expectedTransitions the expected transitions to find
     * @return true if all the expected transitions are present in the TestResult object
     */
    public static BaseMatcher<TestResult> containsAllExpectedTransitions(List<Transition> expectedTransitions) {
        return new BaseMatcher<TestResult>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object object) {
                if (!(object instanceof TestResult)) {
                    return false;
                }
                TestResult objectToTest = (TestResult) object;
                List<Test> testsRun = objectToTest.getTests();
                if (null == testsRun) {
                    return false;
                }

                List<Transition> matchedTransitions = new ArrayList<>();
                for (Transition expected : expectedTransitions) {
                    if (expectedTransitionHasTest(expected, testsRun)) {
                        matchedTransitions.add(expected);
                    }
                }

                return expectedTransitions.equals(matchedTransitions);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void describeTo(Description description) {
                description.appendText(" not all expected transitions where tested");
            }
        };
    }

    /**
     * Checks that all the actual transitions are present in the TestResult object.
     *
     * @param actualTransitions the actual transitions to find
     * @return true if all the actual transitions are present in the TestResult object
     */
    public static BaseMatcher<TestResult> containsAllActualTransitions(List<Transition> actualTransitions) {
        return new BaseMatcher<TestResult>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object object) {
                if (!(object instanceof TestResult)) {
                    return false;
                }
                TestResult objectToTest = (TestResult) object;
                List<Test> testsRun = objectToTest.getTests();
                if (null == testsRun) {
                    return false;
                }

                List<Transition> matchedTransitions = new ArrayList<>();
                for (Transition actual : actualTransitions) {
                    if (actualTransitionHasTest(actual, testsRun)) {
                        matchedTransitions.add(actual);
                    }
                }

                return actualTransitions.equals(matchedTransitions);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void describeTo(Description description) {
                description.appendText(" not all expected transitions where tested");
            }
        };
    }

    /**
     * Checks that all the transitions are present with targets in the TestResult object.
     *
     * @param transitions the transitions to find
     * @return true if all the transitions with their targets are present in the TestResult object
     */
    public static BaseMatcher<TestResult> containsAllExpectedTransitionsWithTargets(List<Transition> transitions) {
        return new BaseMatcher<TestResult>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object object) {
                if (!(object instanceof TestResult)) {
                    return false;
                }
                TestResult objectToTest = (TestResult) object;
                List<Test> testsRun = objectToTest.getTests();
                if (null == testsRun) {
                    return false;
                }

                List<Transition> matchedTransitions = new ArrayList<>();
                for (Transition transition : transitions) {
                    if (expectedTransitionWithTargetHasTest(transition, testsRun)) {
                        matchedTransitions.add(transition);
                    }
                }

                return transitions.equals(matchedTransitions);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void describeTo(Description description) {
                description.appendText(" not all expected transitions where tested");
            }
        };
    }

    /**
     * Checks that all the expected transitions are present with targets in the TestResult object.
     *
     * @param transitions the transitions to find
     * @return true if all the expected transitions with their targets are present in the TestResult object
     */
    public static BaseMatcher<TestResult> containsAllActualTransitionsWithTargets(List<Transition> transitions) {
        return new BaseMatcher<TestResult>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object object) {
                if (!(object instanceof TestResult)) {
                    return false;
                }
                TestResult objectToTest = (TestResult) object;
                List<Test> testsRun = objectToTest.getTests();
                if (null == testsRun) {
                    return false;
                }

                List<Transition> matchedTransitions = new ArrayList<>();
                for (Transition transition : transitions) {
                    if (actualTransitionWithTargetHasTest(transition, testsRun)) {
                        matchedTransitions.add(transition);
                    }
                }

                return transitions.equals(matchedTransitions);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void describeTo(Description description) {
                description.appendText(transitions.stream()
                        .map(Transition::toStringWithStates)
                        .collect(Collectors.joining(", ")));
            }
        };
    }

    /**
     * Checks that all the Tests in the TestResult object are passed.
     *
     * @return true if all the tests are passed
     */
    public static BaseMatcher<TestResult> allTestArePassed() {
        return new BaseMatcher<TestResult>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object object) {
                return allTestsAre(object, Result.PASS);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void describeTo(Description description) {

            }
        };
    }

    /**
     * Checks that all the Tests in the TestResult object are failed.
     *
     * @return true if all the tests are failed
     */
    public static BaseMatcher<TestResult> allTestAreFailed() {
        return new BaseMatcher<TestResult>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object object) {
                return allTestsAre(object, Result.FAIL);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void describeTo(Description description) {

            }
        };
    }

    /**
     * Checks that all the Tests in the TestResult object have the expected result
     *
     * @param object the TestResult to check the Tests in
     * @param result the expected result of each Test
     * @return true if all the tests have the expected result
     */
    private static boolean allTestsAre(Object object, Result result) {
        if (!(object instanceof TestResult)) {
            return false;
        }

        TestResult objectToTest = (TestResult) object;

        for (Test test : objectToTest.getTests()) {
            if (!test.getResult().equals(result)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks the the expected transition has a test associated with it
     *
     * @param expected the expected transitions
     * @param testsRun the tests to look for the transition in
     * @return true if the expected transition has a test associated with it
     */
    private static boolean expectedTransitionHasTest(Transition expected, List<Test> testsRun) {
        for (Test test : testsRun) {
            if (test.getExpectedTransition().equals(expected.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the the actual transition has a test associated with it
     *
     * @param actual   the actual transitions
     * @param testsRun the tests to look for the transition in
     * @return true if the actual transition has a test associated with it
     */
    private static boolean actualTransitionHasTest(Transition actual, List<Test> testsRun) {
        for (Test test : testsRun) {
            if (test.getActualTransition().equals(actual.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the the expected transition with states has a test associated with it
     *
     * @param transition the expected transitions
     * @param testsRun   the tests to look for the transition in
     * @return true if the expected transition has a test associated with it
     */
    private static boolean expectedTransitionWithTargetHasTest(Transition transition, List<Test> testsRun) {
        for (Test test : testsRun) {
            if (test.getExpectedTransition().equals(transition.toStringWithStates())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the the actual transition with states has a test associated with it
     *
     * @param transition the actual transitions
     * @param testsRun   the tests to look for the transition in
     * @return true if the actual transition has a test associated with it
     */
    private static boolean actualTransitionWithTargetHasTest(Transition transition, List<Test> testsRun) {
        for (Test test : testsRun) {
            if (test.getActualTransition().equals(transition.toStringWithStates())) {
                return true;
            }
        }
        return false;
    }

}