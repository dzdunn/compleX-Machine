package uk.ac.uwe.complexmachine.controller;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.uwe.complexmachine.CompleXMachine;
import org.junit.Test;
import uk.ac.uwe.complexmachine.service.JavaConversionService;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Danny Dunn
 * @version alpha-2.0
 * @since alpha-2.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CompleXMachine.class})
@WebAppConfiguration
public class SystemControllerTest {
    /**
     * The message key returned in the response from the controller.
     */
    private static final String MESSAGE_KEY = "message";
    /**
     * The error message key returned in for incompatible file types.
     */
    private static final String INCOMPATIBLE_FILE_TYPE = "Incompatible file type";
    /**
     * Mock of the system conversion service.
     */
    @Mock
    private transient JavaConversionService mockService;

    /**
     * The controller being tested.
     */
    @InjectMocks
    private transient SystemController controller;

    /**
     * Sets up the mocks in the controller.
     * @throws Exception if there is an issue mocking the system
     * return
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockService.createJavaFromFile((File)any())).thenReturn(new CompilationUnit());
    }

    /**
     * Tests that a success message is returned when a valid .java file is uploaded.
     */
    @Test
    public void shouldReturnSuccessMessageifJavaReturnedToUploadSystem() throws IOException {
        // Given the system controller

        // When passing an .java file to the controller
        MultipartFile mockFile = createValidMockFile();
        ResponseEntity result = controller.uploadSystem(mockFile);

        // Then the result should be the successful upload message
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("Result should be the successful upload message", resultBody, hasEntry(MESSAGE_KEY, "Uploaded Test.java successfully"));
    }

    /**
     * Tests that a failure message is returned when a capitalised .JAVA extension file is uploaded.
     */
    @Test
    public void shouldReturnAFailureMessageIfACapitalisedJavaExtensionFileIsPassedToTheUploadSystemMethod() {
        // Given the system controller

        // When passing an test.JAVA file to the controller
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.JAVA");
        ResponseEntity result = controller.uploadSystem(mockFile);

        // Then the result should be the wrong file type upload message
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("Result should be the wrong file type upload message", resultBody, hasEntry(MESSAGE_KEY, INCOMPATIBLE_FILE_TYPE));
    }

    /**
     * Tests that a failure message is returned when an extension follows .java in the uploaded file.
     */
    @Test
    public void shouldReturnAFailureMessageIfAJavaFileWithTwoExtensionsIsPassedToTheUploadSystemMethod() {
        // Given the system controller

        // When passing an test.java.xml file to the controller
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.java.xml");
        ResponseEntity result = controller.uploadSystem(mockFile);

        // Then the result should be the wrong file type upload message
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("Result should be the wrong file type upload message", resultBody, hasEntry(MESSAGE_KEY, INCOMPATIBLE_FILE_TYPE));
    }

    /**
     * Tests that a failure message is returned when a .java extension is missing part of the extension.
     */
    @Test
    public void shouldReturnAFailureMessageIfAnIncompleteJavaExtensionFileIsPassedToTheUploadSystemMethod() {
        // Given the system controller

        // When passing an .ja file to the controller
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.ja");
        ResponseEntity result = controller.uploadSystem(mockFile);

        // Then the result should be the wrong file type upload message
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("Result should be the wrong file type upload message", resultBody, hasEntry(MESSAGE_KEY, INCOMPATIBLE_FILE_TYPE));
    }

    /**
     * Tests that a failure message is returned when a .java file is uploaded without a prefix.
     */
    @Test
    public void shouldReturnAFailureMessageIfAnUnnamedJavaFileIsPassedToTheUploadSystemMethod() {
        // Given the system controller

        // When passing an .java file to the controller
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn(".java");
        ResponseEntity result = controller.uploadSystem(mockFile);

        // Then the result should be the wrong file type upload message
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("Result should be the wrong file type upload message", resultBody, hasEntry(MESSAGE_KEY, INCOMPATIBLE_FILE_TYPE));
    }

    /**
     * Tests that a failure message is returned when a .java extension is preceded by another extension in the uploaded file.
     */
    @Test
    public void shouldReturnAFailureMessageIfJavaIsPreceededByAnotherExtensionPassedToTheUploadSystemMethod() {
        // Given the system controller

        // When passing an Test.xml.java file to the controller
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("Test.xml.java");
        ResponseEntity result = controller.uploadSystem(mockFile);

        // Then the result should be the wrong file type upload message
        Map<String, String> resultBody = (Map<String, String>) result.getBody();
        assertThat("Result should be the wrong file type upload message", resultBody, hasEntry(MESSAGE_KEY, INCOMPATIBLE_FILE_TYPE));
    }

    /**
     * Tests that a failure message is returned if there is a problem reading the
     * file by the service.
     * @throws IOException if there is a problem creating the mock file
    */
    @Test
    public void shouldReturnAFailureMessageIfThereIsAProblemReadingTheInputFileInTheService() throws IOException {
        // Given the system controller

        // When passing an .java file to the controller which the service cannot read
        MultipartFile mockFile = createValidMockFile();
        when(mockService.createJavaFromFile(((File)any()))).thenThrow(IOException.class);
        ResponseEntity result = controller.uploadSystem(mockFile);

        // Then the result should be the cannot convert file message
        Map<String, String> resultBody = (Map<String, String>)result.getBody();
        assertThat("Result should be the cannot convert file message", resultBody.get(MESSAGE_KEY), startsWith("Could not convert file: "));
    }

    /**
     * Creates a valid mock file to use in testing.
     * @return the valid mock file
     * @throws IOException if there is an error creating the mock file
     */
    private MultipartFile createValidMockFile() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("Test.java");
        when(mockFile.getBytes()).thenReturn(new byte[0]);
        return mockFile;
    }
}