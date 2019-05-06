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
  lazy val numberOfUsers = 100

  /**
    * Performs setup of the simulation.
    */
  def doSetUp(): Unit = {
    val theScenarioBuilder: ScenarioBuilder = createScenarioBuilder()
    val theHttpProtocolBuilder: HttpProtocolBuilder = createHttpProtocolBuilder()

    setUp(
      theScenarioBuilder.inject(
        constantUsersPerSec(numberOfUsers) during(1 minute)
      )
    ).protocols(theHttpProtocolBuilder).assertions(
        global.responseTime.mean.lt(250),
        global.responseTime.percentile3.lt(500),
        global.allRequests.count.gt(200)
      )
  }
  doSetUp()
}
