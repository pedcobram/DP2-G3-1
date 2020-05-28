package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F11SolicitudPartidoAmistosoArbitro extends Simulation {

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
		.pause(7)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("Login_Interno")
			.get("/login")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(7)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "referee1")
			.formParam("password", "referee1")
			.formParam("_csrf", "${stoken}"))
		.pause(7)
	}

	object ListaPeticionesPartido {
		val listaPeticionesPartido = exec(http("ListaPeticionesPartido")
			.get("/matchRefereeRequest/list")
			.headers(headers_0))
		.pause(14)
	}

	object AceptarPartido {
		val aceptarPartido = exec(http("AceptarPartido")
			.get("/matchRefereeRequest/list/accept/3")
			.headers(headers_0))
		.pause(6)
	}

	object RechazarPartido {
		val rechazarPartido = exec(http("RechazarPartido")
			.get("/matchRefereeRequest/list/reject/10")
			.headers(headers_0))
		.pause(5)
	}

	val scnAceptar = scenario("F11AceptarSolicitudPartidoAmistosoArbitro").exec(Home.home,
																Login.login,
																ListaPeticionesPartido.listaPeticionesPartido,
																AceptarPartido.aceptarPartido)

	val scnRechazar = scenario("F11RechazarSolicitudPartidoAmistosoArbitro").exec(Home.home,
																Login.login,
																ListaPeticionesPartido.listaPeticionesPartido,
																RechazarPartido.rechazarPartido)

	setUp(
		scnAceptar.inject(rampUsers(2000) during (100 seconds)),
		scnRechazar.inject(rampUsers(2000) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
	)
}