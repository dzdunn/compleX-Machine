package uk.ac.uwe.complexmachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXParseException;
import uk.ac.uwe.complexmachine.model.Specification;
import uk.ac.uwe.complexmachine.service.SpecificationConversionService;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.MediaType.TEXT_XML_VALUE;

/**
 * @author Clare Daly
 * @version alpha-2.0
 * @since alpha-0.1
 */
@Controller
public class SpecificationController {
    /**
     * The key for message in the controller response.
     */
    private static final String MESSAGE_KEY = "message";

    /**
     * Autowired instance of the specification conversion service to
     * create the {@link Specification} from the specification xml file
     */
    @Autowired
    private transient SpecificationConversionService conversionService;

    /**
     * Error message in case of conversion failure.
     */
    private static final String CONVERSION_ERROR = "Could not convert file: ";

    /**
     * Receives the specification file from the view. Checks that the
     * file is an XML file.
     *
     * @param file the specification file set from the view
     * @return a confirmation the file has been uploaded successfully,
     * or an error message if the file was not uploaded successfully
     */
    @PostMapping("/uploadSpecifications")
    public ResponseEntity uploadSpecifications(@RequestParam("specificationFile") MultipartFile file) {
        Map<String, Object> model = new ConcurrentHashMap<>();
        if (file.getContentType().equals(TEXT_XML_VALUE)) {
            try {
                File specificationFile = File.createTempFile("tmp", null);
                new FileOutputStream(specificationFile).write(file.getBytes());
                model.put("specification", conversionService.createSpecificationFromFile(specificationFile));
                model.put(MESSAGE_KEY, "Uploaded " + file.getOriginalFilename() + " successfully");
            } catch (IOException exception) {
                model.put(MESSAGE_KEY, CONVERSION_ERROR + exception.getMessage());
            } catch (JAXBException exception) {
                if(exception.getLinkedException() instanceof SAXParseException) {
                    SAXParseException saxParseException = (SAXParseException)exception.getLinkedException();
                    model.put(MESSAGE_KEY, CONVERSION_ERROR + "error at line " + saxParseException.getLineNumber() + " column " + saxParseException.getColumnNumber() + ": " + saxParseException.getMessage());
                } else {
                    model.put(MESSAGE_KEY, CONVERSION_ERROR + exception.getMessage());
                }
            }
        } else {
            model.put(MESSAGE_KEY, "Incompatible file type");
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
