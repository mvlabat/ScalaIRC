package IRC

import java.net.SocketException

import com.trolltech.qt.core.Qt.ConnectionType
import com.trolltech.qt.QSignalEmitter
import com.trolltech.qt.gui.{QLineEdit, QTextEdit}

import scala.collection.mutable.ArrayBuffer

class Controller(val chatInput: QLineEdit, val chatTextArea: QTextEdit) extends QSignalEmitter {
  val servers: ArrayBuffer[Server] = new ArrayBuffer[Server]()
  val view: View = new View(chatTextArea)
  private var activeServer: Server = _
  private var activeChannel: Channel = _

  // Define worker thread.
  private var isRunning = true

  def destroy(): Unit = {
    isRunning = false
    for (server <- servers) server.disconnect()
    readerThread.join()
  }

  private val readerThread = new Thread(new Runnable {
    override def run(): Unit = listenServers()
  })

  private def listenServers(): Unit = {
    var buffer: String = ""
    while (isRunning) {
      for (server <- servers) {
        try {
          buffer = server.read()
        }
        catch {
          case e: SocketException =>
          case e: Exception =>
            throw e
        }
        textChanged.emit(buffer)
      }
    }
  }

  // Define custom Qt signal.
  val textChanged: Signal1[String] = new Signal1[String]()

  def updateChatTextArea(buffer: String): Unit = {
    view.chatTextArea.append(buffer)
  }

  def sendMessage(): Unit = {
    activeServer.sendMessage(activeChannel.name, chatInput.text())
    chatInput.setText("")
  }

  // Constructor
  {
    val freenodeServer = new Server()
    val qtjambiChannel = new Channel("#qtjambi")
    servers.append(freenodeServer)
    freenodeServer.addChannel(qtjambiChannel)
    freenodeServer.connect()

    activeServer = freenodeServer
    activeChannel = qtjambiChannel
    // Connecting Qt signals with slots.
    textChanged.connect(this, "updateChatTextArea(String)", ConnectionType.QueuedConnection)
    chatInput.returnPressed.connect(this, "sendMessage()")

    readerThread.start()
  }
}
