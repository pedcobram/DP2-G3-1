package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class F20CambiarContratoPublicitario extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources()
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*\.jpeg""", """.*\.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
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

	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(3)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("Login_Interno")
			.get("/login")
			.headers(headers_2))
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(3)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "presidente1")
			.formParam("password", "presidente1")
			.formParam("_csrf", "${stoken}"))
		.pause(2)
	}

	object ShowMyClub {
		val showMyClub = exec(http("ShowMyClub")
			.get("/footballClubs/myClub/presidente1")
			.headers(headers_0))
		.pause(4)
	}

	object RemoveContract {
		val removeContract = exec(http("RemoveContract")
			.get("/contractsCommercial/1/removeFromMyClub")
			.headers(headers_0))
		.pause(2)
	}

	object ContractList {
		val contractList = exec(http("ContractList")
			.get("/contractsCommercial")
			.headers(headers_0))
		.pause(4)
	}

	object ContractDetails {
		val contractDetails = exec(http("ContractDetails")
			.get("/contractsCommercial/1")
			.headers(headers_0))
		.pause(3)
	}

	object ObtainContract {
		val obtainContract = exec(http("ObtainContract")
			.get("/contractsCommercial/1/addToMyClub")
			.headers(headers_0))
		.pause(2)
	}

	val scn = scenario("F20CambiarContratoPublicitario").exec(Home.home,
														Login.login,
														ShowMyClub.showMyClub,
														RemoveContract.removeContract,
														ContractList.contractList,
														ContractDetails.contractDetails,
														ObtainContract.obtainContract
	)

	setUp(
		scn.inject(rampUsers(1000) during (100 seconds))
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
	)
}