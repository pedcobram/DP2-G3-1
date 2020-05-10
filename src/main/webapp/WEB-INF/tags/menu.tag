<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<fmt:message key="code.title.contractsPlayers" var="contracts"/>
	<fmt:message key="code.list.myPlayerList" var="playerList"/>
	<fmt:message key="code.list.contractsCommercial" var="publicities"/>
	<fmt:message key="code.list.coachList" var="coachList"/>
	<fmt:message key="code.crud.transfers" var="Transfers"/>
	<fmt:message key="code.list.newTournament" var="newTournament"/>	
	<fmt:message key="code.list.myTournament" var="myTournaments"/>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span><fmt:message key="home"/></span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'owners'}" url="/competitions/list"
					title="tournaments">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span><fmt:message key="tournaments"/></span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'footballClubs'}" url="/footballClubs/list"
					title="registered teams">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span><fmt:message key="teams"/></span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'footballPlayers'}" url="/footballPlayers"
					title="registered players">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span><fmt:message key="players"/></span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'error'}" url="/oups"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span><fmt:message key="error"/></span>
				</petclinic:menuItem>
						
			</ul>

			<ul class="nav navbar-nav navbar-right">
						
				<sec:authorize access="hasAnyAuthority('president')">	
					<petclinic:menuItem active="${name eq 'footballClubs'}" url="/footballClubs/myClub/${principalUsername}"
						title="club page">
						<span style="color:#ffc800" class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
						<span><fmt:message key="myClub"/></span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="!isAuthenticated()">				
					<li><a href="<c:url value="/login" />"><fmt:message key="login"/></a></li>
					<li><a href="<c:url value="/authenticateds/new" />"><fmt:message key="register"/></a></li>
				</sec:authorize>				
				
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
						
						<sec:authorize access="!hasAnyAuthority('president', 'competitionAdmin', 'admin', 'referee')">
							<li><a href="<c:url value="/presidents/new" />"><fmt:message key="becPresident"/></a></li>
						</sec:authorize>
						
						<sec:authorize access="!hasAnyAuthority('president', 'competitionAdmin', 'admin', 'referee')">
							<li><a href="<c:url value="/createReferee" />"><fmt:message key="code.tag.menu.becReferee"/></a></li>
						</sec:authorize>
						
						<sec:authorize access="!hasAnyAuthority('president', 'competitionAdmin', 'admin', 'referee')">
							<li><a href="<c:url value="/competitionAdminRequest/new" />"><fmt:message key="code.tag.menu.becCompetitionAdmin"/></a></li>
						</sec:authorize>
								

							<li>
								<div class="navbar-login">
									<div class="row">
									
										<sec:authorize access="hasAuthority('authenticated')">	
											<petclinic:menuItem active="${name eq 'authenticateds'}" url="/myProfile/${principalUsername}"
												title="personal space">
												<span><fmt:message key="myProfile"/></span>
											</petclinic:menuItem>
										</sec:authorize>
										
										<sec:authorize access="hasAuthority('president')">	
											<petclinic:menuItem active="${name eq 'presidents'}" url="/presidents/${principalUsername}"
												title="personal space">
												<span><fmt:message key="myProfile"/></span>
											</petclinic:menuItem>
										</sec:authorize>								

										<sec:authorize access="hasAuthority('president')">	
											<petclinic:menuItem active="${name eq 'presidents'}" url="/matchRequests/sent"
												title="sent match requests">
												<span><fmt:message key="code.tag.menu.sentMatchRequests"/></span>
											</petclinic:menuItem>
										</sec:authorize>
										
										<sec:authorize access="hasAuthority('president')">	
											<petclinic:menuItem active="${name eq 'presidents'}" url="/matchRequests/received"
												title="received match requests">
												<span><fmt:message key="code.tag.menu.receivedMatchRequests"/></span>
											</petclinic:menuItem>
										</sec:authorize>
										
										<sec:authorize access="hasAuthority('referee')">	
											<petclinic:menuItem active="${name eq 'referees'}" url="/matches/referee/list/"
												title="personal space">
												<span><fmt:message key="code.tag.menu.matchRefereeList"/></span>
											</petclinic:menuItem>
										</sec:authorize>
										
										<sec:authorize access="hasAuthority('referee')">	
											<petclinic:menuItem active="${name eq 'referees'}" url="/matchRefereeRequest/list"
												title="personal space">
												<span><fmt:message key="code.tag.menu.matchRefereeRequestList"/></span>
											</petclinic:menuItem>
										</sec:authorize>
										
										<sec:authorize access="hasAuthority('referee')">	
											<petclinic:menuItem active="${name eq 'referees'}" url="/myRefereeProfile"
												title="personal space">
												<span><fmt:message key="myProfile"/></span>
											</petclinic:menuItem>
										</sec:authorize>
										
										<sec:authorize access="hasAnyAuthority('admin')">	
											<petclinic:menuItem active="${username eq 'competitionAdmin'}" url="/competitionAdminRequest/list"
												title="club page">
												<span style="color:#ffc800" class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
												<span><fmt:message key="code.tag.menu.compAdminRequestList"/></span>
											</petclinic:menuItem>
										</sec:authorize>
										
										<sec:authorize access="hasAuthority('competitionAdmin')">	
											<petclinic:menuItem active="${name eq 'competitionAdmins'}" url="/myCompetitionAdminProfile"
												title="personal space">
												<span><fmt:message key="myProfile"/></span>
											</petclinic:menuItem>
										</sec:authorize>
										
										<div class="col-lg-4">
											<p style="color:#C42D16" class="text-center">
												<span  class="glyphicon glyphicon-user icon-size"></span>
											</p>
											
										</div>
										
										<div >
											<p style="color:#C42D16" class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-danger btn-block"><fmt:message key="logout"/></a>
											</p>
										</div>
									</div>
								</div>
							</li>
						</ul></li>
				</sec:authorize>
			</ul>	
		</div>
	</div>
