<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message key="code.crud.newClub" var="createClub"/>

<petclinic:layout pageName="competitions.clubs">
<jsp:body>
<!-- Tomo el valor del nombre de usuario actual %-->
    
   <!--  <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>   %-->
	
	 <form:form modelAttribute="competition" class="form-horizontal">
            
            <div class="form-group has-feedback">
                <div class="form-group">
                    <petclinic:inputField label="code.label.name" name="name" readonly="true"/>
                </div>
                  <div class="control-group">
                  		<petclinic:selectField name="clubs"  names="${clubsName}" label="code.label.equipos" size="${size}"/>
                  </div>
                   		<button class="btn btn-default" type="submit"><fmt:message key="code.button.competition.addClub"/></button>
                  </div>
        </form:form>
	</jsp:body>
	</petclinic:layout>

 
 
 