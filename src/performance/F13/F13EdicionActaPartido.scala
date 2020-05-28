package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F13EdicionActaPartido extends Simulation {

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
		val home = exec(http("request_0")
			.get("/")
			.headers(headers_0))
		.pause(5)
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
			.formParam("username", "referee1")
			.formParam("password", "referee1")
			.formParam("_csrf", "${stoken}"))
		.pause(7)
	}

	object MatchList {
		val matchList = exec(http("MatchList")
			.get("/matches/referee/list/")
			.headers(headers_0))
		.pause(7)
	}

	object MatchRecordDetails {
		val matchRecordDetails = exec(http("MatchRecordDetails")
			.get("/matches/matchRecord/8/view")
			.headers(headers_0))
		.pause(5)
	}

	object EditMatchRecordCorrect {
		val editMatchRecordCorrect = exec(http("EditMatchRecordFormCorrect")
			.get("/matches/matchRecord/8/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
		.exec(http("EditMatchRecordCorrect")
			.post("/matches/matchRecord/8/edit")
			.headers(headers_3)
			.formParam("title", "title 111")
			.formParam("season_start", "2020")
			.formParam("result", "")
			.formParam("winner", "Fútbol Club Barcelona")
			.formParam("status", "NOT_PUBLISHED")
			.formParam("_csrf", "${stoken}"))
		.pause(6)
	}

	object EditMatchRecordError {
		val editMatchRecordError = exec(http("EditMatchRecordFormError")
			.get("/matches/matchRecord/8/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(8)
		.exec(http("EditMatchRecordError")
			.post("/matches/matchRecord/8/edit")
			.headers(headers_3)
			.formParam("title", "title 111")
			.formParam("season_start", "2020")
			.formParam("result", "")
			.formParam("winner", "Fútbol Club Barcelona")
			.formParam("status", "PUBLISHED")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}

	val scnCorrecto = scenario("F13EdicionActaPartidoCorrecto").exec(Home.home,
													Login.login,
													MatchList.matchList,
													MatchRecordDetails.matchRecordDetails,
													EditMatchRecordCorrect.editMatchRecordCorrect
	)

	val scnError = scenario("F13EdicionActaPartidoError").exec(Home.home,
													Login.login,
													MatchList.matchList,
													MatchRecordDetails.matchRecordDetails,
													EditMatchRecordError.editMatchRecordError
	)

	setUp(
		scnCorrecto.inject(rampUsers(2000) during (100 seconds)),
		scnError.inject(rampUsers(2000) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
	)
}