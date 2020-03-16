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
            <petclinic:inputField label="Title" name="title" readonly="true"/>
            <petclinic:inputField label="Match Date" name="matchDate"/>
           	<div class="control-group">
            	<petclinic:selectField label="Stadium;" name="stadium" names="${stadiums}" size="2"/>
            </div>
            <petclinic:inputField label="Football Club 1" name="footballClub1.name" readonly="true"/>
            <petclinic:inputField label="Football Club 2" name="footballClub2.name" readonly="true"/>
            <div class="form-group">
				<label class="col-sm-2 control-label">Referee:</label>
				<div class="col-sm-10">
					<div class="form-control" style="background-color: #f1f1f1"> 
						<c:out value="${match.referee.firstName} ${match.referee.lastName}"/>
					</div>
				</div>	
			</div>
            <petclinic:inputField label="Status:" name="matchStatus" readonly="true"/>
            
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit"><fmt:message key="submitUpdateMatchForm"/></button>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
