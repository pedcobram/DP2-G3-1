package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F18CerrarActaArbitro extends Simulation {

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
		.pause(2)
	}

	object LoginReferee {
		val loginReferee = exec(http("Login_Ref")
			.get("/login")
			.headers(headers_0)
			.resources(http("Login_Ref_Interno")
			.get("/login")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(2)
		.exec(http("Logged_Ref")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "referee1")
			.formParam("password", "referee1")
			.formParam("_csrf", "${stoken}"))
		.pause(3)
	}

	object MatchList {
		val matchList = exec(http("MatchList")
			.get("/matches/referee/list/")
			.headers(headers_0))
		.pause(1)
	}

	object MatchRecord {
		val matchRecord = exec(http("MatchRecord")
			.get("/matches/matchRecord/3/view")
			.headers(headers_0))
		.pause(5)
	}

	object UpdateRecord {
		val updateRecord = exec(http("UpdateRecordSubstract")
			.get("/matches/matchRecord/assist/substract/6/1")
			.headers(headers_0)
			.resources(http("UpdateRecordAdd")
			.get("/matches/matchRecord/goal/add/6/1")
			.headers(headers_0)))
		.pause(3)
	}

	object EditRecord {
		val editRecord = exec(http("EditRecord")
			.get("/matches/matchRecord/3/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(2)
		.exec(http("EditRecord_POST")
			.post("/matches/matchRecord/3/edit")
			.headers(headers_3)
			.formParam("title", "Match title 3 Record")
			.formParam("season_start", "2020")
			.formParam("result", "0-2")
			.formParam("winner", "Sevilla Fútbol Club")
			.formParam("status", "PUBLISHED")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}

	object LoginPresident {
		val loginPresident = exec(http("Login_President")
			.get("/login")
			.headers(headers_0)
			.resources(http("Login_President_Interno")
			.get("/login")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(6)
		.exec(http("Logged_President")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "presidente1")
			.formParam("password", "presidente1")
			.formParam("_csrf", "${stoken}"))
		.pause(4)
	}

	object ErrorMatchRecord {
		val errorMatchRecord = exec(http("ErrorMatchRecord")
			.get("/matches/matchRecord/3/edit")
			.headers(headers_0))
		.pause(3)
	}

	val scnCorrecto = scenario("F18CerrarActaArbitroCorrecto").exec(Home.home,
													LoginReferee.loginReferee,
													MatchList.matchList,
													MatchRecord.matchRecord,
													UpdateRecord.updateRecord,
													EditRecord.editRecord
	)

	val scnError = scenario("F18CerrarActaArbitroError").exec(Home.home,
													LoginPresident.loginPresident,
													ErrorMatchRecord.errorMatchRecord
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