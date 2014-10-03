package be.pirlewiet.registrations.application.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;

import org.datanucleus.api.jpa.PersistenceProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import be.occam.utils.spring.configuration.ConfigurationProfiles;
import be.pirlewiet.registrations.web.util.DataGuard;
import be.pirlewiet.registrations.web.util.DevGuard;
import be.pirlewiet.registrations.web.util.NoopGuard;

import com.google.appengine.tools.development.testing.LocalAppIdentityServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@Configuration
@Profile(ConfigurationProfiles.PRODUCTION)
public class PirlewietAppEngineConfig {
	
	final static String JPA_PKG = "be.pirlewiet.registrations";
	
	@Configuration
	@Profile(ConfigurationProfiles.PRODUCTION)
	@EnableJpaRepositories(JPA_PKG)
	static class EntityManagerConfig {
		
		@Bean
		public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(PersistenceProvider persistenceProvider ) {
			
			LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
			factory.setPackagesToScan( "be.pirlewiet.registrations.model" );
			factory.setPersistenceProvider( persistenceProvider );
			// factory.setDataSource(jpaDataSource);
			factory.setPersistenceUnitName("pirlewiet-digitaal");
			factory.getJpaPropertyMap().put( "datanucleus.jpa.addClassTransformer", "false" );
			factory.getJpaPropertyMap().put( "datanucleus.appengine.datastoreEnableXGTransactions", "true" );
			factory.getJpaPropertyMap().put( "datanucleus.metadata.allowXML", "false" );
			factory.afterPropertiesSet();
			return factory;
		}
		
		@Bean
		PersistenceProvider persistenceProvider() {
			
			PersistenceProviderImpl provider
				= new PersistenceProviderImpl();
			
			return provider;
			
		}

		@Bean
		public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean factory) {
			return factory.getObject();
		}

		@Bean
		public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
			return new PersistenceExceptionTranslationPostProcessor();
		}

		@Bean
		public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
			JpaTransactionManager transactionManager = new JpaTransactionManager();
			transactionManager.setEntityManagerFactory(entityManagerFactory);
			return transactionManager;
		}
		
		@Bean
		DataGuard dataGuard() {
			
			return new NoopGuard();
			
		}
		
	}
	
}
