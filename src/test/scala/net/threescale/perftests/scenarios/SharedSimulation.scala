package test.scala.net.threescale.perftests.scenarios


import net.threescale.perftests.common.SimulationBase
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import net.threescale.perftests.entities.Api

import scala.concurrent.duration._
import scala.util.Random

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
abstract class SharedSimulation extends SimulationBase {

    /* Api instance */
    lazy val api: Api = null
    /* Base URL of requests sent in scenario 1. */
    lazy val scenario1BaseURL = ""
    /* Final number of users in the simulation. */
    lazy val finalUserCount = 10
    /* Time period after which the number of users is to reach the final number of users. */
    lazy val userCountRampUpTime: FiniteDuration = 20 seconds


    /*
     * Performs initialization of the simulation before it is executed.
     * Initialization must be performed this way in order to allow for subclasses to
     * modify instance variables and for those modifications to affect the resulting
     * simulation configuration.
     */

    /**
     * Creates the HTTP protocol builder used in the simulation.
     */
    def createHttpProtocolBuilder(): HttpProtocolBuilder = {
        val httpProtocolBuilder = http
                .baseURL(scenario1BaseURL)
                .acceptHeader("text/plain")
                .userAgentHeader("Gatling")
        httpProtocolBuilder
    }

    private val randomSubString: String = Random.alphanumeric.take(20).mkString


    /**
     * Creates the scenario builder used in the simulation.
     */
    def createScenarioBuilder(): ScenarioBuilder = {
        val stringFeeder = Iterator.continually(Map("param" -> randomSubString))
        val scenarioBuilder = scenario("Scenario: " + getClass.getSimpleName)
                .feed(stringFeeder)
                .exec(
                        http("GET request")
                                .get("/get")
                                .queryParam("param", "${param}")
                                .addApiAuthParams(api)
                )
                .exec(
                        http("POST request")
                                .post("/post")
                                .queryParam("param", "${param}")
                                .addApiAuthParams(api)
                )
                .exec(
                        http("PUT request")
                                .put("/put")
                                .queryParam("param", "${param}")
                                .addApiAuthParams(api)
                )
                .exec(
                        http("DELETE request")
                                .delete("/delete")
                                .queryParam("param", "${param}")
                                .addApiAuthParams(api)
                )
        scenarioBuilder
    }

}
