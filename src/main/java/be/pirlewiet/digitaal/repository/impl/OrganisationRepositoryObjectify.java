package be.pirlewiet.digitaal.repository.impl;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.Key;

import be.pirlewiet.digitaal.model.Holiday;
import be.pirlewiet.digitaal.model.Organisation;
import be.pirlewiet.digitaal.repository.OrganisationRepository;

public class OrganisationRepositoryObjectify implements OrganisationRepository {

	@Override
	public Organisation findByUuid(String uuid) {
		return ofy().load().key(Key.create(Organisation.class, uuid)).now();
	}

	@Override
	public Organisation findOneByCode(String code) {
		return ofy().load().type(Organisation.class).filter("code", code).first().now();
	}

	@Override
	public Organisation findOneByEmail(String email) {
		return ofy().load().type(Organisation.class).filter("email", email).first().now();
	}

}
