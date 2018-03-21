package uk.ac.uwe.complexmachine;

import com.github.javaparser.ast.CompilationUnit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author Clare Daly
 * @author Danny Dunn
 * @version alpha-2.0
 * @since alpha-0.1
 */
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan("uk.ac.uwe.complexmachine")
public class CompleXMachine extends WebMvcConfigurerAdapter {
    /**
     * Global variable to handle test system file upload
     */
    public static CompilationUnit system;
    /**
     * Starts the applications.
     *
     * @param args the arguments for starting the application
     */
    public static void main(String[] args) {
        SpringApplication.run(CompleXMachine.class, args);
    }

    /**
     * Sets where to find the views, and the suffix to look for.
     *
     * @return the resolver for the view.
     */
    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /**
     * Sets where to find the resources for the view.
     *
     * @param registry the resource handler registry
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/mainTheme/");
    }
}
