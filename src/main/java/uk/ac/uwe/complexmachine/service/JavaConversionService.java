package uk.ac.uwe.complexmachine.service;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Clare Daly
 * @version alpha-2.0
 * @since alpha-2.0
 */
@Service
public class JavaConversionService {
    /**
     * Converts a java file into a CompilationUnit for running tests on.
     * @param javaFile the java file to convert
     * @return the CompilationUnit created from the file
     * @throws FileNotFoundException if the file cannot be found
     */
    public CompilationUnit createJavaFromFile(File javaFile) throws FileNotFoundException {
        return JavaParser.parse(javaFile);
    }
}
