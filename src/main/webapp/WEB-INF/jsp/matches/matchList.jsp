<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="Matches">
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<h2 style="color:black"><fmt:message key="code.title.matchlist"/></h2>

 	<table id="matchesTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="code.label.matchlist.title"/></th>
           			<th><fmt:message key="code.label.matchlist.matchdate"/></th>
           			<th><fmt:message key="code.label.matchlist.matchdate"/></th>
           			<th><fmt:message key="code.label.matchlist.stadium"/></th>
           			<th><fmt:message key="code.label.matchlist.footballclub.one"/></th>
           			<th><fmt:message key="code.label.matchlist.footballclub.two"/></th>
           			<th><fmt:message key="code.label.matchlist.referee"/></th>
           			<th><fmt:message key="code.label.matchlist.actions"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${matches}" var="matches">
            <tr>
                <td>
                    <c:out value="${matches.title}"/>
                </td>
                <td>
                    <c:out value="${matches.matchDate}"/>
                </td>
                <td>
                    <c:out value="${matches.matchStatus}"/>
                </td>
                <td>
                    <c:out value="${matches.stadium}"/>
                </td>
                 <td>
                    <c:out value="${matches.footballClub1.name}"/> 
                </td>
                <td>
                    <c:out value="${matches.footballClub2.name}"/>
                </td>
                <td>
                <c:choose>
        			<c:when test="${matches.referee != null}">
						<c:out value="${matches.referee.firstName} ${matches.referee.lastName}"/>
					</c:when>
            		<c:otherwise>
            			<c:out value="None"/>
					</c:otherwise>
        		</c:choose>	
                </td> 
                <td>
                	<c:if test="${matches.referee.user.username != principalUsername}">
                  		<spring:url value="/matches/${matches.id}" var='matchStatus'></spring:url>
                		<a href="${fn:escapeXml(matchStatus)}" class="btn btn-default"><fmt:message key="code.button.matchlist.data"/></a>
                	</c:if>     
                	
                	<c:if test="${matches.referee == null}">
                		<spring:url value="/matches/refereeRequest/refereeList/${matches.id}" var='editMatchStatus'></spring:url>
                		<a href="${fn:escapeXml(editMatchStatus)}" class="btn btn-default"><fmt:message key="code.button.matchlist.addreferee"/></a>    
                	</c:if>             
                	
                	<c:if test="${matches.referee != null}">
                    	<spring:url value="/matches/matchRecord/${matches.id}/view" var='viewMatchRecord'></spring:url>
                		<a href="${fn:escapeXml(viewMatchRecord)}" class="btn btn-default"><fmt:message key="code.button.matchlist.matchrecord"/></a>   
                	</c:if>              	         	
                </td>
            </tr>
        </c:forEach>
        </tbody>
     </table>
</petclinic:layout>
