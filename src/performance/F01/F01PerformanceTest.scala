package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F01PerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpg""", """.*.less""", """.*.woff""", """.*.woff2"""), WhiteList())
		
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

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
		.pause(12)
		
	}

	object LoginManuel {
    	val loginM = exec(
      		http("LoginM")
        		.get("/login")
        		.headers(headers_0)
        		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
    		).pause(20)
    		.exec(
      		http("LoggedM")
        		.post("/login")
        		.headers(headers_3)
        		.formParam("username", "manuel")
        		.formParam("password", "manuel")        
        		.formParam("_csrf", "${stoken}")
    		).pause(30)
  	}

	  object LoginGonzalo {
    	val loginG = exec(
      		http("LoginG")
        		.get("/login")
        		.headers(headers_0)
        		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
    		).pause(20)
    		.exec(
      		http("LoggedG")
        		.post("/login")
        		.headers(headers_3)
        		.formParam("username", "gonzalo")
        		.formParam("password", "gonzalo")        
        		.formParam("_csrf", "${stoken}")
    		).pause(30)
  	}

	object ShowFootballClubs {
		val showFootballClubs = exec(http("ShowFootballClubs")
			.get("/footballClubs/list")
			.headers(headers_0))
		.pause(5)
	
	}
	
	object ShowFootballClub {
		 val showFootballClub =exec(http("ShowFootballClub")
			.get("/footballClubs/list/4")
			.headers(headers_0))
		.pause(11)
	}

	object TypeFanForm {
		val typeFanForm =exec(http("typeFanForm")
			.get("/fan/4/new")
			.headers(headers_0))
		.pause(4)
	}
	
	object FanNormal {
		val fanNormal=exec(http("FanNormal")
			.get("/")
			.headers(headers_0))	
		.pause(3)
	}

	object FanVip {
		val fanVip = exec(http("FanVip")
			.get("/fan/4/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(25)
		.exec(http("FanVipDone")
			.post("/fan/4/new")
			.headers(headers_3)
			.formParam("id", "2")
			.formParam("clubId", "4")
			.formParam("userId", "2")
			.formParam("creditCard.creditCardNumber", "7894561237894561")
			.formParam("creditCard.expirationDate", "11/23")
			.formParam("creditCard.cvv", "879")
			.formParam("_csrf", "${stoken}")
		).pause(46)
	}

	val scnNormal = scenario("F01PerformanceTest_FanNormal").exec(Home.home,
															LoginManuel.loginM,
															ShowFootballClubs.showFootballClubs,
															ShowFootballClub.showFootballClub,
															TypeFanForm.typeFanForm,
															FanNormal.fanNormal)
	val scnVip = scenario("F01PerformanceTest_FanVip").exec(Home.home,
															LoginGonzalo.loginG,
															ShowFootballClubs.showFootballClubs,
															ShowFootballClub.showFootballClub,
															FanVip.fanVip)
		
		
	setUp(scnNormal.inject(rampUsers(1500) during(100 seconds)),
		  scnVip.inject(rampUsers(1500) during(100 seconds)))
		  .protocols(httpProtocol)
		  .assertions(
			  global.responseTime.max.lt(5000),
			  global.responseTime.mean.lt(1000),
			  global.successfulRequests.percent.gt(95)
		  )
}