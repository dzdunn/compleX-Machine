package uk.ac.uwe.complexmachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.uwe.complexmachine.service.JavaConversionService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static uk.ac.uwe.complexmachine.CompleXMachine.system;

/**
 * @author Danny Dunn
 * @author Clare Daly
 * @version alpha-3.0
 * @since alpha-2.0
 */
@Controller
public class SystemController {
      /**
     * The key for message in the controller response.
     */
    private static final String MESSAGE_KEY = "message";

    /**
     * Autowired instance of the system conversion service to
     * create the test system from the System .java file
     */
    @Autowired
    private transient JavaConversionService conversionService;

    /**
     * Error message in case of conversion failure.
     */
    private static final String CONVERSION_ERROR = "Could not convert file: ";

    /**
       * Receives the system file from the view. Checks that the  
       * file is an .java file.
       * @param sysFile the system file set from the view
       * @return a confirmation the system file has been uploaded successfully,
       * or an error message if the system file was not uploaded successfully
       */
    @PostMapping("/uploadSystem")
    public ResponseEntity uploadSystem(@RequestParam("uploadedSystemFile") MultipartFile sysFile) {
        Map<String, Object> model = new ConcurrentHashMap<>();
        if (isJavaFile(sysFile.getOriginalFilename())) {
            try {
                File systemFile = File.createTempFile("tmp", null);
                new FileOutputStream(systemFile).write(sysFile.getBytes());
                system = conversionService.createJavaFromFile(systemFile);
                model.put(MESSAGE_KEY, "Uploaded " + sysFile.getOriginalFilename() + " successfully");
                model.put("result", "true");
            } catch (IOException exception) {
                model.put(MESSAGE_KEY, CONVERSION_ERROR + exception.getMessage());
                model.put("result", "false");
            }
        } else {
            model.put(MESSAGE_KEY, "Incompatible file type");
            model.put("result", "false");
        }
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Checks for incorrect .java formats
     * @return true if correct format of '.java' file returned
     */
    private boolean isJavaFile(String fileName) {
        String suffix = ".java";
        int occurance = StringUtils.countOccurrencesOf(fileName, ".");
        return fileName.endsWith(suffix) && occurance == 1 && fileName.length() > suffix.length();
    }
}
