package uk.ac.uwe.complexmachine.testutils;

import com.github.javaparser.ast.CompilationUnit;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.lang.reflect.Field;

/**
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-6.0
 */
public class FieldMatcher {
    /**
     * Private constructor to prevent instantiation.
     */
    private FieldMatcher() {
        // Private constructor
    }

    /**
     * Checks that the field array contains the specified field
     * @param type the type of field to look for
     * @param fieldName the name of the field to look for
     * @return true if the field is found in the array
     */
    public static Matcher<Field[]> hasFieldInArray(Class<?> type, String fieldName) {
        return new BaseMatcher<Field[]>() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void describeTo(Description description) {
                description.appendText(" could not find field of type " + type.getSimpleName() + " and name " + fieldName + " in the class");
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object fieldToTest) {
                if(!(fieldToTest instanceof Field[])) {
                    return false;
                }
                Field[] fields = (Field[]) fieldToTest;
                for(Field field: fields) {
                    if(field.getName().equals(fieldName) && field.getType().equals(type)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
