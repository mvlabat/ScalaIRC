package UserInputController

protected object Analyze {
  def apply(input: String): Command = {
    val command = new SendMessageToChannelCommand(input)
    command
  }
}
