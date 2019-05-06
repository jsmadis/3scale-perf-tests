package test.scala.net.threescale.perftests.scenarios.loadtests

import net.threescale.perftests.CollectionsFactory
import net.threescale.perftests.entities.Api

/**
  *
  * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
  */
class BasicLoadTestSimulation extends SharedLoadTestSimulation {
  override lazy  val constantUsersPerSecond = 150
  override lazy val api: Api = CollectionsFactory.api("user_key")
  override lazy val scenario1BaseURL: String = api.productionUrl
}
