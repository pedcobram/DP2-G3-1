package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F17PerformanceTest extends Simulation {

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
		"Referer" -> "http://www.dp2.com/logout",
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

	val headers_5 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/competition/mylist",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/competitions/1",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(7)
	}

	object Login {
    	val login = exec(
      		http("Login")
        		.get("/login")
        		.headers(headers_1)
        		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
    		).pause(12)
    		.exec(
      		http("Logged")
        		.post("/login")
        		.headers(headers_3)
        		.formParam("username", "pedro")
        		.formParam("password", "pedro")        
        		.formParam("_csrf", "${stoken}")
    		).pause(28)
  	}

	object ShowMyCompetitions {
		val showMyCompetitions = exec(http("ShowMyCompetitions")
			.get("/competition/mylist")
			.headers(headers_1))
		.pause(8)
	}

	object CompetitionDetails {
		val competitionDetails = exec(http("CompetitionDetails")
			.get("/competitions/1")
			.headers(headers_5))
		.pause(8)
	}

	object DeleteCompetition {
		val deleteCompetition = exec(http("DeleteCompetition")
			.get("/competition/1/delete")
			.headers(headers_6))
		.pause(8)
	}

	val deleteScn = scenario("F17DeleteCompetition").exec(Home.home, 
												Login.login, 						
												ShowMyCompetitions.showMyCompetitions, 
												CompetitionDetails.competitionDetails,
												DeleteCompetition.deleteCompetition, 
												)

	setUp(
			deleteScn.inject(rampUsers(10500) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}