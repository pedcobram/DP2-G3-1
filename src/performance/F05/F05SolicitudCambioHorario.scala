package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F05SolicitudCambioHorario extends Simulation {

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

	val headers_1 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(12)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/login")
			.headers(headers_1))
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(8)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "presidente1")
			.formParam("password", "presidente1")
			.formParam("_csrf", "${stoken}"))
		.pause(11)
	}

	object MatchList {
		val matchList = exec(http("MatchList")
			.get("/matches/list")
			.headers(headers_0)
			).pause(12)
	}

	object MatchDateChangeForm {
		val matchDateChangeForm = exec(http("MatchDateChangeForm")
			.get("/matches/edit/date/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken2"))
			).pause(22)
		.exec(http("MatchDateChangeFormPOST")
			.post("/matches/edit/date/1")
			.headers(headers_2)
			.formParam("title", "Match title 0")
			.formParam("new_date", "2024/05/11 22:30")
			.formParam("reason", " 123123")
			.formParam("status", "ON_HOLD")
			.formParam("request_creator", "presidente1")
			.formParam("_csrf", "${stoken2}"))
		.pause(21)
	}

	object MatchDateChangeRequestList {
		val matchDateChangeRequestList = exec(http("MatchDateChangeRequestList")
			.get("/matches/date-request/list")
			.headers(headers_0))
	}

	object Logged2 {
		val logged2 = exec(http("Logged2")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken3")))
		.pause(7)
		.exec(http("request_3")
			.post("/login")
			.headers(headers_1)
			.formParam("username", "presidente2")
			.formParam("password", "presidente2")
			.formParam("_csrf", "${stoken3}"))
		.pause(8)
	}

	object Lista {
		val lista = exec(http("Lista")
			.get("/matches/date-request/list")
			.headers(headers_0))
		.pause(20)
	}

	object DeleteRequest {
		val deleteRequest = exec(http("DeleteRequest")
			.get("/matches/date-request/delete/2")
			.headers(headers_0))
		.pause(18)
	}

	val scnPositivo = scenario("F05SolicitudCambioHorario").exec(Home.home,
														Login.login,
														MatchList.matchList,
														MatchDateChangeForm.matchDateChangeForm,
														MatchDateChangeRequestList.matchDateChangeRequestList
	)

	val scnDelete = scenario("F05DeleteSolicitudCambioHorario").exec(Home.home,
																Logged2.logged2,
																Lista.lista,
																DeleteRequest.deleteRequest
	)


	setUp(
		scnPositivo.inject(rampUsers(2000) during (100 seconds)),
		scnDelete.inject(rampUsers(2000) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
	)
}