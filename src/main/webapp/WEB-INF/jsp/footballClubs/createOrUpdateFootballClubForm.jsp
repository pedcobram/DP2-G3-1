<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="footballClubs">

	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#foundationDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
<jsp:body>

  <h2 class="th-center"><fmt:message key="myFootballClub"/></h2>
    	<c:if test="${fn:startsWith(footballClub.crest, 'https://')}">
    		<div style="margin:2%" class="col-12 text-center">
    			<img width=144px  height=144px src="<spring:url value="${footballClub.crest}" htmlEscape="true" />"/>
			</div>
    	</c:if>
    	
    	<fmt:message key="nameLabel" var="Name"/>
    	<fmt:message key="logoLabel" var="Logo"/>
    	<fmt:message key="cityLabel" var="City"/>
    	<fmt:message key="stadiumLabel" var="Stadium"/>
    	<fmt:message key="moneyLabel" var="Money"/>
    	<fmt:message key="foundationDateLabel" var="FoundationDate"/>
    	
    <form:form modelAttribute="footballClub" class="form-horizontal" id="add-footballClub-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="${Name}" name="name"/>
            <petclinic:inputField label="${Logo}" name="crest" placeholder="https://www.example.com"/>
            <petclinic:inputField label="${City}" name="city"/>
            <petclinic:inputField label="${Stadium}" name="stadium"/>
            <petclinic:inputField label="${FoundationDate}" name="foundationDate" placeholder="yyyy/MM/dd"/>
            
           <h2> PRESUPUESTO:</h2> <form:input class="form-control"  value ="100000000" path="money" readonly="true"/>
           <c:if test="${news}">
           *El presupuesto inicial al crear un equipo es de 100 millones de €.                    
           </c:if>   
        <c:if test="${footballClub.status == false}"> 
        	<div class="control-group" style="padding: 10px">
            	<petclinic:selectField name="status" label="Status:" names="${status}" size="2"/>
            </div>
        </c:if> 
        
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${news}">
                        <button class="btn btn-default" type="submit"><fmt:message key="createClub"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><fmt:message key="updateClub"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        
    </form:form>
   
    </jsp:body>
</petclinic:layout>