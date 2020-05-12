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

	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

    <h2 style="color:black"><fmt:message key="code.title.matchrecorddetail"/></h2>
    
    <table class="table table-striped">
        <tr>
            <th><fmt:message key="code.label.matchrecorddetail.title"/></th>
            <td><b><c:out value="${matchRecord.title}"/></b></td>
        </tr>
         <tr>
            <th><fmt:message key="code.label.matchrecorddetail.season"/></th>
            <td><b><c:out value="${matchRecord.season_start}"/> - <c:out value="${matchRecord.season_end}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.matchrecorddetail.status"/></th>
            <td><c:out value="${matchRecord.status}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.matchrecorddetail.result"/></th>
            <td><c:out value="${matchRecord.result}"/></td>
        </tr>  
    </table>
    
   <c:if test="${matchRecord.status eq 'NOT_PUBLISHED'}">
   	 	<spring:url value="/matches/matchRecord/${matchRecord.match.id}/edit" var="editMatchRecord">	</spring:url>	
    	<a href="${fn:escapeXml(editMatchRecord)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.edit"/></a>
  	</c:if>
  	<br/>
  	<br/>
  	
  	<!-- Jugadores  -->
  
    <h2 style="color:black"><fmt:message key="code.title.matchrecorddetail.playerlist"/></h2>

 	<table id="matchesTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="code.label.matchrecorddetail.playername"/></th>
           			<th><fmt:message key="code.label.matchrecorddetail.goals"/></th>
           			<c:if test="${matchRecord.status eq 'NOT_PUBLISHED' and matchRecord.match.referee.user.username eq principalUsername}">
           				<th><fmt:message key="code.label.matchrecorddetail.actions"/></th>
           			</c:if>
           			<th><fmt:message key="code.label.matchrecorddetail.assists"/></th>
           			<c:if test="${matchRecord.status eq 'NOT_PUBLISHED' and matchRecord.match.referee.user.username eq principalUsername}">
	           			<th><fmt:message key="code.label.matchrecorddetail.actions"/></th>
	           		</c:if>
           			<th><fmt:message key="code.label.matchrecorddetail.redcards"/></th>
           			<c:if test="${matchRecord.status eq 'NOT_PUBLISHED' and matchRecord.match.referee.user.username eq principalUsername}">
           				<th><fmt:message key="code.label.matchrecorddetail.actions"/></th>
           			</c:if>
           			<th><fmt:message key="code.label.matchrecorddetail.yellowcards"/></th>
           			<c:if test="${matchRecord.status eq 'NOT_PUBLISHED' and matchRecord.match.referee.user.username eq principalUsername}">	
           				<th><fmt:message key="code.label.matchrecorddetail.actions"/></th>
           			</c:if>	
           			<th><fmt:message key="code.label.matchrecorddetail.receivedgoals"/></th>
           			<c:if test="${matchRecord.status eq 'NOT_PUBLISHED' and matchRecord.match.referee.user.username eq principalUsername}">
           				<th><fmt:message key="code.label.matchrecorddetail.actions"/></th>
           			</c:if>
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
                
                <c:if test="${footballPlayers.matchRecord.status eq 'NOT_PUBLISHED' and footballPlayers.matchRecord.match.referee.user.username eq principalUsername}">
                	<td>                
                    	<spring:url value="/matches/matchRecord/goal/add/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='addGoalMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(addGoalMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.addone"/></a>
                	
                		<spring:url value="/matches/matchRecord/goal/substract/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='substractGoalMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(substractGoalMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.substractone"/></a>
                	</td>
                </c:if>
                	
                <td>
                    <c:out value="${footballPlayers.assists}"/>
                </td>
                
                <c:if test="${footballPlayers.matchRecord.status eq 'NOT_PUBLISHED' and footballPlayers.matchRecord.match.referee.user.username eq principalUsername}">
                	<td>
                    	<spring:url value="/matches/matchRecord/assist/add/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='addAssistMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(addAssistMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.addone"/></a>
                	
                		<spring:url value="/matches/matchRecord/assist/substract/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='substractAssistMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(substractAssistMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.substractone"/></a>
                	</td>
                </c:if>
                	
               <td>
                    <c:out value="${footballPlayers.red_cards}"/>
               </td>
                
               <c:if test="${footballPlayers.matchRecord.status eq 'NOT_PUBLISHED' and footballPlayers.matchRecord.match.referee.user.username eq principalUsername}">
               		<td>
                    	<spring:url value="/matches/matchRecord/redCard/add/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='addRedCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(addRedCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.addone"/></a>
                	
                		<spring:url value="/matches/matchRecord/redCard/substract/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='substractRedCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(substractRedCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.substractone"/></a>
                	</td>
                </c:if>
                
                <td>
                    <c:out value="${footballPlayers.yellow_cards}"/>
                </td>
                
                <c:if test="${footballPlayers.matchRecord.status eq 'NOT_PUBLISHED' and footballPlayers.matchRecord.match.referee.user.username eq principalUsername}">
                	<td>
                    	<spring:url value="/matches/matchRecord/yellowCard/add/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='addYellowCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(addYellowCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.addone"/></a>
                	
                		<spring:url value="/matches/matchRecord/yellowCard/substract/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='substractYellowCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(substractYellowCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.substractone"/></a>
                
                	</td>
				</c:if>
				                	
                <td>
                    <c:out value="${footballPlayers.received_goals}"/>
                </td>
                
                <c:if test="${footballPlayers.matchRecord.status eq 'NOT_PUBLISHED' and footballPlayers.matchRecord.match.referee.user.username eq principalUsername}">
               		<td>
                    	<spring:url value="/matches/matchRecord/receivedGoals/add/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='addRedCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(addRedCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.addone"/></a>
                	
                		<spring:url value="/matches/matchRecord/receivedGoals/substract/${footballPlayers.matchRecord.id}/${footballPlayers.player.id}" var='substractRedCardMatchRecordStatistics'></spring:url>
                		<a href="${fn:escapeXml(substractRedCardMatchRecordStatistics)}" class="btn btn-default"><fmt:message key="code.button.matchrecorddetail.substractone"/></a>
                	</td>
                </c:if>	
            </tr>
        </c:forEach>
        </tbody>
     </table>
    
    
    <br/>
    <br/>
    <br/>
 </jsp:body> 
</petclinic:layout>
