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
 * @version alpha-1.0
 * @since alpha-1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CompleXMachine.class})
@WebAppConfiguration
public class TestSetupControllerTest {
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
     * Tests that the controller method returns the submitInput view.
     */
    @Test
    public void shouldReturnTestSetupPage() {
       // Given the test setup controller
        TestSetupController controller = new TestSetupController();

        // When requesting the test setup page
        ModelAndView result = controller.setupTest();

        // Then the view should be submitInput
        assertThat("The view should be submitInput", result.getViewName(), is("submitInput"));
    }

    /**
     * Tests that the setupTest URL returns the submitInput view.
     * @throws Exception if there is an error with the mock MVC
     */
    @Test
    public void shouldReturnTestSetupPageWithCallToSetupTestUrl() throws Exception { //NOPMD suppressing generic exception PMD
        mockMvc.perform(get("/setupTest"))
                .andExpect(status().isOk())
                .andExpect(view().name("submitInput"));
    }
}
