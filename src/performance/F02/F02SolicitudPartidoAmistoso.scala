package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F02SolicitudPartidoAmistoso extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*\.jpeg""", """.*\.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es,en-US;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(4)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("Login_Interno")
			.get("/login")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(3)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "presidente1")
			.formParam("password", "presidente1")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
	}

	object TeamList {
		val teamList = exec(http("TeamList")
			.get("/footballClubs/list")
			.headers(headers_0))
		.pause(3)
	}

	object TeamDetails {
		val teamDetails = exec(http("TeamDetails")
			.get("/footballClubs/list/6")
			.headers(headers_0))
		.pause(2)
	}

	object ErrorCreacion {
		val errorCreacion = exec(http("CreacionForm")
			.get("/matchRequests/owner4/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(2)
		.exec(http("ErrorCreacion")
			.post("/matchRequests/owner4/new")
			.headers(headers_3)
			.formParam("title", "Sevilla Fútbol Club vs Real Betis Balompié ")
			.formParam("matchDate", "2020/05/29 00:39")
			.formParam("stadium", "Benito Villamarín")
			.formParam("footballClub1.name", "Sevilla Fútbol Club")
			.formParam("status", "ON_HOLD")
			.formParam("footballClub2.name", "Real Betis Balompié")
			.formParam("creator", "presidente1")
			.formParam("_csrf", "${stoken}"))
		.pause(4)
	}

	object CreacionCorrecta {
		val creacionCorrecta = exec(http("CreacionForm")
			.get("/matchRequests/owner4/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(2)
		.exec(http("CreacionCorrecta")
			.post("/matchRequests/owner4/new")
			.headers(headers_3)
			.formParam("title", "Sevilla Fútbol Club vs Real Betis Balompié ")
			.formParam("matchDate", "2021/05/29 00:39")
			.formParam("stadium", "Benito Villamarín")
			.formParam("footballClub1.name", "Sevilla Fútbol Club")
			.formParam("status", "ON_HOLD")
			.formParam("footballClub2.name", "Real Betis Balompié")
			.formParam("creator", "presidente1")
			.formParam("_csrf", "${stoken}"))
		.pause(4)
	}

	val scnCorrecta = scenario("F02SolicitudPartidoAmistosoCorrecto").exec(Home.home,
														Login.login,
														TeamList.teamList,
														TeamDetails.teamDetails,
														CreacionCorrecta.creacionCorrecta
	)

	val scnError = scenario("F02SolicitudPartidoAmistosoError").exec(Home.home,
														Login.login,
														TeamList.teamList,
														TeamDetails.teamDetails,
														ErrorCreacion.errorCreacion
	)
		
	setUp(
		scnCorrecta.inject(rampUsers(2500) during (100 seconds)),
		scnError.inject(rampUsers(2500) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
	)
}