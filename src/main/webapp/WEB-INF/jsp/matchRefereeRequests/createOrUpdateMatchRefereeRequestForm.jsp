<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="matchRefereeRequest">
   		
   	<jsp:body>
   	<h2> <fmt:message key="matchRefereeRequest"/> </h2>
    
    <form:form modelAttribute="matchRefereeRequest" class="form-horizontal" id="add-match-request-form">
      
       		<div class="form-group has-feedback">
            <petclinic:inputField label="Title" name="title"/>
            <petclinic:inputField label="Status" name="status" readonly="true"/>
            <petclinic:inputField label="Referee" name="referee.user" readonly="true"/>
            <petclinic:inputField label="Match name" name="match.title" readonly="true"/>
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               	<button class="btn btn-default" type="submit"><fmt:message key="submitMatchRefereeRequest"/></button>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
