<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <!--
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/Log.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
    </div> %--> 
    
    <c:if test="${isFan&&!isVip}"> 
   
    	<a href="/footballClub/noVip" class="btn btn-default">
    					 <fmt:message key="serVip"/></a>
	 	
    </c:if>
    <c:if test="${isFan}"> 
    <a href="/footballClub/fan/delete" class="btn btn-default">
    					 <fmt:message key="deleteVip"/></a>
    					 
    
    </c:if>
    
</petclinic:layout>
