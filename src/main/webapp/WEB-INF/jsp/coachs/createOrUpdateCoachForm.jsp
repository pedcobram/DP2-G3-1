<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

	<fmt:message key="code.title.coachInformation" var="dataCoach"/> 
	<fmt:message key="code.crud.register" var="register"/> 
	<fmt:message key="code.label.salary" var="salario"/> 
	<fmt:message key="code.label.clause" var="clausula"/> 

<petclinic:layout pageName="coachs">

	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
        
        <script>		
			function sumar (valor) {
			    var total = 0;	
			    valor = parseInt(valor); // Convertir el valor a un entero (número).
				
			    total = document.getElementById('spTotal').innerHTML;
				
			    // Aquí valido si hay un valor previo, si no hay datos, le pongo un cero "0".
			    total = (total == null || total == undefined || total == "") ? 0 : total;
				
			    /* Esta es la suma. */
			    total = (parseInt(valor)*5);
				
			    // Colocar el resultado de la suma en el control "span".
			    document.getElementById('spTotal').innerHTML = total;			    
			}
		</script> 
    </jsp:attribute>
<jsp:body>

  <h2 class="th-center">${dataCoach}</h2>
    		
    <form:form modelAttribute="coach" class="form-horizontal" id="add-coach-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="code.label.firstName" name="firstName"/>
            <petclinic:inputField label="code.label.lastName" name="lastName"/>
            <petclinic:inputField label="code.label.birthDate" name="birthDate" placeholder="yyyy/MM/dd"/>
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
						<span id="spTotal"></span> €
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