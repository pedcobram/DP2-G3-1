package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F16PerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.png""", """.*.css""", """.*.ico"""), WhiteList())
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-WNS/10.0")

    object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(12)
		
	}
	
	object Login {
    	val login = exec(
      		http("Login")
        		.get("/login")
        		.headers(headers_0)
        		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
    		).pause(20)
    		.exec(
      		http("Logged")
        		.post("/login")
        		.headers(headers_3)
        		.formParam("username", "pedro")
        		.formParam("password", "pedro")        
        		.formParam("_csrf", "${stoken}")
    		).pause(30)
  	}
	object ShowMyCompetitions {
		val showMyCompetitions = exec(http("ShowMyCompetitions")
			.get("/competition/mylist")
			.headers(headers_0))
		.pause(10)
		
	}
	object ShowCompetition {
		val showCompetition = exec(http("ShowCompetition")
			.get("/competitions/3")
			.headers(headers_0))
		.pause(3)
	}
	
	object ShowRounds {
		val showRounds = exec(http("ShowRounds")
			.get("/competitions/3/rounds")
			.headers(headers_0))
		.pause(11)
		
	}
	object ShowRound {
		val showRound = exec(http("ShowRound")
			.get("/competitions/3/round/1")
			.headers(headers_0))
		.pause(14)
	}
	
	
	object EditMatch {
		val editMatch = exec(http("ShowMatch")
			.get("/competitions/3/round/1/match/8")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(24)
		.exec(http("EditMatch")
			.post("/competitions/3/round/1/match/8")
			.headers(headers_3)
			.formParam("matchDate", "2021/05/31 22:30")
			.formParam("_csrf","${stoken}"))
		.pause(25)
	}
	object NotEditMatch {
		val notEditMatch =  exec(http("ShowMatch")
			.get("/competitions/3/round/1/match/8")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(24)
		.exec(http("NotEditMatch")
			.post("/competitions/3/round/1/match/8")
			.headers(headers_3)
			.formParam("matchDate", "2021/05/30")
			.formParam("_csrf", "${stoken}"))
		.pause(20)
		
	}

	val scnEditMatch = scenario("F16PerformanceTest_EditMatch").exec(Home.home,
																	Login.login,
																	ShowMyCompetitions.showMyCompetitions,
																	ShowCompetition.showCompetition,
																	ShowRounds.showRounds,
																	ShowRound.showRound,
																	EditMatch.editMatch)
															
		
	val scnNotEditMatch = scenario("F16PerformanceTest_NotEditMatch").exec(Home.home,
																			Login.login,
																			ShowMyCompetitions.showMyCompetitions,
																			ShowCompetition.showCompetition,
																			ShowRounds.showRounds,
																			ShowRound.showRound,
																			NotEditMatch.notEditMatch)	
		
	
		
	

	setUp(scnEditMatch.inject(rampUsers(2000) during(100 seconds)),
		  scnNotEditMatch.inject(rampUsers(2000) during(100 seconds)))
		  .protocols(httpProtocol)
		  .assertions(
			  global.responseTime.max.lt(5000),
			  global.responseTime.mean.lt(1000),
			  global.successfulRequests.percent.gt(95)
		  )
}