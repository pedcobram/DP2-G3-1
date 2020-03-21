<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="matchRecord">
	
<jsp:body>	

    <h2 style="color:black"><fmt:message key="matchRecord"/></h2>
    
    <table class="table table-striped">
        <tr>
            <th><fmt:message key="titleMatchRecord"/></th>
            <td><b><c:out value="${matchRecord.title}"/></b></td>
        </tr>
         <tr>
            <th><fmt:message key="seasonMatchRecord"/></th>
            <td><b><c:out value="${matchRecord.season_start}"/> - <c:out value="${matchRecord.season_end}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="statusMatchRecord"/></th>
            <td><c:out value="${matchRecord.status}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="resultMatchRecord"/></th>
            <td><c:out value="${matchRecord.result}"/></td>
        </tr>  
    </table>
    
   <c:if test="${matchRecord.status eq 'NOT_PUBLISHED'}">
   	 	<spring:url value="/matches/matchRecord/${matchRecord.match.id}/edit/" var="editMatchRecord">
    		<!-- <spring:param name="matchId" value="${matchRecord.match.id}"/> -->
   		</spring:url>	
    	<a href="${fn:escapeXml(editMatchRecord)}" class="btn btn-default">Editar</a>
  	</c:if>
  	<br/>
  	<br/>
  	
  	<!-- Jugadores  -->
  
    <h2 style="color:black"><fmt:message key="playersMatchRecord"/></h2>

 	<table id="matchesTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="playerNameMatchRecordStatistics"/></th>
           			<th><fmt:message key="goalsMatchRecordStatistics"/></th>
           			<th><fmt:message key="actionsMatchRecordStatistics"/></th>
           			<th><fmt:message key="assistsMatchRecordStatistics"/></th>
           			<th><fmt:message key="actionsMatchRecordStatistics"/></th>
           			<th><fmt:message key="redCardsMatchRecordStatistics"/></th>
           			<th><fmt:message key="actionsMatchRecordStatistics"/></th>
           			<th><fmt:message key="yellowCardsMatchRecordStatistics"/></th>
           			<th><fmt:message key="actionsMatchRecordStatistics"/></th>
           			<th><fmt:message key="receivedGoalsMatchRecordStatistics"/></th>
           			<th><fmt:message key="actionsMatchRecordStatistics"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${footballPlayers.footballPlayerStatisticsList}" var="footballPlayers">
            <tr>
                <td>
                    <c:out value="${footballPlayers.player.firstName}"/>, <c:out value="${footballPlayers.player.lastName}"/>
                </td>
                <td>
                    <c:out value="${footballPlayers.goals}"/>
                </td>
                <td>
                	<c:if test="${footballPlayers.matchRecord.status eq 'NOT_PUBLISHED'}">
                    	<spring:url value="/matches/matchRecord/goal/add/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='addGoalMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(addGoalMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="addOneMatchRecordStatistics"/></a>
                	
                		<spring:url value="/matches/matchRecord/goal/substract/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='substractGoalMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(substractGoalMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="substractOneMatchRecordStatistics"/></a>
                	</c:if>
                </td>
                <td>
                    <c:out value="${footballPlayers.assists}"/>
                </td>
                <td>
                	<c:if test="${footballPlayers.matchRecord.status eq 'NOT_PUBLISHED'}">
                    	<spring:url value="/matches/matchRecord/assist/add/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='addAssistMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(addAssistMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="addOneMatchRecordStatistics"/></a>
                	
                		<spring:url value="/matches/matchRecord/assist/substract/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='substractAssistMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(substractAssistMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="substractOneMatchRecordStatistics"/></a>
                	</c:if>
                </td>
               <td>
                    <c:out value="${footballPlayers.red_cards}"/>
                </td>
                <td>
                	<c:if test="${footballPlayers.matchRecord.status eq 'NOT_PUBLISHED'}">
                    	<spring:url value="/matches/matchRecord/redCard/add/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='addRedCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(addRedCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="addOneMatchRecordStatistics"/></a>
                	
                		<spring:url value="/matches/matchRecord/redCard/substract/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='substractRedCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(substractRedCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="substractOneMatchRecordStatistics"/></a>
                	</c:if>
                </td>
                <td>
                    <c:out value="${footballPlayers.yellow_cards}"/>
                </td>
                <td>
                	<c:if test="${footballPlayers.matchRecord.status eq 'NOT_PUBLISHED'}">
                    	<spring:url value="/matches/matchRecord/yellowCard/add/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='addYellowCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(addYellowCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="addOneMatchRecordStatistics"/></a>
                	
                		<spring:url value="/matches/matchRecord/yellowCard/substract/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='substractYellowCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(substractYellowCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="substractOneMatchRecordStatistics"/></a>
                	</c:if>
                </td>
                <td>
                    <c:out value="${footballPlayers.received_goals}"/>
                </td>
                <td>
                	<c:if test="${footballPlayers.matchRecord.status eq 'NOT_PUBLISHED'}">
                    	<spring:url value="/matches/matchRecord/receivedGoals/add/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='addRedCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(addRedCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="addOneMatchRecordStatistics"/></a>
                	
                		<spring:url value="/matches/matchRecord/receivedGoals/substract/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='substractRedCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(substractRedCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="substractOneMatchRecordStatistics"/></a>
                	</c:if>	
                </td>
            </tr>
        </c:forEach>
        </tbody>
     </table>
    
    
    <br/>
    <br/>
    <br/>
 </jsp:body> 
</petclinic:layout>
