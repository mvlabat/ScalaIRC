package Server.UserInputController

class Command(var logMessage: String) {
  protected[UserInputController] def execute(): Unit = {
    ChatView.View.log(logMessage)
  }
}

trait GeneratesLogMessage {
  def generateLogMessage(): Unit
}

class SendMessageToChannelCommand(val message: String) extends Command("") with GeneratesLogMessage {
  def generateLogMessage(): Unit = {
    logMessage = message
  }

  override def execute(): Unit = {
    super.execute()
    Server.Model.activeConnection.send("PRIVMSG " + Server.Model.activeChannel.name + " :" + message)
  }
}
