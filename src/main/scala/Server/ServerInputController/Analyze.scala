package Server.ServerInputController

import Server.Connection

protected object Analyze {
  def extractServerFromString(input: String): String = {
    val firstColon = input.indexOf(':')
    input.substring(firstColon + 1)
  }

  val checks: Array[(Connection, String) => Option[Command]] = Array(
    /**
      * Check for PongCommand.
      */
    (connection: Connection, input: String) => {
      if (input.indexOf("PING") == 0) {
        val command = new PongCommand(connection, input)
        command.server = extractServerFromString(input)
        if (!command.server.contains(' '))
          Some(command)
        else
          None
      }
      else
        None
    }
  )

  def apply(connection: Connection, input: String): Command = {
    for (check <- checks) {
      val command: Command = check(connection, input).orNull
      if (command != null)
        return command
    }
    new Command(connection, input)
  }
}
