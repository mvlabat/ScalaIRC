package main.scala

import com.trolltech.qt.QSignalEmitter
import com.trolltech.qt.core.Qt.ConnectionType
import com.trolltech.qt.gui._

protected object MainController extends QSignalEmitter
{
  def Initialize() {
    ChatView.Controller.Initialize()
    ServerController.Main.Initialize()

    // Connecting Qt signals with slots.
    ServerController.Main.messageReceived.connect(ServerInputController.Execute, "apply(String)", ConnectionType.QueuedConnection)
    ChatView.Controller.inputSent.connect(UserInputController.Execute, "apply(String)")

    readerThread.start()
  }

  // Define worker thread.
  private var isRunning = true

  def destroy(): Unit = {
    ServerController.Main.destroy()
    readerThread.join()
  }

  private val readerThread = new Thread(new Runnable {
    override def run(): Unit = ServerController.Main.listenServers()
  })

  def main(args: Array[String]): Unit = {
    val app = new QApplication(args)

    // Initialize the controller.
    Initialize()

    app.exec()
    destroy()
  }
}
