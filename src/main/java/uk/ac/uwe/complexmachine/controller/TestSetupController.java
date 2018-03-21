package uk.ac.uwe.complexmachine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Clare Daly
 * @version alpha-0.1
 * @since alpha-0.1
 */
@Controller
public class TestSetupController {
    /**
     * Handles the request for the setupTest page of the tool.
     * @return the setupTest view
     */
    @RequestMapping(value = "/setupTest", method = RequestMethod.GET)
    public ModelAndView setupTest() {
        return new ModelAndView("submitInput");
    }
}
