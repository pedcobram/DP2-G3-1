<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


		<fmt:message key="code.title.addingMoney" var="addingMoney"/>
		<fmt:message key="code.label.moneyToAdd" var="MoneyToAdd"/>
		<fmt:message key="code.crud.addMoney" var="addMoney"/>

<petclinic:layout pageName="footballClubs">

	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#foundationDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    
<jsp:body>

  <h2 class="th-center">${addingMoney}</h2>
    	  	
    <form:form modelAttribute="footballClub" class="form-horizontal" id="add-footballClub-form">
        <div class="form-group has-feedback">
        	<div hidden="true">
	            <petclinic:inputField label="code.label.name" name="name"/>
	            <petclinic:inputField label="code.label.logo" name="crest" placeholder="https://www.example.com"/>
	            <petclinic:inputField label="code.label.city" name="city"/>
	            <petclinic:inputField label="code.label.stadium" name="stadium"/>
	            <petclinic:inputField label="code.label.foundationDate" name="foundationDate" placeholder="yyyy/MM/dd"/>
	            
	       		<div class="form-group">
					<label class="col-sm-2 control-label">Money</label>
					<div class="col-sm-10">
						<div class="form-control" style="background-color: #f1f1f1"> 
							100.000.000,00 €
						</div>
					</div>	
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 control-label">${MoneyToAdd}</label>
				<div class="col-sm-10">
					<input type="number" name="moneyToAdd" class="form-control" min="1"/>
				</div>	
			</div>          
		</div>
        
        <div class="form-group">
        	<div class="col-sm-offset-2 col-sm-10">
                 <button class="btn btn-default" type="submit">${addMoney}</button>
            </div>
        </div>
        
    </form:form>
   
    </jsp:body>
</petclinic:layout>