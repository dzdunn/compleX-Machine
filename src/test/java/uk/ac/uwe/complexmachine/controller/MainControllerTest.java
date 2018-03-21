package uk.ac.uwe.complexmachine.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.uwe.complexmachine.CompleXMachine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Clare Daly
 * @version alpha-0.1
 * @since alpha-0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CompleXMachine.class})
@WebAppConfiguration
public class MainControllerTest {
    /**
     * The web application context for the test.
     */
    @Autowired
    private transient WebApplicationContext context;

    /**
     * The mock MVC to test the controller requests.
     */
    private static MockMvc mockMvc;

    /**
     * Sets the mock MVC for the controller requests. Runs before
     * every test.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    /**
     * Tests that the controller method returns the index view.
     */
    @Test
    public void shouldReturnIndexPage() {
       // Given the main controller
        MainController mainController = new MainController();

        // When requesting the index
        ModelAndView result = mainController.index();

        // Then the view should be the index
        assertThat("The view should be the index", result.getViewName(), is("index"));
    }

    /**
     * Tests that the index URL returns the index view.
     * @throws Exception if there is an error with the mock MVC
     */
    @Test
    public void shouldReturnIndexPageWithCallToRootUrl() throws Exception { //NOPMD suppressing generic exception PMD
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}
