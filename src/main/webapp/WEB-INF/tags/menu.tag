<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

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

				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="tournaments">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span><fmt:message key="tournaments"/></span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'footballClubs'}" url="/footballClub"
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
						
				<sec:authorize access="hasAnyAuthority('president', 'director')">	
					<petclinic:menuItem active="${name eq 'footballClubs'}" url="/myfootballClub/${principalUsername}"
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
						
						<sec:authorize access="!hasAnyAuthority('president', 'director', 'inversor')">
							<li><a href="<c:url value="/createPresident" />"><fmt:message key="becPresident"/></a></li>
						</sec:authorize>
						
						<sec:authorize access="!hasAnyAuthority('president', 'director', 'inversor')">
							<li><a href="<c:url value="/presidents/new" />"><fmt:message key="becDirector"/></a></li>
						</sec:authorize>
						
						<sec:authorize access="!hasAnyAuthority('president', 'director', 'inversor')">
							
							<li><a href="<c:url value="/presidents/new" />"><fmt:message key="becInversor"/></a></li>
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
											<petclinic:menuItem active="${name eq 'authenticateds'}" url="/myPresidentProfile/${principalUsername}"
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
