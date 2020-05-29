package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F03PerformanceTest extends Simulation {

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
		"Referer" -> "http://www.dp2.com/transfers/panel",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/transfers/players/free-agents",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_7 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/contractPlayer/64/new",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_8 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/contractPlayer/64",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_9 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/contractPlayer/list",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_10 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Referer" -> "http://www.dp2.com/contractPlayer/1",
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
			.headers(headers_1))
		.pause(19)
	}

	object ShowFreeAgentPlayers {
		val showFreeAgentPlayers = exec(http("ShowFreeAgentPlayers")
			.get("/transfers/players/free-agents")
			.headers(headers_5))
		.pause(13)
	}

	object SignPlayer {
		val signPlayer = exec(http("SignPlayer")
			.get("/contractPlayer/64/new")
			.headers(headers_6)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(25)
		.exec(http("PlayerSigned")
			.post("/contractPlayer/64/new")
			.headers(headers_7)
			.formParam("salary", "1000000")
			.formParam("startDate", "")
			.formParam("endDate", "2025/05/30")
			.formParam("clause", "")
			.formParam("_csrf", "${stoken}")
		).pause(46)
	}

	object ShowContractPlayerList {
		val showContractPlayerList = exec(http("ShowContractPlayerList")
			.get("/contractPlayer/list")
			.headers(headers_8))
		.pause(16)
	}

	object ShowContractPlayer {
		val showContractPlayer = exec(http("ShowContractPlayer")
			.get("/contractPlayer/1")
			.headers(headers_9))
		.pause(23)
	}

	object PlayerFired {
		val playerFired = exec(http("PlayerFired")
			.get("/contractPlayer/1/delete")
			.headers(headers_10))
		.pause(12)
	}

	val signScn = scenario("F03SignFreeAgent").exec(Home.home, 
												Login.login,  
												ShowTransferPanel.showTransferPanel,
												ShowFreeAgentPlayers.showFreeAgentPlayers, 
												SignPlayer.signPlayer, 
												)
		
	val fireScn = scenario("F03FirePlayer").exec(Home.home, 
												Login.login, 						
												ShowContractPlayerList.showContractPlayerList, 
												ShowContractPlayer.showContractPlayer,
												PlayerFired.playerFired, 
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