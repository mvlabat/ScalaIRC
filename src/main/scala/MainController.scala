package main.scala

import Server.ServerInputController.Execute
import Server.UserInputController
import com.trolltech.qt.QSignalEmitter
import com.trolltech.qt.core.Qt.ConnectionType
import com.trolltech.qt.gui._

protected object MainController extends QSignalEmitter
{
  def Initialize() {
    ChatView.View.Initialize()
    Server.Controller.Initialize()

    // Connecting Qt signals with slots.
    Server.Controller.messageReceived.connect(Execute, "apply(Connection, String)", ConnectionType.QueuedConnection)
    ChatView.View.inputSent.connect(UserInputController.Execute, "apply(String)")

    readerThread.start()
  }

  // Define worker thread.
  private var isRunning = true

  def destroy(): Unit = {
    Server.Controller.destroy()
    readerThread.join()
  }

  private val readerThread = new Thread(new Runnable {
    override def run(): Unit = Server.Controller.listenServers()
  })

  def main(args: Array[String]): Unit = {
    val app = new QApplication(args)

    // Initialize the controller.
    Initialize()

    app.exec()
    destroy()
  }
}
