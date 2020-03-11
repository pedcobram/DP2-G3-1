<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
	
	<fmt:message key="code.title.newContract" var="newContract"/> 
	<fmt:message key="code.title.signingFor" var="firmaPor"/>  
	<fmt:message key="code.title.playerValue" var="playerValue"/>  
	<fmt:message key="code.label.salary" var="salario1"/> 
	<fmt:message key="code.label.startDate" var="startfecha"/> 
	<fmt:message key="code.label.endDate" var="endfecha"/> 
	<fmt:message key="code.label.clause" var="clausula1"/>
	<fmt:message key="code.crud.sign" var="contratar"/>  	
	<fmt:message key="code.information.salary" var="salaryInfo"/>
	<fmt:message key="code.information.clause" var="clauseInfo"/>	

<petclinic:layout pageName="contractPlayer">

<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#endDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>

<jsp:body>

  <h2 class="th-center">${newContract}</h2>
  <h2 class="th-center" style="color:#d20000; padding-bottom:10px" >${playerName} <c:out value=" "></c:out> ${firmaPor} ${clubName}</h2>
    		
    <form:form modelAttribute="contractPlayer" class="form-horizontal" id="add-contractPlayer-form">
    
        <div class="form-group has-feedback">     
        	<h2> ${playerValue}</h2>        	
        		<div class="form-control" style="margin:10px; background-color: #f1f1f1"> ${valor} € </div>       
            <petclinic:inputField label="${salario1}" name="salary"/>
				<div style="margin-bottom:12px; padding-left: 18%">${salaryInfo} ${salario} €.  </div>
            <petclinic:inputField label="${startfecha}" name="startDate" readonly="true" placeholder="${startDate}"/>
            <petclinic:inputField label="${endfecha}" name="endDate"/>
            <petclinic:inputField label="${clausula1}" name="clause" readonly="true" placeholder="${clausula} €"/>         
				<div style="padding-left: 18%">${clauseInfo}  </div>     
        </div>
                
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				 <button class="btn btn-default" type="submit">${contratar}</button>         
            </div>
        </div>
        
    </form:form>
   
</jsp:body>
    
    
</petclinic:layout>