package Server.ServerInputController

import Server.Connection

object Execute {
  def getCommand(connection: Connection, input: String): Command = {
    Analyze(connection, input)
  }

  def apply(connection: Connection, input: String): Unit = {
    val command = getCommand(connection, input)
    command.execute()
  }
}
