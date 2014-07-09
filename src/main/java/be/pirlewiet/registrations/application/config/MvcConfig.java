package be.pirlewiet.registrations.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import be.pirlewiet.registrations.web.controllers.DeelnemerController;
import be.pirlewiet.registrations.web.controllers.InschrijvingController;
import be.pirlewiet.registrations.web.controllers.InschrijvingenController;

@Configuration
@EnableWebMvc
public class MvcConfig {
	
	@Configuration
	public static class DispatcherConfig {
		
		@Bean
		public InternalResourceViewResolver internalResourceViewResolver() {
			InternalResourceViewResolver resolver
				= new InternalResourceViewResolver();
			resolver.setPrefix( "/WEB-INF/jsp/" );
			resolver.setSuffix( ".jsp" );
			return resolver;
		}
		
	}
	
	@Configuration
	public static class ControllerConfig {
		
		@Bean
		public InschrijvingenController inschrijvingenController() {
			
			return new InschrijvingenController();
			
		}
		
		@Bean
		public InschrijvingController inschrijvingController() {
			
			return new InschrijvingController();
			
		}
		
		@Bean
		public DeelnemerController deelnemerController() {
			
			return new DeelnemerController();
			
		}
		
	}
	
	@Configuration
	public static class FormatConfig {
		
		@Bean
		DateFormatter dateFormatter() {
			
			DateFormatter dateFormatter
				= new DateFormatter();
			
			dateFormatter.setPattern("dd/MM/yyyy");
			
			return dateFormatter;
			
		}
	}
		

}
