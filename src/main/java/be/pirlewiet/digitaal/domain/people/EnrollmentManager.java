package be.pirlewiet.digitaal.domain.people;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.pirlewiet.digitaal.model.Application;
import be.pirlewiet.digitaal.model.Enrollment;
import be.pirlewiet.digitaal.repositories.EnrollmentRepository;

/*
 * 
 * 
 */
public class EnrollmentManager {

	protected final Logger logger
		= LoggerFactory.getLogger( this.getClass() );
	
	@Resource
	protected EnrollmentRepository enrollmentRepository;
	
	public EnrollmentManager( ) {
	}
	
	public List<Enrollment> findByApplicationUuid( String applicationUuid ) {
		
		List<Enrollment> enrollments
			= this.enrollmentRepository.findByApplicationUuid( applicationUuid );
		
		// TODO, sort ...
		
		return enrollments;
		
	}
	

}
 