package IRC

import com.trolltech.qt.gui.{QLineEdit, QTextEdit}

import scala.collection.mutable.ArrayBuffer

class Controller(val chatInput: QLineEdit, val chatTextArea: QTextEdit) {
  val servers: ArrayBuffer[Server] = new ArrayBuffer[Server]()

  {
    var freenodeServer = new Server()
    freenodeServer.connect()
    servers.append(freenodeServer)
  }

  def listenServers(): Unit = {
    for (server <- servers) {
      server.read()
    }
  }
}
