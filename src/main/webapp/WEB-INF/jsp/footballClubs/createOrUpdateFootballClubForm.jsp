<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

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
            <petclinic:inputField label="${Money}" name="money"/>
            <petclinic:inputField label="${FoundationDate}" name="foundationDate"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${!footballClub['new']}">
                        <button class="btn btn-default" type="submit"><fmt:message key="updateClub"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><fmt:message key="createClub"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        
    </form:form>
    <c:if test="${!footballClub['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>