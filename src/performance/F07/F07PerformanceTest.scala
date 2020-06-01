package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F07PerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpg""", """.*.less""", """.*.woff""", """.*.woff2"""), WhiteList())
		.disableAutoReferer
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/login")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/login",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_4 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/login-error",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/competitions/list",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_7 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/competitions/2",
		"Upgrade-Insecure-Requests" -> "1")


	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
	}

	 object Login {
    	val login = exec(
      		http("Login")
        		.get("/login")
        		.headers(headers_0)
        		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
    		).pause(9)
    		.exec(
      		http("Logged")
        		.post("/login")
        		.headers(headers_3)
        		.formParam("username", "rafa")
        		.formParam("password", "rafa")        
        		.formParam("_csrf", "${stoken}")
    		).pause(9)
  	}

	object ShowCompetitionList {
		val showCompetitionList = exec(http("ShowCompetitionList")
			.get("/competitions/list")
			.headers(headers_1))
		.pause(10)
	}

	object CompetitionDetails {
		val competitionDetails = exec(http("CompetitionDetails")
			.get("/competitions/2")
			.headers(headers_6))
		.pause(7)
	}

	object ShowCompetitionStats {
		val showCompetitionStats = exec(http("ShowCompetitionStats")
			.get("/competitions/2/statistics")
			.headers(headers_7))
		.pause(18)
	}

	val statsScn = scenario("F07ShowStatistics").exec(Home.home, 
												Login.login,  
												ShowCompetitionList.showCompetitionList, 
												CompetitionDetails.competitionDetails,
												ShowCompetitionStats.showCompetitionStats,
												)
		

	setUp(
			statsScn.inject(rampUsers(5500) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}