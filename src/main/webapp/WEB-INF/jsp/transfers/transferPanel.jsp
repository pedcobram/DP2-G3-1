<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<fmt:message key="code.title.coachs" var="CoachsTitle"/>  
	<fmt:message key="code.title.coachsFree" var="CoachsFreeTitle"/>
	<fmt:message key="code.title.players" var="PlayersTitle"/>
	<fmt:message key="code.title.playersFree" var="PlayersFreeTitle"/>
	<fmt:message key="code.title.transferPanel" var="TransferTitle"/>
	<fmt:message key="code.title.transferPanel" var="TransferTitle"/>
	<fmt:message key="code.label.transferpanel.player.transfersRequestSent" var="playerTransferRequestSent"/>
	<fmt:message key="code.label.transferpanel.coach.transfersRequestSent" var="coachTransferRequestSent"/>

<petclinic:layout pageName="coachs">

<jsp:attribute name="customScript">
	
	<!-- Script para mostrar mensajes mousehover %-->

		<script>
		if(status == false){
			$(document).ready(function(){
  				$('[data-toggle="tooltip"]').tooltip();   
			});
			}
		</script>
		
	</jsp:attribute>

<jsp:body>
	<div style="margin-bottom: 4%">
		<h2 class="th-center" style="color:black; font-size:30px; margin-bottom:25px">${TransferTitle}</h2>
	</div>
	
	<div style="margin-bottom: 15%">
	    	
    	<div style="margin-bottom:2%; width: 60%; height: 10%;float:left; background-color:#d4d4d4; border: 5px solid grey; box-shadow: 10px 10px 5px #3e3e3e57;">			
 			<div class="th-center" style="height: 60px; background-color:#d4d4d4; text-aling:center">
 	 			<input type="button" class="myButtonFreeAgent" value="${playerTransferRequestSent}" onclick="window.location='/transfers/players/requests/sent'"/>	
			</div>		
    	</div>
	
		<div style="margin-top:2%; width: 38%; height: 10%;float:right; margin-left:20px; background-color:#d4d4d4; border: 5px solid grey; box-shadow: 10px 10px 5px #3e3e3e57;">	
 			<div class="th-center" style="height: 60px; background-color:#d4d4d4; text-aling:center">
	 	 		<input type="button" class="myButtonFreeAgent" value="${coachTransferRequestSent}" onclick="window.location='/transfers/coachs/free-agents'"/>
			</div>	
    	</div>
	
	</div>
	
	<div style="margin-bottom:45%">
 		
 		<div style="width: 60%; height: 350px;float:left; background-color:#d4d4d4; border: 5px solid grey; box-shadow: 10px 10px 5px #3e3e3e57;">		
 			<div class="th-center" style="height: 40px; background-color:grey; text-aling:center">
 		 		<h1 style="padding-top:5px ; color:white; font-weight: bold">${PlayersTitle}</h1>
			</div>	
			
			 	<c:choose>
                    <c:when test="${status == true}">
                        <input type="button" class="myButtonPlayer" onclick="window.location='/transfers/players'"/>
                    </c:when>
                    <c:otherwise>
                        <input type="button" class="myButtonPlayer" data-toggle="tooltip" title="Publica tu club para poder acceder"/>
                    </c:otherwise>
                </c:choose>
					
        </div>
        
        <div style="width: 38%; height: 350px;float:left; margin-left:20px; background-color:#d4d4d4; border: 5px solid grey; box-shadow: 10px 10px 5px #3e3e3e57;">	
 			<div class="th-center" style="height: 40px; background-color:grey; text-aling:center">
 		 		<h1 style="padding-top:5px ; color:white; font-weight: bold">${CoachsTitle}</h1>
			</div>	
			
			<c:choose>
                    <c:when test="${status == true}">
                        <input type="button" class="myButtonCoach" onclick="window.location='/transfers/coachs'"/>
                    </c:when>
                    <c:otherwise>
                        <input type="button" class="myButtonCoach" data-toggle="tooltip" title="Publica tu club para poder acceder"/>
                    </c:otherwise>
            </c:choose>
						       
        </div>
    
    	<div style="margin-top:2%; width: 60%; height: 10%;float:left; background-color:#d4d4d4; border: 5px solid grey; box-shadow: 10px 10px 5px #3e3e3e57;">			
 			<div class="th-center" style="height: 60px; background-color:#d4d4d4; text-aling:center">
 		 		<input type="button" class="myButtonFreeAgent" value="${PlayersFreeTitle}" onclick="window.location='/transfers/players/free-agents'"/>	
			</div>		
    	</div>
    	
    	<div style="margin-top:2%; width: 38%; height: 10%;float:left; margin-left:20px; background-color:#d4d4d4; border: 5px solid grey; box-shadow: 10px 10px 5px #3e3e3e57;">	
 			<div class="th-center" style="height: 60px; background-color:#d4d4d4; text-aling:center">
 		 		<input type="button" class="myButtonFreeAgent" value="${CoachsFreeTitle}" onclick="window.location='/transfers/coachs/free-agents'"/>
			</div>	
        </div>
        
    </div>
  </jsp:body> 
</petclinic:layout>
