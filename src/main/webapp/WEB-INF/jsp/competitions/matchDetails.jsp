<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
	<!-- Tomo el valor del nombre de usuario actual %-->
    
    <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

		<fmt:message key="code.title.footballClub" var="footballClubTitle"/>
		<fmt:message key="code.label.name" var="Name"/>
    	<fmt:message key="code.label.logo" var="Logo"/>
    	<fmt:message key="code.label.city" var="City"/>
    	<fmt:message key="code.label.stadium" var="Stadium"/>
    	<fmt:message key="code.label.money" var="Money"/>
    	<fmt:message key="code.label.foundationDate" var="FoundationDate"/>
    	<fmt:message key="code.label.fans" var="Fans"/>
    	<fmt:message key="code.label.coach" var="Coach"/>
    	<fmt:message key="code.label.president" var="President"/>  	
    	<fmt:message key="code.list.playerList" var="playerList"/>
    	<fmt:message key="code.button.back" var="back"/>

<petclinic:layout pageName="matches">
      	<jsp:attribute name="customScript">
		    
       		<script>
           		$(document).ready(function () {
            		$("#edit-match-form").hide();
           			 });
           		$('#fecha').on('click', function(){
			        $('#edit-match-form').fadeIn('slow');
			        $('#fecha').fadeOut('slow');
			       
			        return false;
			    });
           
           	</script>	
           	
      
        <script>
           $(function () {
              $("#matchDate").datepicker(
            		  {dateFormat: 'yy/mm/dd'});
               
           });
          
        </script>
    </jsp:attribute>
<jsp:body>
    

     

    <h2 style="color:black"><fmt:message key="code.title.matchdetails"/></h2>
    <div>
    <table class="table table-striped">
        <tr>
            <th><fmt:message key="code.label.matchdetails.title"/></th>
            <td><c:out value="${match.title}"/></td>
        </tr>
         <tr>
            <th><fmt:message key="code.label.matchdetails.matchdate"/></th>
            <td><c:out value="${match.matchDate}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.matchdetails.stadium"/></th>
            <td><c:out value="${match.stadium}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.matchdetails.footballclub.one"/></th>
            <td><c:out value="${match.footballClub1.name}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.matchdetails.footballclub.two"/></th>
            <td><c:out value="${match.footballClub2.name}"/></td>
        </tr>
         <tr>
            <th><fmt:message key="code.label.matchdetails.referee"/></th>
            <td><c:out value="${match.referee.firstName} ${match.referee.lastName}"/></td>
        </tr>  
        <tr>		
        	<th><fmt:message key="code.label.matchdetails.status"/></th>
        	<td><c:out value="${match.matchStatus}"/></td>        
        </tr> 
    </table>							 
    <input type="button" class="btn btn-default" value="${back}" name="Back" onclick="history.back()" />  
    <input type="button" id ="fecha" class="btn btn-default" value="Actualizar fecha" name="editDate.button"  />
  </div> 
<div>


   <form:form modelAttribute="match" class="form-horizontal" id="edit-match-form">
      
       	<div class="form-group has-feedback">
		 <petclinic:inputField label="code.label.matchupdateform.matchdate" name="matchDate"/>
  	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit"><fmt:message key="code.button.matchdetails.submit"/></button>
            </div>
        </div>
    </form:form>
 
    </div>   
    </jsp:body>
</petclinic:layout>


