<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

	<fmt:message key="code.title.signingInformation" var="dataSing"/> 
	<fmt:message key="code.title.coachInformation" var="dataCoach"/> 
	<fmt:message key="code.crud.register" var="register"/> 
	<fmt:message key="code.label.salary" var="salario"/> 
	<fmt:message key="code.label.clause" var="clausula"/> 
	<fmt:message key="code.title.signingFor" var="firmaPor"/>  	
	<fmt:message key="code.title.isNotFreeAgent" var="notFree"/>  
	<fmt:message key="code.title.isFreeAgent" var="free"/> 
	<fmt:message key="code.title.toPay" var="toPay"/>  
	<fmt:message key="code.title.and" var="andS"/>
		
		<c:set var = "read" value = "false"/>
		
		<c:if test="${readonly}">
			<c:set var = "read" value = "true"/>		  
		</c:if>
		
		<c:choose>
        	<c:when test="${freeAgent}">
				<c:set var = "freeOrNotFree" value = "${free}"/>
			</c:when>
            <c:otherwise>
            	<c:set var = "freeOrNotFree" value = "${notFree}"/>
            	<c:set var = "toPays" value = "${toPay}"/>
            	<c:set var = "toPaysValue" value = "${toPayValue}"/>
			</c:otherwise>
        </c:choose>	

<petclinic:layout pageName="coachs">

	<jsp:attribute name="customScript">   
        <script>		
			function sumar (valor) {
			    var total = 0;	
			    valor = parseInt(valor); // Convertir el valor a un entero (número).
				
			    total = document.getElementById('spTotal').innerHTML;
				
			    // Aquí valido si hay un valor previo, si no hay datos, le pongo un cero "0".
			    total = (total == null || total == undefined || total == "") ? 0 : total;
				
			    /* Esta es la suma. */
			    total = (parseInt(valor)*3);
				
			    // Colocar el resultado de la suma en el control "span".
			    document.getElementById('spTotal').innerHTML = total;			    
			}
		</script> 
		<script>
			if(${read} == false){ //ejecutamos solo si lo estamos registrando
            	$(function () {
                	$("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            	});}
        </script>
		
    </jsp:attribute>
<jsp:body>

  				
                    <c:if test="${readonly}">
                        <h2 class="th-center">${dataSing}</h2>
                        <h2 class="th-center" style="color:#a20101; padding-bottom: 15px">${toPays} ${toPaysValue}</h2>
                        <h2 class="th-center" style="color:#d20000" >${coach.firstName}<c:out value=" "></c:out>${coach.lastName} ${freeOrNotFree} ${clubCoach}, ${firmaPor} ${clubName}</h2>
                        <c:if test="${freeAgent == false}">
							<h2 class="th-center">${andS}</h2>	
							<h2 class="th-center" style="color:#d20000; font-size:16px; padding-bottom: 15px" >${myCoachFirstName}<c:out value=" "></c:out>${myCoachLastName} ${freeOrNotFree} ${clubName}, ${firmaPor} ${clubCoach}</h2>	  
						</c:if>    
                    </c:if>
                    
  		<h2 class="th-center" style="padding-bottom: 15px">${dataCoach}</h2>
    <form:form modelAttribute="coach" class="form-horizontal" id="add-coach-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="code.label.firstName" name="firstName" readonly="${read}"/>
            <petclinic:inputField label="code.label.lastName" name="lastName" readonly="${read}"/>
            <petclinic:inputField label="code.label.birthDate" name="birthDate" placeholder="yyyy/MM/dd" readonly="${read}"/>
            <div class="form-group">         		
            	<label class="col-sm-2 control-label" >${salario}</label>
            	<div class="col-sm-10"> 
            		<form:input class="form-control" path="salary" id="salary" OnKeyUp="sumar(this.value);"/> 
            			<form:errors path="salary"/>
            	</div>
            </div>
            <div class="form-group">
				<label class="col-sm-2 control-label">${clausula}</label>
				<div class="col-sm-10">
					<div class="form-control" style="background-color: #f1f1f1"> 
						<span id="spTotal" ></span> €
					</div>
				</div>	
			</div>       
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">             
                        <button class="btn btn-default" type="submit">${register}</button>                 
            </div>
        </div>
        
    </form:form>
 
    </jsp:body>
    
    
</petclinic:layout>