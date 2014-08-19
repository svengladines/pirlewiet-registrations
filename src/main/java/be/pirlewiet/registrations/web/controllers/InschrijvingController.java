package be.pirlewiet.registrations.web.controllers;

import static be.occam.utils.spring.web.Controller.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import be.pirlewiet.registrations.model.Adres;
import be.pirlewiet.registrations.model.ContactGegevens;
import be.pirlewiet.registrations.model.Deelnemer;
import be.pirlewiet.registrations.model.InschrijvingX;
import be.pirlewiet.registrations.model.SecretariaatsMedewerker;
import be.pirlewiet.registrations.model.Status;
import be.pirlewiet.registrations.model.Vakantie;
import be.pirlewiet.registrations.model.Vraag;

@Controller
@RequestMapping( {"/inschrijvingen/{id}"} )
public class InschrijvingController {
	
	protected Logger logger 
		= LoggerFactory.getLogger( this.getClass() );
	
	@Resource
	SecretariaatsMedewerker secretariaatsMedewerker;
	
	@RequestMapping( method = { RequestMethod.GET }, produces={"application/json","text/xml"} )
	@ResponseBody
	public ResponseEntity<InschrijvingX> retrieve( @PathVariable String id ) {
		
		InschrijvingX inschrijving
			= this.secretariaatsMedewerker.inschrijving( Long.valueOf( id ) );
		
		if ( inschrijving == null ) {
			return response( HttpStatus.NOT_FOUND );
		}

		return response( inschrijving, HttpStatus.OK );
		
	}
	
	@RequestMapping( method = { RequestMethod.PUT } )
	@ResponseBody
	public ResponseEntity<InschrijvingX> update(
				@RequestBody InschrijvingX inschrijving ) {
		
		return response( inschrijving, HttpStatus.OK );
		
	}
	
	@RequestMapping( value="/contact", method = { RequestMethod.PUT } )
	@ResponseBody
	public ResponseEntity<ContactGegevens> contactUpdate(
				@PathVariable String id,
				@RequestBody ContactGegevens contactGegevens ) {
		
		ResponseEntity<InschrijvingX> retrieve
			= this.retrieve( id );
		
		InschrijvingX retrieved
			= retrieve.getBody();
		
		retrieved.setContactGegevens( contactGegevens );
		this.secretariaatsMedewerker.pasAan( retrieved );
		
		return response( contactGegevens, HttpStatus.OK );
		
	}
	
	@RequestMapping( value="/vakantie", method = { RequestMethod.PUT } )
	@ResponseBody
	public ResponseEntity<Vakantie> vakantieUpdate(
				@PathVariable String id,
				@RequestBody Vakantie vakantie ) {
		
		ResponseEntity<InschrijvingX> retrieve
			= this.retrieve( id );
		
		InschrijvingX inschrijving
			= retrieve.getBody();
		
		this.secretariaatsMedewerker.setVakantie( Long.valueOf( id ), vakantie );
		
		return response( inschrijving.getVakantie(), HttpStatus.OK );
		
	}
	
	@RequestMapping( value="/deelnemers", method = { RequestMethod.GET }, produces={"application/json","text/xml"} )
	@ResponseBody
	public ResponseEntity<List<Deelnemer>> deelnemersRetrieve(
				@PathVariable String id  ) {
		
		ResponseEntity<InschrijvingX> retrieve
			= this.retrieve( id );
		
		InschrijvingX inschrijving
			= retrieve.getBody();
		
		return response( inschrijving.getDeelnemers(), HttpStatus.OK );
		
	}
	
	@RequestMapping( value="/deelnemers", method = { RequestMethod.POST } )
	@ResponseBody
	public ResponseEntity<Deelnemer> deelnemersAdd(
				@PathVariable String id,
				@RequestBody Deelnemer deelnemer ) {
		
		ResponseEntity<InschrijvingX> retrieve
			= this.retrieve( id );
		
		InschrijvingX inschrijving
			= retrieve.getBody();
		
		this.secretariaatsMedewerker.addDeelnemer( inschrijving.getId(), deelnemer );
		
		return response( deelnemer, HttpStatus.OK );
		
	}
	
