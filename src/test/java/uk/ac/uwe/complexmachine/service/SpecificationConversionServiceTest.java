package uk.ac.uwe.complexmachine.service;

import org.junit.Test;
import uk.ac.uwe.complexmachine.model.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Clare Daly
 * @version alpha-1.0
 * @since alpha-1.0
 */
public class SpecificationConversionServiceTest {

    /**
     * Tests that a {@link Specification} object is created from an XML
     * file with a specification root.
     * @throws JAXBException if there is an error parsing the XML file
     */
    @Test
    public void shouldReturnASpecificationObjectIfRootOfDocumentIsSpecification() throws JAXBException {
        // Given the specification conversion service and an xml document
        // a specification root
        SpecificationConversionService service = new SpecificationConversionService();
        File testFile = getFile("root.xml");

        // When converting a file
        Object result = service.createSpecificationFromFile(testFile);

        // Then the result should be a specification object
        assertThat("Should create a specification object", result, instanceOf(Specification.class));
    }

    /**
     * Tests that an exception is thrown if the XML file does not have
     * a specification root element.
     * @throws JAXBException if there is an error parsing the XML file
     */
    @Test(expected = JAXBException.class)
    public void shouldReturnAsErrorIfThereIsNoSpecificationRoot() throws JAXBException {
        // Given the specification conversion service and an xml document
        // with an incorrect root
        SpecificationConversionService service = new SpecificationConversionService();
        File testFile = getFile("incorrectRoot.xml");

        // When converting a file
        service.createSpecificationFromFile(testFile);

        // Then an exception should be thrown
    }

    /**
     * Tests that an empty transitions XML tag will result in a
     * transitions list.
     * @throws JAXBException if there is an error parsing the XML file
     */
    @Test
    public void shouldCreateASpecificationObjectWithATransitionList() throws JAXBException {
        // Given the specification conversion service and xml document
        // with an empty transitions wrapper
        SpecificationConversionService service = new SpecificationConversionService();
        File testFile = getFile("emptyTransitions.xml");

        // When converting the file
        Specification result = service.createSpecificationFromFile(testFile);

        // Then the result should be a specification object with a
        // transitions list
        assertThat("Specification should contain an empty transitions list", result.getTransitions(), is(notNullValue()));
    }

    /**
     * Tests that a transition tag is converted into a transition
     * @throws JAXBException if there is an error parsing the XML file
     */
    @Test
    public void shouldCreateATransitionObjectFromTheXMLFile() throws JAXBException {
        // Given the specification conversion service and xml document
        // with a transition element
        SpecificationConversionService service = new SpecificationConversionService();
        File testFile = getFile("transition.xml");

        // When converting the file
        Specification result = service.createSpecificationFromFile(testFile);

        // Then the result should be a specification object with a
        // transition object
        Transition testTransition = new Transition();
        testTransition.setName("testTransition");
        assertThat("Specification should contain the transition", result.getTransitions(), hasItem(testTransition));
    }

    /**
     * Tests that an empty parameters XML tag will result in a
     * parameter list.
     * @throws JAXBException if there is an error parsing the XML file
     */
    @Test
    public void shouldCreateASpecificationObjectWithAParameterList() throws JAXBException {
        // Given the specification conversion service and xml document
        // with an empty parameter wrapper
        SpecificationConversionService service = new SpecificationConversionService();
        File testFile = getFile("emptyParameters.xml");

        // When converting the file
        Specification result = service.createSpecificationFromFile(testFile);

        // Then the result should be a specification object with a
        // transition object with a parameter list
        assertThat("Specification should contain an empty parameters list", result.getTransitions().get(0).getParameters(), is(notNullValue()));
    }

    /**
     * Tests that a parameter tag is converted into a transition
     * @throws JAXBException if there is an error parsing the XML file
     */
    @Test
    public void shouldCreateAParameterObjectFromTheXMLFile() throws JAXBException {
        // Given the specification conversion service and xml document
        // with a parameter element
        SpecificationConversionService service = new SpecificationConversionService();
        File testFile = getFile("parameter.xml");

        // When converting the file
        Specification result = service.createSpecificationFromFile(testFile);

        // Then the result should be a specification object with a
        // transition object with a parameter object
        Parameter testParameter = new Parameter();
        testParameter.setType("String");
        testParameter.setValue("testParameter");
        assertThat("Specification should contain the parameter", result.getTransitions().get(0).getParameters(), hasItem(testParameter));
    }

    /**
     * Tests that the starting state tag is converted into the starting State object.
     * @throws JAXBException if there is an error parsing the file
     */
    @Test
    public void shouldCreateAStartingStateObjectFromTheXMLFile() throws JAXBException {
        // Give the specification conversion service and xml document
        // with a starting state element
        SpecificationConversionService service = new SpecificationConversionService();
        File testFile = getFile("startingState.xml");

        // When converting the file
        Specification result = service.createSpecificationFromFile(testFile);

        // Then the result should be a specification object with a transition
        // object with a starting state object
        State testState = new State();
        testState.setName("startingState");
        assertThat("Specification should contain the starting state", result.getTransitions().get(0).getStartingState(), is(testState));
    }

    /**
     * Tests that the finishing state tag is converted into the finishing State
     * object.
     * @throws JAXBException if there is an error parsing the file
     */
    @Test
    public void shouldCreateAFinishingStateObjectFromTheXMLFile() throws JAXBException {
        // Give the specification conversion service and xml document
        // with a finishing state element
        SpecificationConversionService service = new SpecificationConversionService();
        File testFile = getFile("finishingState.xml");

        // When converting the file
        Specification result = service.createSpecificationFromFile(testFile);

        // Then the result should be a specification object with a transition
        // object with a finishing state object
        State testState = new State();
        testState.setName("finishingState");
        assertThat("Specification should contain the finishing state", result.getTransitions().get(0).getFinishingState(), is(testState));
    }

    /**
     * Tests that an empty variables tag will result in an empty
     * variables list.
     * @throws JAXBException if there is an error parsing the xmlFile
     */
    @Test
    public void shouldCreateASpecificationObjectWithAVariableList() throws JAXBException {
        // Given the specification conversion service and xml document
        // with an empty variable wrapper
        SpecificationConversionService service = new SpecificationConversionService();
        File testFile = getFile("emptyVariables.xml");

        // When converting the file
        Specification result = service.createSpecificationFromFile(testFile);

        // Then the result should be a specification object with a
        // transition object with a starting state with an empty
        // variable list
        assertThat("Specification should contain an empty variable list", result.getTransitions().get(0).getStartingState().getVariables(), is(notNullValue()));
    }



    /**
     * Tests that a variable tag is converted into a variable
     * @throws JAXBException if there is an error parsing the XML file
     */
    @Test
    public void shouldCreateAVariableObjectFromTheXMLFile() throws JAXBException {
        // Given the specification conversion service and xml document
        // with a variable element
        SpecificationConversionService service = new SpecificationConversionService();
        File testFile = getFile("variable.xml");

        // When converting the file
        Specification result = service.createSpecificationFromFile(testFile);

        // Then the result should be a specification object with a
        // transition object with a starting state with a variable object
        Variable testVariable = new Variable();
        testVariable.setName("testVariable");
        assertThat("Specification should contain the variable", result.getTransitions().get(0).getStartingState().getVariables(), hasItem(testVariable));
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
