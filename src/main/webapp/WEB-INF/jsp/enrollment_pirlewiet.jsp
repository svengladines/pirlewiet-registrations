<!DOCTYPE html>
<html>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring"%>

	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<jsp:include page="/WEB-INF/jsp/head.jsp"/>
	
	<body>
	
	<fmt:bundle basename="pirlewiet-messages">

    <jsp:include page="/WEB-INF/jsp/menu_pirlewiet.jsp">
    	<jsp:param name="active" value="enrollments"/>
    </jsp:include>

	<div class="banner">
		<div class="container">
			<div class="row centered">
				<div class="col-lg-12">
					<h1>Inschrijving</h1>
					<p>
						Beslis over de inschrijving van een deelnemer.
					</p>
				</div>
			</div><!-- row -->
		</div><!-- container -->
	</div>
	
	<div class="container">
	
		<br/>
		<div class="row">
			
			<div class="col-sm-12 alert alert-info">
				<h4><strong>Status</strong><br/></h4>
				<p>
					<i><spring:message code="enrollment.status.${enrollment.status.value}"/></i><br/>
					<spring:message code="enrollment.status.${enrollment.status.value}.description"/> <br/>
				</p>
			</div>
			
		</div>
		<div class="row">
			<div class="col-sm-12 alert alert-${ fn:length(enrollment.vakanties) == 1 ? 'success' : 'warning'}">
				<i class="fa fa-2x fa-2x fa-calendar pull-right"></i><h4><strong>Vakantie</strong><br/></h4>
				<p>Selecteer vakantie(s)</p>
					<c:forEach items="${enrollment.vakanties}" var="vakantie">	
						<div class="checkbox">
							<label>
								<input type="checkbox" name="vak" class="vakantie" value="${vakantie.uuid}" checked="checked">&nbsp;${vakantie.naam}
							</label>
						</div>
					</c:forEach>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 alert alert-success">
				<div class="row">
					<div class="col-sm-12"><i class="fa fa-2x fa-2x fa-user pull-right"></i><h4><strong>Deelnemer</strong><br/></h4>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-4 media-middle max-height">
					<span>${enrollment.deelnemers[0].voorNaam}&nbsp;${enrollment.deelnemers[0].familieNaam}</span>
				</div>
			</div>
						
			</div>
				
		</div>
		
		<div class="row">
			<div class="col-sm-12 alert alert-${ enrollment.status.value == 'SUBMITTED' ? 'warning' : 'success'}">
				<div class="row">
					<div class="col-sm-12"><i class="fa fa-2x fa-check pull-right"></i><h4><strong>Beslissing</strong></h4>
					<p>Neem een beslissing over de inschrijving</p>
				</div>
			</div>
			<div class="row">
				<div class="radio col-sm-12">
					<label>
						<input type="radio" name="decision" value="ACCEPTED" ${ enrollment.status.value == 'ACCEPTED' ? "checked=\"checked\"" : ""}><spring:message code="enrollment.status.ACCEPTED"/><br/>
					</label>
				</div>
				<div class="radio col-sm-12">
					<label>
						<input type="radio" name="decision" value="REJECTED" ${ enrollment.status.value == 'REJECTED' ? "checked=\"checked\"" : ""}><spring:message code="enrollment.status.REJECTED"/><br/>
					</label>
				</div>
				<div class="radio col-sm-12">
					<label>
						<input type="radio" name="decision" value="WAITINGLIST" ${ enrollment.status.value == 'WAITINGLIST' ? "checked=\"checked\"" : ""}><spring:message code="enrollment.status.WAITINGLIST"/><br/>
					</label>
				</div>
				<div class="radio col-sm-12">
					<label>
						<input type="radio" name="decision" value="CANCELLED" ${ enrollment.status.value == 'CANCELLED' ? "checked=\"checked\"" : ""}><spring:message code="enrollment.status.CANCELLED"/><br/>
					</label>
				</div>
			</div>
			
			</div>
				
		</div>
		
		<div class="row">
			<div class="col-sm-12 alert alert-info">
				<div class="row">
					<div class="col-sm-12"><h4><strong>Uitleg bij beslissing</strong></h4>
						<p>Voorzie enige uitleg bij de beslissing.<br/>
						<strong>Opgelet:</strong> De doorverwijzer kan deze uitleg zien.</p>
					</div>
				</div>
				<div class="row">
					<div class="radio col-sm-12">
						<textarea id="decision-comment-text" class="form-control" rows="10" cols="64"></textarea>
					</div>
				</div>
			</div>
		</div>
			
		<div class="row">
			<div class="col-sm-12 alert alert-info">
				<div class="row">
					<div class="col-sm-12"><h4><strong>Opslaan</strong><br/></h4>
				</div>
			</div>
				<div class="row">
					<div class="checkbox col-sm-12">
						<label>
							<input type="checkbox" name="send-email" checked="checked">&nbsp;Verstuur e-mail naar doorverwijzer.
						</label>
					</div>
				</div>
				<div id="enrollment-status" class="col-sm-12 alert alert-warning hidden"></div>
			<div class="row">
				<div class="radio col-sm-12">
					<button type="button" id="enrollment-submit" class="btn btn-primary"><i class="fa fa-3 fa-save"></i>&nbsp;&nbsp;Sla op</button>
				</div>
			</div>
		</div>	
	</div>
				
	</div><!-- container -->
	
	</fmt:bundle>
	
	<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
	
    <script>
    	var $jq = jQuery.noConflict();
    	
    	var show = function( id ) {
    		$jq(".panel" ).removeClass("show").addClass("hidden");
    		$jq("#" + id ).removeClass("hidden").addClass("show");
    	};
    	
		var saveVakanties = function( id ) {
			var list
				= "";
			$jq( ".vakantie:checked" ).each( function( index, element ) {
				if ( list.length > 0 ) {
					list = list.concat(",");
				}
				list = list.concat( element.value );	
			});
			putVakanties ( id, list, $jq("#enrollment-submit"),$jq("#enrollment-status" ), saveStatus );
		};
		
		var saveStatus = function( id ) {
			var comment = $jq("#decision-comment-text").val();
			var decision = $jq( "input[name=decision]:checked" ).val();
			if ( ! decision ) {
				decision = "SUBMITTED";	
			}
			var sx = new Status (decision, comment ,true );
			putStatus ( id, sx, $jq("#enrollment-submit" ),$jq("#enrollment-status" ), refresh );
			
		};
		
		$jq("#enrollment-submit").click( function( event ) {
			
			clearStatus();
			saveVakanties( "${enrollment.uuid}" );
			
		});
		
		$jq( document ).ready(function() {
			
			buttons();
		    
		});
		
    </script>
  </body>
</html>
