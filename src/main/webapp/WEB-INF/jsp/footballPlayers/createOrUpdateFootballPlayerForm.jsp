<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

	<fmt:message key="code.title.playerInformation" var="dataPlayer"/> 
	<fmt:message key="code.crud.register" var="register"/> 
	<fmt:message key="code.crud.update" var="update"/> 	
	<fmt:message key="code.label.salary" var="salario"/> 
	<fmt:message key="code.label.startDate" var="startfecha"/> 
	<fmt:message key="code.label.endDate" var="endfecha"/> 
	<fmt:message key="code.label.clause" var="clausula"/> 
	<fmt:message key="code.title.contractInformation" var="contractInfo"/> 
	<fmt:message key="code.title.contractMoreInfo" var="moreInfo"/> 

<petclinic:layout pageName="footballPlayers">

	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
<jsp:body>

  <h2 class="th-center">${dataPlayer}</h2>
    		
    <form:form modelAttribute="footballPlayer" class="form-horizontal" id="add-footballPlayer-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="code.label.firstName" name="firstName"/>
            <petclinic:inputField label="code.label.lastName" name="lastName"/>
            <petclinic:inputField label="code.label.birthDate" name="birthDate" placeholder="yyyy/MM/dd"/>
            <div class="control-group">
                    <petclinic:selectField label="code.label.position" name="position" names="${positions}" size="4"/>
                </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${!footballPlayer['new']}">
                        <button class="btn btn-default" type="submit">${update}</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">${register}</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        
    </form:form>
    
    <h2 class="th-center">${contractInfo}</h2>
    <p class="th-center" style="margin-bottom: 2%">${moreInfo}</p>
    
    <label class="col-sm-2 control-label" style="text-align: right; margin:0.5%">${salario}</label> <div class="form-control" style="width:70%; margin-left:18%; margin-bottom:10px; background-color: #f1f1f1"> ${salary}</div>
   	<label class="col-sm-2 control-label" style="text-align: right; margin:0.5%">${startfecha}</label> <div class="form-control" style="width:70%; margin-left:18%; margin-bottom:10px; background-color: #f1f1f1">${startDate}</div>
   	<label class="col-sm-2 control-label" style="text-align: right; margin:0.5%">${endfecha}</label><div class="form-control" style="width:70%; margin-left:18%; margin-bottom:10px; background-color: #f1f1f1"> ${endDate}</div>
   	<label class="col-sm-2 control-label" style="text-align: right; margin:0.5%">${clausula}</label><div class="form-control" style="width:70%; margin-left:18%; margin-bottom:10px; background-color: #f1f1f1"> ${clause}</div>
    
    </jsp:body>
    
    
</petclinic:layout>