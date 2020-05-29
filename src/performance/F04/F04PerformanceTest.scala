package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F04PerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpg""", """.*.less""", """.*.woff""", """.*.woff2"""), WhiteList())
		.disableAutoReferer
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"A-IM" -> "x-bm,gzip",
		"Accept-Encoding" -> "gzip, deflate",
		"Proxy-Connection" -> "keep-alive")

	val headers_1 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_3 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/login")

	val headers_4 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/login",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/footballClubs/myClub/presidente1",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_7 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/coachs/1",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_9 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/transfers/panel",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_10 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/transfers/coaches/free-agents",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_11 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/coachs/10",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_13 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/coach/10/sign",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(9)
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
        		.formParam("username", "presidente1")
        		.formParam("password", "presidente1")        
        		.formParam("_csrf", "${stoken}")
    		).pause(30)
  	}

	  object ShowTransferPanel {
		val showTransferPanel = exec(http("ShowTransferPanel")
			.get("/transfers/panel")
			.headers(headers_6))
		.pause(13)
	}

	object ShowFreeAgentCoaches {
		val showFreeAgentCoaches = exec(http("ShowFreeAgentCoaches")
			.get("/transfers/coaches/free-agents")
			.headers(headers_9))
		.pause(23)
	}

	object CoachDetails {
		val coachDetails = exec(http("CoachDetails")
			.get("/coachs/10")
			.headers(headers_10))
		.pause(15)
	}

	object SignCoach {
		val signCoach = exec(http("SignCoach")
			.get("/coach/10/sign")
			.headers(headers_11)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(5)
		.exec(http("CoachSigned")
			.post("/coach/10/sign")
			.headers(headers_13)
			.formParam("firstName", "Marcelino")
			.formParam("lastName", "Garcia Toral")
			.formParam("birthDate", "1975/01/19")
			.formParam("salary", "2000000")
			.formParam("_csrf", "${stoken}")
		).pause(9)
	}

	object ShowMyClub {
		val showMyClub = exec(http("ShowMyClub")
			.get("/footballClubs/myClub/presidente1")
			.headers(headers_2))
		.pause(14)
	}

	object CoachDetailsToFire {
		val coachDetailsToFire = exec(http("CoachDetailsToFire")
			.get("/coachs/1")
			.headers(headers_6))
		.pause(17)
	}

	object FiredCoach {
		val firedCoach = exec(http("FiredCoach")
			.get("/coach/1/fire")
			.headers(headers_7))
		.pause(9)
	}

	val fireScn = scenario("F04FireCoach").exec(Home.home, 
												Login.login, 						
												ShowMyClub.showMyClub, 
												CoachDetailsToFire.coachDetailsToFire,
												FiredCoach.firedCoach, 
												)

	val signScn = scenario("F04SignFreeAgentCoach").exec(Home.home, 
												Login.login,  
												ShowTransferPanel.showTransferPanel,
												ShowFreeAgentCoaches.showFreeAgentCoaches, 
												CoachDetails.coachDetails,
												SignCoach.signCoach, 
												)
		

	setUp(
			signScn.inject(rampUsers(1500) during (100 seconds)),
			fireScn.inject(rampUsers(1500) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}