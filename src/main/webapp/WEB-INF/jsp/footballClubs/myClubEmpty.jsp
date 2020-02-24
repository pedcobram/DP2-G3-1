<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="footballCLubs">

    <h2 class="th-center" style="color:black">YOU DO NOT HAVE A CLUB YET!</h2>
    <div class="th-center">
  	 <security:authorize access="hasAnyAuthority('president')">
        <spring:url value="/footballClub/new" var="addUrl"></spring:url>
    	<a href="${fn:escapeXml(addUrl)}" class="btn btn-default2" style="color:white"><b>CREATE A FOOTBALL CLUB</b></a>
    </security:authorize>
    </div>
    
</petclinic:layout>
