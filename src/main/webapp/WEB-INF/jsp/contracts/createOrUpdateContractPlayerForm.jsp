<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="contractPlayer">

<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#endDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>

<jsp:body>

  <h2 class="th-center">New Contract</h2>
  <h2 class="th-center" style="color:#d20000; padding-bottom:10px" >${playerName} firma por: ${clubName}</h2>
    		<c:out value="${sid}"></c:out>
    <form:form modelAttribute="contractPlayer" class="form-horizontal" id="add-contractPlayer-form">
        <div class="form-group has-feedback">
        <h2> VALOR DEL JUGADOR:</h2> <div class="form-control" style="margin:10px; background-color: #f1f1f1"> ${valor} € </div>
            <petclinic:inputField label="Salario:" name="salary"/>
<div style="margin-bottom:12px; padding-left: 18%">*El salario mínimo de este jugador será de: ${salario} €.  </div>
            <petclinic:inputField label="Inicio de contrato:" name="startDate" readonly="true" placeholder="${startDate}"/>
            <petclinic:inputField label="Fin de contrato:" name="endDate"/>
            <petclinic:inputField label="Cláusula de rescisión" name="clause" readonly="true" placeholder="${clausula} €"/>
<div style="padding-left: 18%">*La cláusula de rescisión dependerá del valor del jugador y se genera automáticamente.  </div>     
        </div>
        
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				 <button class="btn btn-default" type="submit">Contratar</button>         
            </div>
        </div>
        
    </form:form>
   
    </jsp:body>
    
    
</petclinic:layout>