package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F10AceptarSolicitudPartidoAmistoso extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
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
		.pause(5)
	}

	object Logged {
		val logged = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("Login_Interno")
			.get("/login")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(6)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "presidente1")
			.formParam("password", "presidente1")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object ListaPartidosRecibidos {
		val listaPartidosRecibidos = exec(http("ListaPartidosRecibidos")
			.get("/matchRequests/received")
			.headers(headers_0))
		.pause(16)
	}

	object AceptarPartido {
		val aceptarPartido = exec(http("AceptarPartido")
			.get("/matchRequests/accept/5")
			.headers(headers_0))
		.pause(12)
	}

	object RechazarPartido {
		val rechazarPartido = exec(http("RechazarPartido")
			.get("/matchRequests/reject/6")
			.headers(headers_0))
		.pause(8)
	}

	val scnAceptar = scenario("F10AceptarSolicitudPartidoAmistoso").exec(Home.home,
															Logged.logged,
															ListaPartidosRecibidos.listaPartidosRecibidos,
															AceptarPartido.aceptarPartido)

	val scnRechazar = scenario("F10RechazarSolicitudPartidoAmistoso").exec(Home.home,
															Logged.logged,
															ListaPartidosRecibidos.listaPartidosRecibidos,
															RechazarPartido.rechazarPartido)														



	setUp(
		scnAceptar.inject(rampUsers(5000) during (100 seconds)),
		scnRechazar.inject(rampUsers(5000) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
	)
}