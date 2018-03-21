package uk.ac.uwe.complexmachine.utils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import org.junit.Test;
import uk.ac.uwe.complexmachine.CompleXException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertThat;
import static uk.ac.uwe.complexmachine.testutils.FieldMatcher.hasFieldInArray;
import static uk.ac.uwe.complexmachine.testutils.MethodMatcher.hasMethodInArray;
import static uk.ac.uwe.complexmachine.testutils.MethodMatcher.hasMethodWithParameter;

/**
 * @author Clare Daly
 * @version alpha-6.0
 * @since alpha-6.0
 */
public class ClassCreatorTest {
    /**
     * Tests that a class object is returned from the ClassCreator
     */
    @Test
    public void shouldReturnAClassObject() throws CompleXException {
        // Given the class creator

        // When creating a class
        Object result = ClassCreator.createClassFromCompilationUnit(new CompilationUnit());

        // Then the result should be a class object
        assertThat("Result should create class object", result, instanceOf(Class.class));
    }

    /**
     * Tests that a single variable from the CompilationUnit can be translated into
     * the created class.
     */
    @Test
    public void shouldReturnAClassWithASingleVariableFromTheCompilationUnit() throws CompleXException {
        // Given a compilation unit with a single variable color of type String
        CompilationUnit compilationUnit = new CompilationUnit();
        TypeDeclaration type = new ClassOrInterfaceDeclaration();
        type.addPublicField("java.lang.String", "color");
        compilationUnit.addType(type);

        // When creating a class
        Class result = ClassCreator.createClassFromCompilationUnit(compilationUnit);

        // Then the result should have a single variable called color of type String
        assertThat("Result should have a single variable called color of type String", result.getDeclaredFields(), both(hasFieldInArray(String.class, "color")).and(arrayWithSize(1)));
    }

    /**
     * Tests that multiple variables from the CompilationUnit can be translated into
     * the created class
     */
    @Test
    public void shouldReturnAClassWithMultipleFieldsFromTheCompilationUnit() throws CompleXException {
        // Given a compilation unit with multiple fields
        CompilationUnit compilationUnit = new CompilationUnit();
        TypeDeclaration type = new ClassOrInterfaceDeclaration();
        type.addPublicField("java.lang.String", "color");
        type.addPrivateField("java.lang.Integer", "count");
        type.addPublicField("java.lang.Object", "value");
        compilationUnit.addType(type);

        // When creating a class
        Class result = ClassCreator.createClassFromCompilationUnit(compilationUnit);

        // Then the result should have multiple variables
        assertThat("Result should have multiple variables from the CompilationUnit", result.getDeclaredFields(), allOf(hasFieldInArray(String.class, "color"), hasFieldInArray(Integer.class, "count"), hasFieldInArray(Object.class, "value")));
    }

    /**
     * Tests that a single method from the CompilationUnit can be translated into
     * the created class
     */
    @Test
    public void shouldReturnAClassWithASingleMethodFromTheCompilationUnit() throws CompleXException {
        // Given a compilation unit with a single method
        CompilationUnit compilationUnit = new CompilationUnit();
        TypeDeclaration type = new ClassOrInterfaceDeclaration();
        MethodDeclaration method = new MethodDeclaration();
        method.setName("go");
        method.setType("java.lang.String");
        BlockStmt methodBody = new BlockStmt();
        methodBody.addStatement("return null;");
        method.setBody(methodBody);
        type.addMember(method);
        compilationUnit.addType(type);

        // When creating a class
        Class result = ClassCreator.createClassFromCompilationUnit(compilationUnit);

        // Then the result should have a single method
        assertThat("Result should have a single method", result.getDeclaredMethods(), both(hasMethodInArray(String.class, "go")).and(arrayWithSize(1)));
    }

    /**
     * Tests that multiple methods from the compilation unit can be translated into
     * the created class
     */
    @Test
    public void shouldReturnAClassWithMultipleMethodsFromTheCompilationUnit() throws CompleXException {
        // Given a compilation unit with multiple methods
        CompilationUnit compilationUnit = new CompilationUnit();
        TypeDeclaration type = new ClassOrInterfaceDeclaration();
        MethodDeclaration firstMethod = new MethodDeclaration();
        firstMethod.setName("go");
        firstMethod.setType("java.lang.String");
        BlockStmt methodBody = new BlockStmt();
        methodBody.addStatement("return null;");
        firstMethod.setBody(methodBody);
        MethodDeclaration secondMethod = new MethodDeclaration();
        secondMethod.setName("stop");
        secondMethod.setType("java.lang.String");
        BlockStmt secondMethodBody = new BlockStmt();
        secondMethodBody.addStatement("return new String();");
        secondMethod.setBody(secondMethodBody);
        type.addMember(firstMethod);
        type.addMember(secondMethod);
        compilationUnit.addType(type);

        // When creating a class
        Class result = ClassCreator.createClassFromCompilationUnit(compilationUnit);

        // Then the result should have both methods
        assertThat("Result should have multiple methods from the CompilationUnit", result.getDeclaredMethods(), allOf(hasMethodInArray(String.class, "go"), hasMethodInArray(String.class, "stop")));
    }

    /**
     * Tests that a method with a variable from the CompilationUnit can be
     * translated into the class.
     */
    @Test
    public void shouldReturnAClassWithAMethodWithParameters() throws CompleXException {
        // Given a compilation unit with a single method
        CompilationUnit compilationUnit = new CompilationUnit();
        TypeDeclaration type = new ClassOrInterfaceDeclaration();
        MethodDeclaration method = new MethodDeclaration();
        method.setName("go");
        method.setType("java.lang.String");
        com.github.javaparser.ast.body.Parameter parameter = new com.github.javaparser.ast.body.Parameter();
        parameter.setName("color");
        parameter.setType(String.class);
        NodeList parameters = new NodeList();
        parameters.add(parameter);
        method.setParameters(parameters);
        BlockStmt methodBody = new BlockStmt();
        methodBody.addStatement("return null;");
        method.setBody(methodBody);
        type.addMember(method);
        compilationUnit.addType(type);

        // When creating a class
        Class result = ClassCreator.createClassFromCompilationUnit(compilationUnit);

        // Then the result should have a single method
        assertThat("Result should have a single method", result.getDeclaredMethods(), hasMethodWithParameter("go", String.class));
    }
}
