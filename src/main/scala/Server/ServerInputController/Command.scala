package Server.ServerInputController

import Server.Connection

class Command(val connection: Connection, logMessage: String) {
  var server: String = _

  protected[ServerInputController] def execute(): Unit = {
    // TODO: multichannel support
    if (!logMessage.isEmpty) ChatView.View.log(logMessage)
  }
}

protected trait GeneratesLogMessage {
  def generateLogMessage(): Unit
}

class PongCommand(connection: Connection, logMessage: String) extends Command(connection, logMessage) {
  override protected[ServerInputController] def execute(): Unit = {
    connection.send("PONG " + server)
    ChatView.View.log("PONGED TO " + server)
  }
}
