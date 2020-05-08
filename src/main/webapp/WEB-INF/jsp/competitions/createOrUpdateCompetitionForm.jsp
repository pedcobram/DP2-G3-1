<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

		<fmt:message key="code.crud.newClub" var="createClub"/>
		<fmt:message key="code.crud.updateClub" var="updateClub"/>

<petclinic:layout pageName="competitions">
    
<jsp:body>
    	  	
    <form:form modelAttribute="competition" class="form-horizontal" id="add-competition-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="code.label.name" name="name"/>
            <petclinic:inputField label="code.label.description" name="description"/>
            <petclinic:inputField label="code.label.reward" name="reward"/>
            
            <div class="control-group" style="padding: 10px">
            	<petclinic:selectField name="type" label="code.label.Competitiontypes" names="${types}" size="2"/>
            </div>

        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${isNew}">
                        <button class="btn btn-default" type="submit">Crear</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        
    </form:form>
   
    </jsp:body>
</petclinic:layout>