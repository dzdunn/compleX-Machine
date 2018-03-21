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
public class MainController {

    /**
     * Handles the request for the index page of the tool.
     * @return the index view
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
