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
    <div style="text-align:center">
    
    <c:if test="${isFan}"> 
     <spring:url value="/footballClubs/list/{footballClubId}" var="footballClubUrl">
                     <spring:param name="footballClubId" value="${club.id}"/>
                </spring:url>
                <img width=30px height= auto hspace="20" src="${club.crest}"/>
                	<a id="urlClub" href="${fn:escapeXml(footballClubUrl)}"><b><c:out value="${club.name}"/></b></a></br></br>
    
    <c:if test="${!isVip}"> 
   
    	<a href="/fan/noVip" class="btn btn-default">
    					 <fmt:message key="serVip"/></a>
	 	
    </c:if>
    <a id ="deleteFan" href="/fan/delete" class="btn btn-default">
    					 <fmt:message key="deleteVip"/></a>
    					 
   </br> 	 </br> 			
    					 
	<table id="lastMachesTable" class="table table-striped">
			<thead>
       			<tr>
            		<th class="th-center"><fmt:message key="code.label.matchlist.matchdate"/></th>
           			<th class="th-center"><fmt:message key="code.label.matchlist.footballclub.one"/></th>
           			<th class="th-center">	<fmt:message key="code.label.matchrecorddetail.result"/></th>          		
           			<th class="th-center">	<fmt:message key="code.label.matchlist.footballclub.two"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${lastMatches}" var="m">
            <tr>              		
                <td>
                	<c:out value="${m.match.matchDate}"/>
                </td>
                <td>
                    <c:out value="${m.match.footballClub1.name}"/>
                </td>
                <td>
                    <c:out value="${m.result}"/>
                </td>        
                <td>
                    <c:out value="${m.match.footballClub2.name}"/>
                </td>
              
            </tr>
        </c:forEach>
        </tbody>
        </table>
    </c:if>
    </div>
</petclinic:layout>
