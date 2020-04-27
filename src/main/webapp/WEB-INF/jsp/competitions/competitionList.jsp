<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<fmt:message key="code.title.coachs" var="CoachsTitle"/>  
	<fmt:message key="code.title.freeAgent" var="FreeAgent"/> 
	<fmt:message key="code.column.name" var="Name"/>  
	<fmt:message key="code.column.club" var="Club"/> 
	<fmt:message key="code.column.birthDate" var="BirthDate"/> 
	<fmt:message key="code.crud.registerCoach" var="RegisterCoach"/> 
	<fmt:message key="code.crud.signFreeAgent" var="SignCoach"/> 
	<fmt:message key="code.information.registerCoach" var="RegisterCoachInfo"/>

<petclinic:layout pageName="competitions">

	<h2 style="color:black">COMPETITIONS</h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
            		<th>Name</th>
           			<th>Type</th>
           			<th>Reward</th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${competitions}" var="competition">
            <tr>
                 		
                <td>
                <spring:url value="/competitions/{competitionId}" var="competitionUrl">
                        <spring:param name="competitionId" value="${competition.id}"/>
                </spring:url>
					<b><a href="${fn:escapeXml(competitionUrl)}"><c:out value="${competition.name}"/></a></b>
                </td>
                <td>                         
                    <c:out value="${competition.type}"/>                    	    
                </td>
              
                <td>
                    <c:out value="${competition.reward}"/>
                </td>
                
              	
            </tr>
        </c:forEach>
        </tbody>
        </table>     
   
</petclinic:layout>
