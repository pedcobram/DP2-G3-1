<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<!-- Tomo el valor del nombre de usuario actual %-->
    
    <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

	<fmt:message key="code.title.coach" var="Coach"/>  
	<fmt:message key="code.title.freeAgent" var="FreeAgent"/> 
	<fmt:message key="code.label.name" var="Name"/>  
	<fmt:message key="code.label.age" var="Age"/> 
	<fmt:message key="code.label.team" var="Team"/>
	<fmt:message key="code.label.clause" var="Clausula"/>
	<fmt:message key="code.label.salary" var="Salario"/> 
	<fmt:message key="code.crud.sign" var="Fichar"/> 
	<fmt:message key="code.security.firePlayer" var="areYouSure"/>
	<fmt:message key="code.crud.fire" var="fire"/>
	<fmt:message key="code.error.validator.fireTransaction" var="salaryValidation"/>

<petclinic:layout pageName="coachs">

<jsp:attribute name="customScript">
	
	<!-- Script para mostrar mensajes mousehover %-->
	
		<script>
			$(document).ready(function(){
  				$('[data-toggle="tooltip"]').tooltip();   
			});
		</script>
	</jsp:attribute>

<jsp:body>	
    <h2 style="color:black">${Coach}</h2>

    <table class="table table-striped">
        <tr>
            <th>${Name}</th>
            <td><b><c:out value="${coach.firstName} ${coach.lastName}"/></b></td>
        </tr>
        <tr>
            <th>${Age}</th>
            <td><c:out value="${coach.birthDate} "/><b><c:out value="(${coachAge})"/></b></td>
        </tr>
        <tr>
            <th>${Team}</th>
            <td>
            
             <c:choose>
                    <c:when test="${coach.club == null}">
                        <c:out value="${FreeAgent}"/>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${coach.club.name}"/>
                    </c:otherwise>
                </c:choose>
                       
            </td>
        </tr>
        	<tr>
        		<th>${Clausula}</th>
        		<td>
        		<c:choose>
                    <c:when test="${coach.club == null}">
                        <c:out value="0 €"/>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${coach.clause} €"/>
                    </c:otherwise>
                </c:choose>
                
            	
            	
            	</td>
        	</tr> 
        
        <c:if test="${coach.club.president.user.username == principalUsername}">  
        	<tr>
            	<th>${Salario}</th>
            	<td><c:out value="${coach.salary} €"/></td>
        	</tr>     
        	   
         </c:if>
         
         
         
    </table>
    
    
    <c:if test="${salaryError == true}">
    	<p style="color:red"> ${salaryValidation}</p>
    	<br>
    	
    </c:if>
    
    <c:if test="${coach.club.id != clubId && !iCantSign && clubStatus}">
    		<spring:url value="/coachs/{coachId}/sign" var="newCoachUrl">
    			<spring:param name="coachId" value="${coach.id}"/>
    		</spring:url>	
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(newCoachUrl)}" class="btn btn-default">${Fichar}</a>
    </c:if>
    
    <c:if test="${coach.club.president.user.username == principalUsername}">
    		<spring:url value="/coachs/{coachId}/fire" var="fireCoachUrl">
    			<spring:param name="coachId" value="${coach.id}"/></spring:url>
    		<a href="${fn:escapeXml(fireCoachUrl)}" onclick="return confirm('${areYouSure}')" class="btn btn-default2">${fire}</a>
    	</c:if> 
  
 </jsp:body>
 
   
</petclinic:layout>

  
