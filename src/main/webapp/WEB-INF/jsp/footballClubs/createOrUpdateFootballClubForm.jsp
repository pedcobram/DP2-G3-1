<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


		<fmt:message key="code.title.newClub" var="newClub"/>
    	<fmt:message key="code.label.money" var="Money"/>
    	<fmt:message key="code.information.money" var="MoneyInfo"/>
		<fmt:message key="code.crud.newClub" var="createClub"/>
		<fmt:message key="code.crud.updateClub" var="updateClub"/>

<petclinic:layout pageName="footballClubs">

	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#foundationDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    
<jsp:body>

  <h2 class="th-center">${newClub}</h2>
    	<c:if test="${fn:startsWith(footballClub.crest, 'https://')}">
    		<div style="margin:2%" class="col-12 text-center">
    			<img width=144px  height=144px src="<spring:url value="${footballClub.crest}" htmlEscape="true" />"/>
			</div>
    	</c:if>
    	  	
    <form:form modelAttribute="footballClub" class="form-horizontal" id="add-footballClub-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="code.label.name" name="name"/>
            <petclinic:inputField label="code.label.logo" name="crest" placeholder="https://www.example.com"/>
            <petclinic:inputField label="code.label.city" name="city"/>
            <petclinic:inputField label="code.label.stadium" name="stadium"/>
            <petclinic:inputField label="code.label.foundationDate" name="foundationDate" placeholder="yyyy/MM/dd"/>
            
       		<div class="form-group">
				<label class="col-sm-2 control-label">${Money}</label>
				<div class="col-sm-10">
					<div class="form-control" style="background-color: #f1f1f1"> 
						100.000.00,00 €
					</div>
				</div>	
			</div> 
           
           <c:if test="${isNew}">
           ${MoneyInfo}                        
           </c:if>   
        <c:if test="${isEditing}"> 
        	<div class="control-group" style="padding: 10px">
            	<petclinic:selectField name="status" label="code.label.status" names="${status}" size="2"/>
            </div>
        </c:if> 
        
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${isNew}">
                        <button class="btn btn-default" type="submit">${createClub}</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">${updateClub}</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        
    </form:form>
   
    </jsp:body>
</petclinic:layout>