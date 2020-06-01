package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F06PerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpg""", """.*.less""", """.*.woff""", """.*.woff2"""), WhiteList())
		.disableAutoReferer
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/login")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/login",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/competition/new",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_8 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/competitions/5",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_9 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/competition/mylist",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_12 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/competitions/1",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_13 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/competition/1/edit",
		"Upgrade-Insecure-Requests" -> "1")

    
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
    		).pause(9)
    		.exec(
      		http("Logged")
        		.post("/login")
        		.headers(headers_3)
        		.formParam("username", "pedro")
        		.formParam("password", "pedro")        
        		.formParam("_csrf", "${stoken}")
    		).pause(9)
  	}

	  object CreateCompetition {
		val createCompetition = exec(http("CreateCompetition")
			.get("/competition/new")
			.headers(headers_1)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(21)
		.exec(http("CompetitionCreated")
			.post("/competition/new")
			.headers(headers_6)
			.formParam("name", "asd")
			.formParam("description", "asd")
			.formParam("reward", "5000000")
			.formParam("type", "LEAGUE")
			.formParam("_csrf", "${stoken}")
		).pause(22)
		}

	object ShowMyCompetitions {
		val showMyCompetitions = exec(http("ShowMyCompetitions")
			.get("/competition/mylist")
			.headers(headers_8))
		.pause(13)
	}

	object CompetitionDetails {
		val competitionDetails = exec(http("CompetitionDetails")
			.get("/competitions/1")
			.headers(headers_9))
		.pause(11)
	}
	
	object EditCompetition {
		val editCompetition = exec(http("EditCompetition")
			.get("/competition/1/edit")
			.headers(headers_12)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(13)
		.exec(http("CompetitionEdited")
			.post("/competition/new")
			.headers(headers_13)
			.formParam("name", "Premier Leagues EDIT")
			.formParam("description", "Torneo donde los equipos participantes jugaran todos contra todos.")
			.formParam("reward", "10000000")
			.formParam("type", "LEAGUE")
			.formParam("_csrf", "${stoken}")
		).pause(6)
		}

	val createScn = scenario("F06CreateCompetition").exec(Home.home, 
												Login.login,  
												CreateCompetition.createCompetition, 
												)
		
	val editScn = scenario("F06EditCompetition").exec(Home.home, 
												Login.login, 						
												ShowMyCompetitions.showMyCompetitions, 
												CompetitionDetails.competitionDetails,
												EditCompetition.editCompetition, 
												)

	setUp(
			createScn.inject(rampUsers(5500) during (100 seconds)),
			editScn.inject(rampUsers(5500) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}