package ServerInputControllerTest

import collection.mutable.Stack
import org.scalatest._
import Server.ServerInputController._

class ExampleSpec extends FlatSpec with Matchers {
  it should "return right PongCommand" in {
    val input = "PING :adams.freenode.net"
    val command = Execute.getCommand(null, input)

    command shouldBe a [PongCommand]
    command.server should equal("adams.freenode.net")
  }
}
