import mill._, scalalib._

import $ivy.`com.lihaoyi::mill-contrib-docker:$MILL_VERSION`
import contrib.docker.DockerModule

object app extends ScalaModule with DockerModule {
  object docker extends DockerConfig {
    override def tags = List("scala-sql-service")
    override def exposedPorts = Seq(8081)
  }

  def scalaVersion = "3.3.1"

  override def ivyDeps = Agg(ivy"com.lihaoyi::os-lib:0.9.2")
}
