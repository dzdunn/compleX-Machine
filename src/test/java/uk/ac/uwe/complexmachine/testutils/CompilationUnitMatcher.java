package uk.ac.uwe.complexmachine.testutils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;

/**
 * @author Clare Daly
 * @version alpha-2.0
 * @since alpha-2.0
 */
public final class CompilationUnitMatcher {
    /**
     * Private constructor to prevent instantiation.
     */
    private CompilationUnitMatcher() {
        // Private constructor
    }

    /**
     * Looks for the method within the compilation object.
     * @param methodName the method name to find
     * @return true if the method name exists in the CompilationUnit
     */
    public static Matcher<CompilationUnit> containsMethod(String methodName) {
        return new BaseMatcher<CompilationUnit>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object object) {
                if(!(object instanceof CompilationUnit)) {
                    return false;
                }

                for (TypeDeclaration typeDec : ((CompilationUnit) object).getTypes()) {
                    List<BodyDeclaration> members = typeDec.getMembers();
                    if (members == null) {
                        return false;
                    }
                    if (methodExistsInBodyDeclarations(members, methodName)) {
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
                description.appendText(" should have method " + methodName);
            }
        };
    }

    /**
     * Looks for the variable within the CompilationUnit object.
     * @param variable the variable to find
     * @return true if the variable exists in the CompilationUnit
     */
    public static Matcher<CompilationUnit> containsVariable(VariableDeclarator variable) {
        return new BaseMatcher<CompilationUnit>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean matches(Object object) {
                if (!(object instanceof CompilationUnit)) {
                    return false;
                }
                for (TypeDeclaration typeDec : ((CompilationUnit) object).getTypes()) {
                    List<BodyDeclaration> members = typeDec.getMembers();
                    if (members == null) {
                        return false;
                    }
                    if (variableExistsInBodyDeclarations(members, variable)) {
                        return true;
                    }
                }
                return false;
            }

            /**
             * {@inheritDoc}
             * @param description
             */
            @Override
            public void describeTo(Description description) {
                description.appendText(" should have variable");
            }
        };
    }

    /**
     * Iterates through the list of body declarations to find the specified variable.
     * @param members the list of BodyDeclarations to iterate through
     * @param variable the variable to find in the list
     * @return true if the variable is found in the body declaration list
     */
    private static boolean variableExistsInBodyDeclarations(List<BodyDeclaration> members, VariableDeclarator variable) {
        return members
                .stream()
                .anyMatch(member -> variableExistsInFieldDeclaration((FieldDeclaration)member, variable));
    }

    /**
     * Iterates through the list of body declarations to find the specified method.
     * @param members the list of BodyDeclarations to iterate through
     * @param methodName the method name to find in the list
     * @return true if the method is found in the body declaration list
     */
    private static boolean methodExistsInBodyDeclarations(List<BodyDeclaration> members, String methodName) {
        return members
                .stream()
                .anyMatch(member -> methodExistsInMethodDeclaration((MethodDeclaration)member, methodName));
    }

    /**
     * Compares the name of the method declaration to the method name string.
     * @param method the method to get the name of
     * @param methodName the string to compare to
     * @return true if the method names match
     */
    private static boolean methodExistsInMethodDeclaration(MethodDeclaration method, String methodName) {
        return method.getNameAsString().equals(methodName);
    }

    /**
     * Iterates through the variables in the field declaration to find the variable.
     * @param field The field to find the variable in
     * @param variable The variable to find
     * @return true if the variable exists in the field
     */
    private static boolean variableExistsInFieldDeclaration(FieldDeclaration field, VariableDeclarator variable) {
        return field.getVariables()
                .stream()
                .anyMatch(cuVariable -> cuVariable.getType().equals(variable.getType()) &&
                        cuVariable.getNameAsString().equals(variable.getNameAsString()));
    }
}
