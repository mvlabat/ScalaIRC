package UserInputController

class Command(var logMessage: String) {
  def execute(): Unit = {
    ChatView.Controller.log(logMessage)
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
    ServerController.Main.activeServer.send("PRIVMSG " + ServerController.Main.activeChannel.name + " :" + message)
  }
}
