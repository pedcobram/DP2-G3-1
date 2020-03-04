<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="footballCLubs">

    <h2 class="th-center" style="color:black"><fmt:message key="noClubYet"/></h2>
    <div class="th-center">
  	 <security:authorize access="hasAnyAuthority('president')">
        <spring:url value="/myfootballClub/new" var="addUrl"></spring:url>
    	<a href="${fn:escapeXml(addUrl)}" class="btn btn-default2" style="color:white"><b><fmt:message key="createAClub"/></b></a>
    </security:authorize>
    </div>
    
</petclinic:layout>
