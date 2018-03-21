package uk.ac.uwe.complexmachine.service;

import org.springframework.stereotype.Service;
import uk.ac.uwe.complexmachine.model.Specification;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * @author Clare Daly
 * @version alpha-1.0
 * @since alpha-1.0
 */
@Service
public class SpecificationConversionService {
    /**
     * Extracts the specification from the XML file and converts it to
     * a {@link Specification} object.
     * @param specificationFile the XML file containing the specification
     * @return the {@link Specification} created from the XML file
     * @throws JAXBException if there is an issue parsing the XML file
     */
    public Specification createSpecificationFromFile(File specificationFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Specification.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Specification) jaxbUnmarshaller.unmarshal(specificationFile);
    }
}
