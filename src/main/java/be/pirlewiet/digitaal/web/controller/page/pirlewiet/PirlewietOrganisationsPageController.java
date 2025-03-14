package be.pirlewiet.digitaal.web.controller.page.pirlewiet;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import be.occam.utils.spring.web.Result;
import be.pirlewiet.digitaal.domain.people.DoorMan;
import be.pirlewiet.digitaal.domain.service.OrganisationService;
import be.pirlewiet.digitaal.model.Organisation;
import be.pirlewiet.digitaal.web.dto.OrganisationDTO;
import be.pirlewiet.digitaal.web.util.PirlewietUtil;

@Controller
@RequestMapping( {"/pirlewiet/organisations.html"} )
public class PirlewietOrganisationsPageController {
	
	protected Logger logger 
		= LoggerFactory.getLogger( this.getClass() );
	
	@Autowired
	DoorMan doorMan;
	
	@Autowired
	OrganisationService organisationService;
	
	protected final Comparator<Result<OrganisationDTO>> byCity 
		= new Comparator<Result<OrganisationDTO>>() {

			@Override
			public int compare(Result<OrganisationDTO> o1, Result<OrganisationDTO> o2) {
				
				return o1.getObject().getCity().compareTo( o2.getObject().getCity() );

			}
		
	};
	
	protected final Comparator<Result<OrganisationDTO>> byName 
		= new Comparator<Result<OrganisationDTO>>() {
	
			@Override
			public int compare(Result<OrganisationDTO> o1, Result<OrganisationDTO> o2) {
				
				return o1.getObject().getName().compareTo( o2.getObject().getName() );
	
			}
		
		};

	@RequestMapping( method = { RequestMethod.GET }, produces={ MediaType.TEXT_HTML_VALUE } )
	public String view(
			@RequestParam(required=false) String order,
			@CookieValue(required=true, value="pwtid") String pwtid,
			Model model) {
	
		Organisation actor
			= this.doorMan.guard().whoHasID(  pwtid  );
		
		Result<List<Result<OrganisationDTO>>> organisationsResult
				= this.organisationService.guard().query( actor );
		
		this.order(organisationsResult.getObject(), order );
			
		model.addAttribute( "organisationsResult", organisationsResult );
		
		return "pirlewiet/organisations";
	}
	
	protected void order( List<Result<OrganisationDTO>> list, String order ) {
		
		if ( "city".equals( order ) ) {
			Collections.sort( list, this.byCity );
		}
		else {
			Collections.sort( list, this.byName );
		}
		
	}
	

}
