package uk.ac.uwe.complexmachine.service;

import com.github.javaparser.ast.CompilationUnit;
import uk.ac.uwe.complexmachine.CompleXException;
import uk.ac.uwe.complexmachine.model.*;
import uk.ac.uwe.complexmachine.utils.ClassCreator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-6.0
 */
public class MisdirectedTransitionsTestService extends AbstractTestService {

    /**
     * Compares the specification to the system to identify transitions
     * which do not end at the correct state.
     * @param specification the specification to test against
     * @param system        the system to look for misdirected transitions in
     * @return the results of the test
     * @throws CompleXException if there is an error creating the class to test
     */
    @Override
    public TestResult runTest(Specification specification, CompilationUnit system) throws CompleXException {
        Class classToTest;

        classToTest = ClassCreator.createClassFromCompilationUnit(system);

        TestResult testResult = new TestResult();
        if (specification.getTransitions() != null) {
            for (Transition transition : specification.getTransitions()) {
                Test test = new Test();
                test.setExpectedTransition(transition.toStringWithStates());
                testTransition(test, transition, classToTest);
                testResult.addTest(test);
            }
        }

        return testResult;
    }

    /**
     * Tests the transition to ensure the final state is reached.
     *
     * @param test        the test being run
     * @param transition  the transition to test for
     * @param classToTest the class to test the transition in
     * @throws CompleXException if there is an issue creating the class to test
     */
    private void testTransition(Test test, Transition transition, Class classToTest) throws CompleXException {
        Result result = Result.FAIL;
        State actualEndingState = new State();
        try {
            Object objectToTest = classToTest.newInstance();
            State startingState = transition.getStartingState();
            State finishingState = transition.getFinishingState();
            Field[] fields = classToTest.getDeclaredFields();
            for (Field field : fields) {
                setFieldValue(objectToTest, startingState, field);
            }
            Method[] methods = classToTest.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(transition.getName())) {
                    method.invoke(objectToTest);
                }
            }
            for (Field field : fields) {
                for (Variable variable : finishingState.getVariables()) {
                    Variable actualVariable = new Variable();
                    actualVariable.setType(field.getType().getCanonicalName());
                    actualVariable.setName(field.getName());
                    actualVariable.setValue(field.get(objectToTest));
                    actualEndingState.addVariable(actualVariable);
                    if (field.get(objectToTest).equals(variable.getValue())) {
                        result = Result.PASS;
                        break;
                    }
                }
                if (result.equals(Result.FAIL)) {
                    break;
                }
            }
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException exception) {
            throw new CompleXException("Could not run test on created class", exception);
        }
        test.setActualTransition(transition.getStartingState().getState() + " -> " + transition.toString() + " -> " + actualEndingState.getState());
        test.setResult(result);
    }

    /**
     * Sets the field value from the state object.
     *
     * @param objectToTest  The object to set the value in
     * @param startingState the state to get the value from
     * @param field         the field to set the value for
     * @throws IllegalAccessException if the field cannot be accessed
     */
    private void setFieldValue(Object objectToTest, State startingState, Field field) throws IllegalAccessException {
        for (Variable variable : startingState.getVariables()) {
            if (field.getName().equals(variable.getName())) {
                field.setAccessible(true);
                field.set(objectToTest, variable.getValue());
            }
        }
    }
}
