<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="updateMatch">
   		
   	<jsp:body>
   	<h2> <fmt:message key="updateMatchForm"/> </h2>
    
    <form:form modelAttribute="match" class="form-horizontal" id="add-match-form">
      
       		<div class="form-group has-feedback">
            <petclinic:inputField label="Title" name="title" />
            <petclinic:inputField label="Match Date" name="matchDate" readonly="true"/>
           	<petclinic:inputField label="Stadium" name="stadium" readonly="true"/>
            <petclinic:inputField label="Football Club 1" name="footballClub1.name" readonly="true"/>
            <petclinic:inputField label="Football Club 2" name="footballClub2.name" readonly="true"/>
            <petclinic:inputField label="Referee first name" name="referee.firstName" readonly="true"/>
             <petclinic:inputField label="Referee last name" name="referee.lastName" readonly="true"/>
            <div class="control-group">
            	<petclinic:selectField label="Status" name="matchStatus" names="${status}" size="3"/>
            </div>
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit"><fmt:message key="submitUpdateMatchForm"/></button>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
