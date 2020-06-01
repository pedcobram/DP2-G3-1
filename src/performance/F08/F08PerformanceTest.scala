package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F08PerformanceTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpg""", """.*.less"""), WhiteList())
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
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
        		.formParam("username", "rafa")
        		.formParam("password", "rafa")        
        		.formParam("_csrf", "${stoken}")
    		).pause(30)
  	}

	  object Login2 {
    	val login2 = exec(
      		http("Login")
        		.get("/login")
        		.headers(headers_0)
        		.check(css("input[name=_csrf]", "value").saveAs("stoken"))
    		).pause(20)
    		.exec(
      		http("Logged")
        		.post("/login")
        		.headers(headers_3)
        		.formParam("username", "owner7")
        		.formParam("password", "owner7")        
        		.formParam("_csrf", "${stoken}")
    		).pause(30)
  	}


	object MyClubEmpty {
		val myClubEmpty = exec(http("MyClubEmpty")
			.get("/footballClubs/myClub/rafa")
			.headers(headers_0))
		.pause(1)
	}

	object MyClubEmpty2 {
		val myClubEmpty2 = exec(http("MyClubEmpty2")
			.get("/footballClubs/myClub/owner7")
			.headers(headers_0))
		.pause(1)
	}

	object NewClub {
		val newClub = exec(http("NewClub")
			.get("/footballClubs/myClub/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(25)
		.exec(http("NewClubCreated")
			.post("/footballClubs/myClub/new")
			.headers(headers_3)
			.formParam("name", "Rafa Fc")
			.formParam("crest", "")
			.formParam("city", "Sevilla")
			.formParam("stadium", "RF STADIUM")
			.formParam("foundationDate", "2020/05/12")
			.formParam("_csrf", "${stoken}")
		).pause(19)
	}

	object UpdateClub {
		val updateClub = exec(http("UpdateClub")
			.get("/footballClubs/myClub/owner7/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(25)
		.exec(http("UpdateClubDone")
			.post("/footballClubs/myClub/owner7/edit")
			.headers(headers_3)
			.formParam("name", "Rafa Fc Edit")
			.formParam("crest", "")
			.formParam("city", "Sevilla Edit")
			.formParam("stadium", "RF STADIUM")
			.formParam("foundationDate", "2020/05/12")
			.formParam("status", "false")
			.formParam("_csrf", "${stoken}")
		).pause(19)
	}


	val registerScn = scenario("F08RegisterTeam").exec(Home.home, 
												Login.login,  
												MyClubEmpty.myClubEmpty, 
												NewClub.newClub, 
												)
		
	val editScn = scenario("F08EditTeam").exec(Home.home, 
												Login2.login2, 						
												MyClubEmpty2.myClubEmpty2, 
												UpdateClub.updateClub, 
												)

	setUp(
			registerScn.inject(rampUsers(1500) during (100 seconds)),
			editScn.inject(rampUsers(1500) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}