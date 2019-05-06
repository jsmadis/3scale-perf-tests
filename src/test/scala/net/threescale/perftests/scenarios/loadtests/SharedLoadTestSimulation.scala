package test.scala.net.threescale.perftests.scenarios.loadtests

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder
import test.scala.net.threescale.perftests.scenarios.SharedSimulation

import scala.concurrent.duration._
/**
  *
  * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
  */
abstract class SharedLoadTestSimulation extends SharedSimulation {

  /* Constant users per second */
  lazy val constantUsersPerSecond = 100

  /**
    * Performs setup of the simulation.
    */
  def doSetUp(): Unit = {
    val theScenarioBuilder: ScenarioBuilder = createScenarioBuilder()
    val theHttpProtocolBuilder: HttpProtocolBuilder = createHttpProtocolBuilder()

    setUp(
      theScenarioBuilder.inject(
        constantUsersPerSec(constantUsersPerSecond) during(30 seconds)
      )
    ).protocols(theHttpProtocolBuilder)
  }

  doSetUp()
}
