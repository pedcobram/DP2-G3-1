<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="contractCommercial">

<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#endDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>

<jsp:body>

  <h2 class="th-center">New Contract</h2>
  <h2 class="th-center" style="color:#d20000; padding-bottom:10px" >PARA: ${clubName}</h2>
    		
    <form:form modelAttribute="contractCommercial" class="form-horizontal" id="add-contractCommercial-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Dinero:" name="money"/>
            <petclinic:inputField label="Inicio de contrato:" name="startDate" readonly="true" placeholder="${startDate}"/>
            <petclinic:inputField label="Banner publicitario" name="publicity" placeholder="https://www.example.com"/>
            <petclinic:inputField label="Fin de contrato:" name="endDate"/>
            <petclinic:inputField label="Cláusula de rescisión" name="clause" readonly="true" placeholder="10.000.000 €"/>
*La cláusula de rescisión será de 10 mill de € por defecto.       
        </div>
        
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				 <button class="btn btn-default" type="submit">Contratar</button>         
            </div>
        </div>
        
    </form:form>
   
    </jsp:body>
    
    
</petclinic:layout>