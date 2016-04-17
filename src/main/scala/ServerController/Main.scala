package ServerController

import java.net.SocketException

import com.trolltech.qt.QSignalEmitter

import scala.collection.mutable.ArrayBuffer

object Main extends QSignalEmitter {
  val servers: ArrayBuffer[Server] = new ArrayBuffer[Server]()

  protected var _activeServer: Server = _
  protected def activeServer_=(server: Server) = _activeServer = server
  def activeServer = _activeServer

  protected var _activeChannel: Channel = _
  protected def activeChannel_=(channel: Channel) = _activeChannel = channel
  def activeChannel = _activeChannel

  protected var initialized = false
  def Initialize(): Unit = {
    if (initialized) return
    initialized = true

    val freenodeServer = new Server()
    val testChannel = new Channel("#botwar")
    servers.append(freenodeServer)
    freenodeServer.addChannel(testChannel)
    freenodeServer.connect()

    activeServer = freenodeServer
    activeChannel = testChannel
    isListening = true
  }

  def destroy(): Unit = {
    isRunning = false
    for (server <- servers) server.disconnect()
  }

  val messageReceived: Signal1[String] = new Signal1[String]()
  protected var isRunning = false
  protected var isListening = false
  def listenServers(): Unit = {
    if (isRunning) return
    isRunning = true
    var buffer: String = ""
    while (isRunning) {
      if (isListening) {
        for (server <- servers) {
          try {
            buffer = server.read()
          }
          catch {
            case e: SocketException =>
            case e: Exception =>
              throw e
          }
          println(buffer)
          messageReceived.emit(buffer)
        }
      }
    }
  }

}
