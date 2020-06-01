package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F14PerformanceTest extends Simulation {

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
		"Referer" -> "http://www.dp2.com/footballClubs/myClub/footballPlayers",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/footballPlayer/new",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_8 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/footballClubs/myClub/owner8",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_9 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/coach/new",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(5)
	}

	object Login {
    	val login = exec(
      		http("Login")
        		.get("/login")
        		.headers(headers_1)
        		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
    		).pause(11)
    		.exec(
      		http("Logged")
        		.post("/login")
        		.headers(headers_2)
        		.formParam("username", "owner8")
        		.formParam("password", "owner8")        
        		.formParam("_csrf", "${stoken}")
    		).pause(13)
  	}

	object MyPlayerList {
		val myPlayerList = exec(http("MyPlayerList")
			.get("/footballClubs/myClub/footballPlayers")
			.headers(headers_1))
		.pause(10)
	}

	object RegisterPlayer {
		val registerPlayer = exec(http("RegisterPlayer")
			.get("/footballPlayer/new")
			.headers(headers_5)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(12)
		.exec(http("PlayerRegistered")
			.post("/footballPlayer/new")
			.headers(headers_6)
			.formParam("firstName", "asd")
			.formParam("lastName", "asd")
			.formParam("birthDate", "2000/05/05")
			.formParam("position", "DEFENDER")
			.formParam("_csrf", "${stoken}")
		).pause(9)
	}

	object MyClubDetails {
		val myClubDetails = exec(http("MyClubDetails")
			.get("/footballClubs/myClub/owner8")
			.headers(headers_5))
		.pause(11)
	}

	object RegisterCoach {
		val registerCoach = exec(http("RegisterCoach")
			.get("/coach/new")
			.headers(headers_8)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(17)
		.exec(http("CoachRegistered")
			.post("/coach/new")
			.headers(headers_9)
			.formParam("firstName", "asd")
			.formParam("lastName", "asd")
			.formParam("birthDate", "2000/05/04")
			.formParam("salary", "1000000")
			.formParam("_csrf", "${stoken}")
		).pause(10)
	}

	val registerPlayerScn = scenario("F14RegisterPlayer").exec(Home.home, 
												Login.login,
												MyPlayerList.myPlayerList,
												RegisterPlayer.registerPlayer, 
												)
		
	val registerCoachScn = scenario("F14RegisterCoach").exec(Home.home, 
												Login.login, 						
												MyClubDetails.myClubDetails, 
												RegisterCoach.registerCoach, 
												)

	setUp(
			registerPlayerScn.inject(rampUsers(4000) during (100 seconds)),
			registerCoachScn.inject(rampUsers(4000) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}