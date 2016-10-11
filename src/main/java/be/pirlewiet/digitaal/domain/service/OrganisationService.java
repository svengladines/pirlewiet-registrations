package be.pirlewiet.digitaal.domain.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import be.occam.utils.spring.web.Result;
import be.occam.utils.spring.web.Result.Value;
import be.pirlewiet.digitaal.domain.exception.ErrorCodes;
import be.pirlewiet.digitaal.domain.people.AddressManager;
import be.pirlewiet.digitaal.domain.people.DoorMan;
import be.pirlewiet.digitaal.domain.people.OrganisationManager;
import be.pirlewiet.digitaal.dto.AddressDTO;
import be.pirlewiet.digitaal.dto.OrganisationDTO;
import be.pirlewiet.digitaal.model.Address;
import be.pirlewiet.digitaal.model.Organisation;
import be.pirlewiet.digitaal.repositories.OrganisationRepository;

@Service
public class OrganisationService extends be.pirlewiet.digitaal.domain.service.Service<OrganisationDTO,Organisation> {
	
	@Resource
	protected DoorMan doorMan;
	
	@Resource
	OrganisationManager organisationManager;
	
	@Resource
	OrganisationRepository organisationRepository;
	
	@Resource
	AddressManager addressManager;
	
	@Override
	public OrganisationService guard() {
		super.guard();
		return this;
	}

	@Override
	public Result<OrganisationDTO> create(OrganisationDTO dto, Organisation actor) {
		
		Result<OrganisationDTO> result
			= new Result<OrganisationDTO>();
		
		Organisation organisation
			= Organisation.from( dto );
		
		Organisation created
			= this.organisationManager.create(organisation);
		
		result.setValue( Value.OK );
		result.setObject( OrganisationDTO.from( created ) );
		
		return result;
		
	}
	
	public Result<OrganisationDTO> findOneByUuid( String uuid, Organisation actor ) {
		
		Result<OrganisationDTO> result
			= new Result<OrganisationDTO>();
		
		Organisation found 
			= this.organisationRepository.findByUuid( uuid );
		
		if ( found == null ) {
			result.setValue( Value.NOK );
			result.setErrorCode( ErrorCodes.ORGANISATION_NOT_FOUND );
		}
		else {
			result.setValue( Value.OK );
			result.setObject( OrganisationDTO.from( found ) );
		}
		
		return result;
		
	}
	
	public Result<AddressDTO> updateAddress( String uuid, AddressDTO address, Organisation actor ) {
		
		Result<AddressDTO> result
			= new Result<AddressDTO>();
		
		Result<OrganisationDTO> organisationResult
			= this.findOneByUuid( uuid , actor );
		
		if ( ! Value.OK.equals( organisationResult.getValue() ) ) {
			// TODO
		}
		
		Address updated
			= this.addressManager.createOrUpdate( Address.from( address ) );
		
		this.organisationManager.updateAddress( organisationResult.getObject(), updated.getUuid() );
		
		result.setValue( Value.OK );
		result.setObject( AddressDTO.from( updated ) );
		
		return result;
		
	}

	
}
