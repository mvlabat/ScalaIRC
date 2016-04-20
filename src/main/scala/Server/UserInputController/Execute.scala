package Server.UserInputController

object Execute {
  def getCommand(input: String): Command = {
    Analyze(input)
  }

  def apply(input: String): Unit = {
    val command = getCommand(input)
    command.execute()
  }
}
