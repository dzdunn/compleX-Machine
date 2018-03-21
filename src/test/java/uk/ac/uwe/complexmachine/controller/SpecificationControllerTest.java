package uk.ac.uwe.complexmachine.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXParseException;
import uk.ac.uwe.complexmachine.CompleXMachine;
import uk.ac.uwe.complexmachine.model.Specification;
import uk.ac.uwe.complexmachine.service.SpecificationConversionService;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.http.MediaType.TEXT_XML_VALUE;

/**
 * @author Clare Daly
 * @version alpha-1.0
 * @since alpha-1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CompleXMachine.class})
@WebAppConfiguration
public class SpecificationControllerTest {

    /**
     * The message key returned in the response from the controller.
     */
    private static final String MESSAGE_KEY = "message";
    /**
     * Mock of the specification conversion service.
     */
    @Mock
    private transient SpecificationConversionService mockService;

    /**
     * The controller being tested.
     */
    @InjectMocks
    private transient SpecificationController controller;

    /**
     * Sets up the mocks in the controller.
     * @throws Exception if there is an issue mocking the specification
     * return
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockService.createSpecificationFromFile((File)any())).thenReturn(new Specification());
    }

    /**
     * Tests that a success message is returned when an xml file is uploaded.
     */
    @Test
    public void shouldReturnASuccessMessageIfAnXMLFileIsPassedToTheUploadSpecificationMethod() throws IOException {
        // Given the specification controller

        // When passing an XML file to the controller
        MultipartFile mockFile = createValidMockFile();
        ResponseEntity result = controller.uploadSpecifications(mockFile);

        // Then the result should be the successful upload message
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("Result should be the successful upload message", resultBody, hasEntry(MESSAGE_KEY, "Uploaded testFile successfully"));
    }

    /**
     * Tests that a failure message is returned when a non-xml file is uploaded.
     */
    @Test
    public void shouldReturnAFailureMessageIfANonXMLFileIsPassedToTheUploadSpecificationMethod() {
        // Given the specification controller

        // When passing an XML file to the controller
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getContentType()).thenReturn(TEXT_PLAIN_VALUE);
        ResponseEntity result = controller.uploadSpecifications(mockFile);

        // Then the result should be the wrong file type upload message
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("Result should be the wrong file type upload message", resultBody, hasEntry(MESSAGE_KEY, "Incompatible file type"));
    }

    /**
     * Tests that a failure message is returned if there is a problem reading the
     * file by the service.
     * @throws JAXBException if there is a parsing error by the service
     * @throws IOException if there is a problem creating the mock file
     */
    @Test
    public void shouldReturnAFailureMessageIfThereIsAProblemReadingTheInputFileInTheService() throws JAXBException, IOException {
        // Given the specification controller

        // When passing an XML file to the controller which the service cannot read
        MultipartFile mockFile = createValidMockFile();
        when(mockService.createSpecificationFromFile((File)any())).thenThrow(IOException.class);
        ResponseEntity result = controller.uploadSpecifications(mockFile);

        // Then the result should be the cannot convert file message
        Map<String, String> resultBody = (Map<String, String>)result.getBody();
        assertThat("Result should be the cannot convert file message", resultBody.get(MESSAGE_KEY), startsWith("Could not convert file: "));
    }

    /**
     * Tests that a failure message is returned if the there is a SAXParseException
     * thrown by the service
     * @throws IOException if there is a problem creating the mock file
     * @throws JAXBException if there is a problem parsing the XML
     */
    @Test
    public void shouldReturnAFailureMessageIfTheXMLIsInvalid() throws IOException, JAXBException {
        // Given the specification controller

        // When passing an invalid XML file to the controller
        MultipartFile mockFile = createValidMockFile();
        JAXBException expectedException = new JAXBException("JAXBError");
        expectedException.setLinkedException(new SAXParseException("SAXError", "PublicId", "System", 2, 1));
        when(mockService.createSpecificationFromFile((File)any())).thenThrow(expectedException);
        ResponseEntity result = controller.uploadSpecifications(mockFile);

        // Then the result should be the cannot convert file message with line numbers
        Map<String, String> resultBody = (Map<String, String>)result.getBody();
        assertThat("Result should be the cannot convert file message", resultBody, hasEntry(MESSAGE_KEY, "Could not convert file: error at line 2 column 1: SAXError"));
    }

    /**
     * Tests that a failure message is returned if there is a JAXB error thrown by
     * the service
     * @throws IOException if there is a problem creating the mock file
     * @throws JAXBException if there is a problem parsing the XML
     */
    @Test
    public void shouldReturnAFailureMessageIfTheIsAProblemParsingTheXML() throws IOException, JAXBException {
        // Given the specification controller

        // When passing an XML file to the controller which the service cannot read
        MultipartFile mockFile = createValidMockFile();
        JAXBException expectedException = new JAXBException("Error");
        when(mockService.createSpecificationFromFile((File)any())).thenThrow(expectedException);
        ResponseEntity result = controller.uploadSpecifications(mockFile);

        // Then the result should be the cannot convert file message
        Map<String, String> resultBody = (Map<String, String>)result.getBody();
        assertThat("Result should be the cannot convert file message", resultBody, hasEntry(MESSAGE_KEY, "Could not convert file: Error"));
    }

    /**
     * Creates a valid mock file to use in testing.
     * @return the valid mock file
     * @throws IOException if there is an error creating the mock file
     */
    private MultipartFile createValidMockFile() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getContentType()).thenReturn(TEXT_XML_VALUE);
        when(mockFile.getOriginalFilename()).thenReturn("testFile");
        when(mockFile.getBytes()).thenReturn(new byte[0]);
        return mockFile;
    }
}
