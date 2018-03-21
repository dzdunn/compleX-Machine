package uk.ac.uwe.complexmachine.testutils;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-6.0
 */
public class MethodMatcher {

    /**
     * Asserts that the method contains the parameter type.
     * @param methodName the name of the method to check
     * @param parameterType the type of the parameter to look for
     * @return true if the parameter type is found in the parameters for the method
     */
    public static Matcher<Method[]> hasMethodWithParameter(String methodName, Class parameterType) {
        return new BaseMatcher<Method[]>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object methodToTest) {
                if (!(methodToTest instanceof Method[])) {
                    return false;
                }
                Method[] methods = (Method[]) methodToTest;
                for (Method method : methods) {
                    if (method.getName().equals(methodName) && hasParameter(method, parameterType)) {
                        return true;
                    }
                }
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void describeTo(Description description) {
                description.appendText(" could not find method " + methodName + " with parameter " + parameterType.getSimpleName());
            }
        };
    }

    /**
     * Checks if the method has a parameter matching the type.
     * @param method the method to check
     * @param parameterType the parameter type to look for
     * @return true if the parameter type is found in the method
     */
    private static boolean hasParameter(Method method, Class parameterType) {
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if (parameter.getType().equals(parameterType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks that a method exists in the method array.
     * @param returnType the return type of the method to look for
     * @param methodName the name of the method to look for
     * @return true if a method is found matching the return type and method name specified
     */
    public static Matcher<Method[]> hasMethodInArray(Class returnType, String methodName) {
        return new BaseMatcher<Method[]>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object methodToTest) {
                if (!(methodToTest instanceof Method[])) {
                    return false;
                }
                Method[] methods = (Method[]) methodToTest;
                for (Method method : methods) {
                    if (method.getName().equals(methodName) && method.getReturnType().equals(returnType)) {
                        return true;
                    }
                }
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void describeTo(Description description) {
                description.appendText(" could not find method " + returnType.getSimpleName() + "  " + methodName + " in the class");
            }
        };
    }
}