	@RequestMapping( value="/adres", method = { RequestMethod.PUT } )
	@ResponseBody
	public ResponseEntity<Adres> adressUpdate(
				@PathVariable String id,
				@RequestBody Adres adres ) {
		
		ResponseEntity<InschrijvingX> retrieve
			= this.retrieve( id );
		
		InschrijvingX inschrijving
			= retrieve.getBody();
		inschrijving.setAdres( adres );
		this.secretariaatsMedewerker.pasAan( inschrijving );
		
		return response( adres, HttpStatus.OK );
		
	}
	
	@RequestMapping( value="/vragen", method = { RequestMethod.POST } )
	@ResponseBody
	public ResponseEntity<Vraag> vragenAdd(
				@PathVariable String id,
				@RequestBody Vraag vraagEnAntwoord ) {
		
		ResponseEntity<InschrijvingX> retrieve
			= this.retrieve( id );
		
		InschrijvingX inschrijving
			= retrieve.getBody();
		
		// SGL| extra read not necessary
		this.secretariaatsMedewerker.addVraag( inschrijving.getId(), vraagEnAntwoord );
		
		return response( vraagEnAntwoord, HttpStatus.OK );
		
	}
	
	@RequestMapping( value="/opmerking", method = { RequestMethod.PUT } )
	@ResponseBody
	public ResponseEntity<String> opmerkingUpdate(
				@PathVariable String id,
				@RequestBody String opmerking ) {
		
		ResponseEntity<InschrijvingX> retrieve
			= this.retrieve( id );
		
		InschrijvingX inschrijving
			= retrieve.getBody();
		inschrijving.setOpmerking( opmerking );
		this.secretariaatsMedewerker.pasAan( inschrijving );
		
		return response( opmerking, HttpStatus.OK );
		
	}
	
	@RequestMapping( value="/status", method = { RequestMethod.PUT } )
	@ResponseBody
	public ResponseEntity<Status> statusUpdate(
				@PathVariable String id,
				@RequestBody Status status ) {
		
		ResponseEntity<InschrijvingX> retrieve
			= this.retrieve( id );
		
		InschrijvingX inschrijving
			= retrieve.getBody();
		inschrijving.setStatus( status );
		this.secretariaatsMedewerker.pasAan( inschrijving );
		
		return response( status, HttpStatus.OK );
		
	}
	
	@RequestMapping( value="/alternatief", method = { RequestMethod.PUT } )
	@ResponseBody
	public ResponseEntity<Vakantie> alternatiefUpdate(
				@PathVariable String id,
				@RequestBody Vakantie alternatief ) {
		
		ResponseEntity<InschrijvingX> retrieve
			= this.retrieve( id );
		
		InschrijvingX inschrijving
			= retrieve.getBody();
		
		this.secretariaatsMedewerker.setAlternatief( Long.valueOf( id ), alternatief );
		
		return response( inschrijving.getAlternatief(), HttpStatus.OK );
		
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<String> handleError( Exception e ){
		
		logger.warn( "failure while handling request", e );
		return response( e.getMessage(), HttpStatus.BAD_REQUEST );
		
	}
	
	@RequestMapping( method = { RequestMethod.GET }, produces={ MediaType.TEXT_HTML_VALUE } )
	public ModelAndView view( @PathVariable String id ) {
		
		ResponseEntity<InschrijvingX> entity
			= this.retrieve( id );
		
		Map<String,Object> model
			= new HashMap<String,Object>();
		
		InschrijvingX inschrijving
			= entity.getBody();
		
		model.put( "inschrijving", inschrijving );
		
		List<Vakantie> vakanties
			= this.secretariaatsMedewerker.actueleVakanties( );
		
		model.put( "vakanties", vakanties );
		
		List<Vakantie> alternatieven
			= this.secretariaatsMedewerker.alternatieven( inschrijving.getVakantie() );
		
		model.put( "alternatieven", alternatieven );

		return new ModelAndView( "inschrijving", model );
		
	}
	
}