</nav>
<sec:authorize access="hasAuthority('president')">	
<nav style="border-color:grey" class="navbar2 navbar2-default">		
		<div class="th-center">		
		
    		<spring:url value="/footballClubs/myClub/footballPlayers" var="footballPlayersUrl"></spring:url>
    		<a   href="${fn:escapeXml(footballPlayersUrl)}" class="btn btn-default3"><span class="glyphicon glyphicon-user"></span> ${playerList}</a>
    		
    		<spring:url value="/contractPlayer/list" var="contractPlayersUrl"></spring:url>
    		<a   href="${fn:escapeXml(contractPlayersUrl)}" class="btn btn-default3"><span class="glyphicon glyphicon-inbox"></span> ${contracts}</a>
    		
    		<spring:url value="/contractsCommercial" var="contractsCommercial"></spring:url>
    		<a   href="${fn:escapeXml(contractsCommercial)}" class="btn btn-default3"><span class="glyphicon glyphicon-inbox"></span> ${publicities}</a>
    		
    		<spring:url value="/transfers/panel" var="transfersUrl"></spring:url>
    		<a   href="${fn:escapeXml(transfersUrl)}" class="btn btn-default3"><span class="glyphicon glyphicon-sort"></span> ${Transfers}</a>
    		
    		<spring:url value="/matches/list" var="matchesUrl"></spring:url>
    		<a   href="${fn:escapeXml(matchesUrl)}" class="btn btn-default3"><span class="glyphicon glyphicon-list-alt"></span> <fmt:message key="code.tag.menu.matchList"/></a>
    		
    	</div> 
	</nav>
</sec:authorize>

<sec:authorize access="hasAuthority('competitionAdmin')">	
<nav style="border-color:grey" class="navbar2 navbar2-default">		
		<div class="th-center">		
		
    		<spring:url value="/competition/new" var="createCompetitionUrl"></spring:url>
    		<a   href="${fn:escapeXml(createCompetitionUrl)}" class="btn btn-default3"><span class="glyphicon glyphicon-user"></span> ${newTournament}</a>
    		
    		<spring:url value="/competition/mylist" var="mylistCompetitionUrl"></spring:url>
    		<a   href="${fn:escapeXml(mylistCompetitionUrl)}" class="btn btn-default3"><span class="glyphicon glyphicon-user"></span> ${myTournaments}</a>
    
    	</div> 
	</nav>
</sec:authorize>