package be.pirlewiet.digitaal.repository.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;

import be.pirlewiet.digitaal.model.Address;
import be.pirlewiet.digitaal.model.Application;
import be.pirlewiet.digitaal.model.Organisation;
import be.pirlewiet.digitaal.repository.ApplicationRepository;
@Repository
public class ApplicationRepositoryObjectify implements ApplicationRepository {
	
	@Override
	public Application findByUuid(String uuid) {
		
		return ofy().load().key(Key.create(Application.class, uuid)).now();
		
	}

	@Override
	public List<Application> findByYear(int year) {
		
		return ofy().load().type(Application.class).filter("year", year).list();
		
	}

	@Override
	public List<Application> findByOrganisationUuid(String organisationUuid) {
		
		return ofy().load().type(Application.class).filter("organisationuuid", organisationUuid).list();
		
	}
	
	@Override
	public List<Application> findByOrganisationUuidAndYear(String organisationUuid, int year) {

		return ofy().load().type(Application.class).filter("organisationuuid", organisationUuid).filter("year", year).list();
		
	}

	@Override
	public Application saveAndFlush(Application application) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Application application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Application> findAll() {
		return ofy().load().type(Application.class).list();
	}

}
