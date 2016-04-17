package ServerInputController

class Command(var logMessage: String) {
  def execute(): Unit = {
    if (!logMessage.isEmpty) ChatView.Controller.log(logMessage)
  }
}

trait GeneratesLogMessage {
  def generateLogMessage(): Unit
}
