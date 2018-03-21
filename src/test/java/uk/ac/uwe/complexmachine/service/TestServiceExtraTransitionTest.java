package uk.ac.uwe.complexmachine.service;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;
import uk.ac.uwe.complexmachine.model.Specification;
import uk.ac.uwe.complexmachine.model.TestResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.ac.uwe.complexmachine.testutils.TestHelper.createTransitionList;
import static uk.ac.uwe.complexmachine.testutils.TestResultMatcher.*;

/**
 * @author Clare Daly
 * @version alpha-4.0
 * @since alpha-3.0
 */
public class TestServiceExtraTransitionTest {

    /**
     * Tests that running the test returns a TestResult object.
     * @throws FileNotFoundException if the test java file cannot be found
     */
    @Test
    public void testShouldReturnATestResultObject() throws FileNotFoundException {
        // Given the Test Service
        ExtraTransitionTestService extraTransitionTestService = new ExtraTransitionTestService();
        Specification testSpecification = mock(Specification.class);
        when(testSpecification.getTransitions()).thenReturn(new ArrayList<>());

        // When running a test
        Object result = extraTransitionTestService.runTest(testSpecification, JavaParser.parse(getFile("Methods.java")));

        // A test result should be returned
        assertThat("A TestResult object should be returned", result, both(notNullValue()).and(instanceOf(TestResult.class)));
    }

    @Test
    public void shouldContainAllTheSystemTransitionsInTheTestResultObject() throws FileNotFoundException {
        // Given the test service and a known list of actual transitions
        ExtraTransitionTestService extraTransitionTestService = new ExtraTransitionTestService();
        CompilationUnit testSystem = JavaParser.parse(getFile("Methods.java"));
        Specification testSpecification = mock(Specification.class);
        when(testSpecification.getTransitions()).thenReturn(createTransitionList());

        // When running a test
        TestResult result = extraTransitionTestService.runTest(testSpecification, testSystem);

        // A test result with all the actual transitions should be returned
        assertThat("The TestResult object should contain all the actual transitions", result, containsAllActualTransitions(createTransitionList()));
    }

    @Test
    public void shouldPassAllActualTransitionsWhenTheyAppearInTheSpecification() throws FileNotFoundException {
        // Given the test service and a known list of actual transitions
        ExtraTransitionTestService extraTransitionTestService = new ExtraTransitionTestService();
        CompilationUnit testSystem = JavaParser.parse(getFile("Methods.java"));
        Specification testSpecification = mock(Specification.class);
        when(testSpecification.getTransitions()).thenReturn(createTransitionList());

        // When running a test
        TestResult result = extraTransitionTestService.runTest(testSpecification, testSystem);

        // Then all the actual transitions should pass when they appear in the specification
        assertThat("The Tests should all pass for actual transitions that appear in the specification", result, allTestArePassed());
    }

    @Test
    public void shouldFailAllActualTransitionsWhenTheyDoNotAppearInTheSpecification() throws FileNotFoundException {
        // Given the test service and a know list of actual transitions
        ExtraTransitionTestService extraTransitionTestService = new ExtraTransitionTestService();
        CompilationUnit testSystem = JavaParser.parse(getFile("Methods.java"));
        Specification testSpecification = mock(Specification.class);

        // When running a test
        TestResult result = extraTransitionTestService.runTest(testSpecification, testSystem);

        // Then all the actual transitions should fail when they do not appear in the specification
        assertThat("The Tests should all false for expected transitions that do not appear in the system", result, allTestAreFailed());
    }

    @Test
    public void shouldFailAllActualTransitionsWhenTransitionsAppearInSpecificationWithIncorrectParameters() throws FileNotFoundException {
        // Given the test service and a know list of expected transitions
        ExtraTransitionTestService extraTransitionTestService = new ExtraTransitionTestService();
        CompilationUnit testSystem = JavaParser.parse(getFile("MethodsWrongParameters.java"));
        Specification testSpecification = mock(Specification.class);
        when(testSpecification.getTransitions()).thenReturn(createTransitionList());

        // When running a test
        TestResult result = extraTransitionTestService.runTest(testSpecification, testSystem);

        // Then all the actual transitions should fail when they do not appear in the specification
        assertThat("The Tests should all false for actual transitions that appear in the specification with incorrect parameters", result, allTestAreFailed());
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
