package net.threescale.perftests.scenarios.simple

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder
import test.scala.net.threescale.perftests.scenarios.SharedSimulation

import scala.concurrent.duration._


abstract class SharedBasicSimulation extends SharedSimulation {


  /**
    * Performs setup of the simulation.
    */
  def doSetUp(): Unit = {
    val theScenarioBuilder: ScenarioBuilder = createScenarioBuilder()
    val theHttpProtocolBuilder: HttpProtocolBuilder = createHttpProtocolBuilder()

    // https://gatling.io/docs/2.3/general/simulation_setup/
    setUp(
      theScenarioBuilder.inject(
        nothingFor(2 seconds),
        atOnceUsers(10),
        rampUsers(finalUserCount) over userCountRampUpTime,
        constantUsersPerSec(5) during (15 seconds), // 4
        constantUsersPerSec(5) during (15 seconds) randomized,
        rampUsersPerSec(10) to 20 during (10 seconds),
        rampUsersPerSec(10) to 20 during (10 seconds) randomized,
        heavisideUsers(1000) over (20 seconds) // 10
      )
    ).protocols(theHttpProtocolBuilder)
  }

  doSetUp()

}
