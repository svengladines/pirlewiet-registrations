package be.pirlewiet.digitaal.domain.people;

import static be.occam.utils.javax.Utils.isEmpty;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.pirlewiet.digitaal.domain.exception.ErrorCodes;
import be.pirlewiet.digitaal.domain.exception.IncompleteObjectException;
import be.pirlewiet.digitaal.domain.exception.PirlewietException;
import be.pirlewiet.digitaal.model.Address;
import be.pirlewiet.digitaal.repository.AddressRepository;
import org.springframework.stereotype.Component;

@Component
public class AddressManager {
	
	protected final Logger logger
		= LoggerFactory.getLogger( this.getClass() );
	
	@Autowired
	protected DoorMan doorMan;
	
	@Autowired
	AddressRepository addressRepository;
	
	public Address create( Address address ) {
		
		if ( isEmpty( address.getZipCode() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_ZIPCODE_MISSING );
		}
		
		if ( isEmpty( address.getCity() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_CITY_MISSING );
		}
		
		if ( isEmpty( address.getStreet() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_STREET_MISSING );
		}
		
		if ( isEmpty( address.getNumber() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_NUMBER_MISSING );
		}
		
		address.setUuid( UUID.randomUUID().toString() );
		
		Address created 
			= this.addressRepository.saveAndFlush( address );
	
		return created;
		
	}
	
	public Address update( Address toUpdate, Address update ) {
		
		// TODO, move check to protected method & reuse. prio 2
		if ( isEmpty( update.getZipCode() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_ZIPCODE_MISSING );
		}
		
		if ( isEmpty( update.getCity() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_CITY_MISSING );
		}
		
		if ( isEmpty( update.getStreet() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_STREET_MISSING );
		}
		
		if ( isEmpty( update.getNumber() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_NUMBER_MISSING );
		}
		
		toUpdate.setZipCode( update.getZipCode() );
		toUpdate.setCity( update.getCity() );
		toUpdate.setStreet( update.getStreet() );
		toUpdate.setNumber( update.getNumber() );
		
		Address updated 
			= this.addressRepository.saveAndFlush( toUpdate );
		
		logger.info( "updated address with uuid [{}] to [{}]", toUpdate.getUuid(), update.getNumber() );
	
		return updated;
		
	}
	
	public Address findOneByUuid( String uuid ) {
		return this.addressRepository.findByUuid( uuid );
	}
	
	public List<Address> findAll() {
		return this.addressRepository.findAll();
	}
	
	public void delete( Address address ) {
		this.addressRepository.delete( address );
	}
	
	public void checkComplete( String uuid ) {
		
		Address address
			= this.addressRepository.findByUuid( uuid );
		
		if ( address == null ) {
			throw new PirlewietException( ErrorCodes.INTERNAL, String.format("no address with uuid [%s]", uuid ) );
		}
		
		if ( isEmpty( address.getCity() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_CITY_MISSING );
		}
		
		if ( isEmpty( address.getStreet() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_STREET_MISSING );
		}
		
		if ( isEmpty( address.getNumber() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_NUMBER_MISSING );
		}
		
		if ( isEmpty( address.getZipCode() ) ) {
			throw new IncompleteObjectException( ErrorCodes.ADDRESS_ZIPCODE_MISSING );
		}
		
	}
	
	public boolean isComplete( String uuid ) {
		
		try {
			this.checkComplete( uuid );
			return true;
		}
		catch( IncompleteObjectException e ) {
			return false;
		}
		
	}
	
}
