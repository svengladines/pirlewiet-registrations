package be.pirlewiet.digitaal.domain.q;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import be.pirlewiet.digitaal.model.QuestionAndAnswer;
import be.pirlewiet.digitaal.model.QuestionType;
import be.pirlewiet.digitaal.model.Tags;

public class QuestionSheet {
	
	protected Map<String, List<QuestionAndAnswer>> vragen; 
		
	public static QuestionSheet template() {
		
		List<QuestionAndAnswer> templateVragen 
			= new ArrayList<QuestionAndAnswer>();
		
		QuestionAndAnswer[] medics = new QuestionAndAnswer[] {
				//new QuestionAndAnswer(1,QuestionType.Text, Tags.TAG_MEDIC, QIDs.QID_MEDIC_MEDIC, "Naam huisarts" ),
				//new QuestionAndAnswer(2,QuestionType.Text,Tags.TAG_MEDIC,  QIDs.QID_MEDIC_MEDIC_TEL, "Telefoon huisarts" ),
				//new QuestionAndAnswer(3,QuestionType.Label,  Tags.TAG_MEDIC, QIDs.QID_MEDIC_CAN,  "Mag de deelnemer deelnemen aan:" ),
				//new QuestionAndAnswer(4,QuestionType.YesNo,  Tags.TAG_MEDIC, QIDs.QID_MEDIC_SPORTS, "Sport" ),
				//new QuestionAndAnswer(5,QuestionType.YesNo, Tags.TAG_MEDIC,  QIDs.QID_MEDIC_GAME,"Spel" ),
				//new QuestionAndAnswer(6,QuestionType.YesNo, Tags.TAG_MEDIC, QIDs.QID_MEDIC_HIKE, "Wandelen" ),
				//new QuestionAndAnswer(7,QuestionType.YesNo, Tags.TAG_MEDIC, QIDs.QID_MEDIC_BIKE, "Fietsen" ),
				//new QuestionAndAnswer(8,QuestionType.YesNo, Tags.TAG_MEDIC, QIDs.QID_MEDIC_SWIM, "Zwemmen" ),
				// new QuestionAndAnswer(9,QuestionType.YesNo, Tags.TAG_MEDIC, QIDs.QID_MEDIC_SMOKE, "Voor tieners (14-16 jaar): mag uw kind roken?" ),
				//new QuestionAndAnswer(11,QuestionType.YesNo, Tags.TAG_MEDIC, QIDs.QID_MEDIC_MEDICINS, "Moet de deelnemer geneesmiddelen innemen tijdens het kamp of de vakantie?" ),
				//new QuestionAndAnswer(12,QuestionType.Area, Tags.TAG_MEDIC, QIDs.QID_MEDIC_MEDICINS_DETAIL, "Indien ja, welke geneesmiddelen en welke dosering?" )
		};
		
		QuestionAndAnswer[] fotos = new QuestionAndAnswer[] {
			
		};
		
		QuestionAndAnswer[] various = new QuestionAndAnswer[] {
				new QuestionAndAnswer(1,QuestionType.YesNo, Tags.TAG_APPLICATION, QIDs.QID_SHARED_PHOTO, "Mogen wij foto’s gebruiken waar de deelnemer(s) herkenbaar op staat/staan?" ),
				new QuestionAndAnswer(2,QuestionType.MC, Tags.TAG_APPLICATION, QIDs.QID_SHARED_BILL, "Wie betaalt de factuur?", "Deelnemer", "Organisatie", "Bewindvoerder", "Andere" ),
				new QuestionAndAnswer(3,QuestionType.Text, Tags.TAG_APPLICATION, QIDs.QID_SHARED_BILL_DETAIL, "Specifieer (naam)" ),
				new QuestionAndAnswer(4,QuestionType.Text, Tags.TAG_APPLICATION, QIDs.QID_SHARED_CONTACT, "Wie kan de vakantieleiding contacteren tijdens de vakantie ?" ),
				new QuestionAndAnswer(5,QuestionType.Text, Tags.TAG_APPLICATION, QIDs.QID_SHARED_CONTACT_PHONE, "Telefoonnummer van deze persoon" ),
		};
		
		QuestionAndAnswer[] participant
			= new QuestionAndAnswer[] {
			new QuestionAndAnswer(100, QuestionType.YesNo, Tags.TAG_PARTICIPANT, QIDs.QID_HISTORY, "Ging deze deelnemer reeds eerder mee op een Pirlewietvakantie ?" ),
			new QuestionAndAnswer(101,QuestionType.YesNo, Tags.TAG_PARTICIPANT, QIDs.QID_MEDIC_DUTCH, "Spreekt voldoende Nederlands om vlot te communiceren met vrijwilligers en mededeelnemers/om zichzelf verstaanbaar te maken" ),
			new QuestionAndAnswer(102, QuestionType.MC, Tags.TAG_PARTICIPANT , QIDs.QID_FAMILY_CAR, "Komt naar de vakantieplaats", "Met de auto", "Met de bus en/of trein" ),
			new QuestionAndAnswer(103,QuestionType.Area, Tags.TAG_PARTICIPANT, QIDs.QID_MEDIC_REMARKS, "Zijn er nog aandachtspunten waar de vakantieleiding rekening mee dient te houden? Beschrijf hier extra zorgnoden of zaken waar we zeker rekening mee moeten houden tijdens de vakantie" ),
		};
		
		QuestionAndAnswer[] adultery
			= new QuestionAndAnswer[] {
			new QuestionAndAnswer(200, QuestionType.MC, Tags.TAG_PARTICIPANT_VOV, QIDs.QID_ADULTERY_WITH, "Komt deze persoon:", "Alleen", "Met partner", "Met vriend(in)" ),
			new QuestionAndAnswer(201,QuestionType.Text, Tags.TAG_PARTICIPANT_VOV, QIDs.QID_ADULTERY_WITH_WHO, "Indien met partner/vriend(in), naam:" ),
			new QuestionAndAnswer(202,QuestionType.YesNo, Tags.TAG_PARTICIPANT_VOV, QIDs.QID_ADULTERY_SELF_RELIANT, "Ik bevestig dat de vakantieganger minimum 1 km kan stappen, voldoende mobiel en zelfredzaam is" ),

		};

		QuestionAndAnswer[] tika = new QuestionAndAnswer[] {
				new QuestionAndAnswer(300, QuestionType.YesNo, Tags.TAG_PARTICIPANT_TIKA, QIDs.QID_TIKA_CYCLING, "Ik bevestig dat de vakantieganger probleemloos 10 km kan fietsen" ),
		};
		
		templateVragen.addAll( Arrays.asList( medics ) );
		templateVragen.addAll( Arrays.asList( fotos ) );
		templateVragen.addAll( Arrays.asList( various ) );
		templateVragen.addAll( Arrays.asList( participant ) );
		templateVragen.addAll( Arrays.asList( adultery ) );
		templateVragen.addAll( Arrays.asList( tika ) );
		
		return new QuestionSheet( templateVragen );
		
	}
	
	public QuestionSheet( List<QuestionAndAnswer> vragen ) {
		
		this.vragen = new HashMap<String, List<QuestionAndAnswer>>();
		
		if ( vragen != null ) {
		
			for ( QuestionAndAnswer v : vragen ) {
				
				List<QuestionAndAnswer> list
					= this.vragen.get( v.getTag() );
				
				if ( list == null ) {
					
					list = new LinkedList<QuestionAndAnswer>();
					this.vragen.put( v.getTag(), list );
					
				}
				
				list.add( v );
				
			}
		}
		
	}

	public Map<String, List<QuestionAndAnswer>> getQuestions() {
		return vragen;
	}
	
	public QuestionAndAnswer getQuestion( String qid ) {
		
		QuestionAndAnswer vraag
			= null;
		
		for ( List<QuestionAndAnswer> list : vragen.values() ) {
			
			for ( QuestionAndAnswer v : list ) {
				if ( qid.equals( v.getQid() ) ) {
					vraag = v;
					break;
				}
			}
			
			if ( vraag != null ) {
				break;
			}

		}
		
		return vraag;
		
	}
	
}
