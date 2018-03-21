package uk.ac.uwe.complexmachine.service;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import static com.github.javaparser.ast.type.PrimitiveType.intType;
import static org.hamcrest.CoreMatchers.both;
import static org.junit.Assert.assertThat;
import static uk.ac.uwe.complexmachine.testutils.CompilationUnitMatcher.*;

/**
 * @author Clare Daly
 * @version alpha-2.0
 * @since alpha-2.0
 */
public class JavaConversionServiceTest {

    /**
     * Tests that the variables of a java file are retrieved.
     * @throws FileNotFoundException if the java file cannot be found in the system
     */
    @Test
    public void testShouldGetVariablesFromJavaFile() throws FileNotFoundException {
        // Given the java conversion service and a java file with variables
        JavaConversionService service = new JavaConversionService();
        File testFile = getFile("Variables.java");

        // When converting the file
        CompilationUnit result = service.createJavaFromFile(testFile);

        // Then the compilation unit should contain the two variables
        VariableDeclarator stringVariable = new VariableDeclarator(new ClassOrInterfaceType("String"), "stringVariable");
        VariableDeclarator intVariable = new VariableDeclarator(intType(), "intVariable");
        assertThat("CompilationUnit should contain the two variables", result, both(containsVariable(stringVariable)).and(containsVariable(intVariable)));
    }

    /**
     * Tests that the methods of a java file are retrieved.
     * @throws FileNotFoundException if the java file cannot be found in the systems
     */
    @Test
    public void testShouldGetTheMethodsFromJavaFile() throws FileNotFoundException {
        // Given the java conversion service and a java file with methods
        JavaConversionService service = new JavaConversionService();
        File testFile = getFile("Methods.java");

        // When converting the file
        CompilationUnit result = service.createJavaFromFile(testFile);

        // Then the compilation unis should contain the two methods
        String firstMethod = "firstMethod";
        String secondMethod = "secondMethod";
        assertThat("Compilation unit should contain the two methods", result, both(containsMethod(firstMethod)).and(containsMethod(secondMethod)));
    }

    /**
     * Gets the test file from the test resources folder within the project.
     * @param fileName the name of the file to retrieve
     * @return the XML file from the test resources folder
     */
    private File getFile(String fileName) {
        final URL resource = Thread.currentThread().getContextClassLoader().getResource(fileName);
        // Here we need to ensure the path to the file does not have any
        // space encoded, in case of space appearing in the directory or
        // file names e.g. "My Documents", "Program Files",
        // "Test File.xml"
        return new File(resource.getFile().replace("%20", " "));
    }
}
