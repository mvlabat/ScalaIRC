package Server

import java.net.SocketException

import com.trolltech.qt.QSignalEmitter

object Controller extends QSignalEmitter {
  protected var initialized = false
  def Initialize(): Unit = {
    if (initialized) return
    initialized = true

    val freenodeServerIdentity = new Identity()
    val testChannel = new Channel("#botwar")

    val connection = Model.addConnection(freenodeServerIdentity)
    connection.addChannel(testChannel)
    connection.connect()

    isListening = true
  }

  def destroy(): Unit = {
    isRunning = false
    for ((_, server) <- Model.connections) server.disconnect()
  }

  val messageReceived = new Signal2[Connection, String]()
  protected var isRunning = false
  protected var isListening = false
  def listenServers(): Unit = {
    if (isRunning) return
    isRunning = true
    var buffer: String = ""
    while (isRunning) {
      if (isListening) {
        for ((_, connection: Connection) <- Model.connections) {
          try {
            buffer = connection.read()
          }
          catch {
            case e: SocketException =>
            case e: Exception =>
              throw e
          }
          println(buffer)
          messageReceived.emit(connection, buffer)
        }
      }
    }
  }

}
