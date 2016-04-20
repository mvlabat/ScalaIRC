package Server

import java.io.{BufferedReader, BufferedWriter, InputStreamReader, OutputStreamWriter}
import java.net.Socket

import scala.collection.mutable

object Model {
  protected[Server] val connections = new scala.collection.mutable.HashMap[Identity, Connection]()

  def addConnection(host: String = "irc.freenode.net", port: Int = 6667, nickname: String = "ScalaIRC"): Connection = {
    addConnection(new Identity(host, port, nickname))
  }

  def addConnection(serverIdentity: Identity): Connection = {
    val connection = new Connection(serverIdentity)
    connections.put(serverIdentity, connection)
    connection
  }

  def connect(serverIdentity: Identity): Unit = {
    val server = connections.get(serverIdentity).orNull
    if (server != null) {
      server.connect()
    }
  }

  protected var _activeConnection: Connection = _
  protected def activeConnection_=(connection: Connection) = _activeConnection = connection
  def activeConnection = _activeConnection

  protected var _activeChannel: Channel = _
  protected def activeChannel_=(channel: Channel) = _activeChannel = channel
  def activeChannel = _activeChannel

  def switchActiveChannel(identity: Identity, channelName: String): Unit = {
    val server = connections.get(identity).orNull
    if (server != null) {
      val channel = server.getChannel(channelName).orNull
      if (channel != null) {
        activeConnection = server
        activeChannel = channel
      }
    }
  }
}

protected case class Identity(host: String = "irc.freenode.net", port: Int = 6667, nickname: String = "ScalaIRC")

protected class Connection(private var identity: Identity) {
  private val channels: mutable.HashMap[String, Channel] = new mutable.HashMap[String, Channel]()
  private var socket: Socket = _
  private var reader: BufferedReader = _
  private var writer: BufferedWriter = _
  private var isConnected: Boolean = false

  def setServerIdentity(identity: Identity): Unit = {
    this.identity = identity
  }

  protected[Server] def send(command: String): Unit = {
    if (isConnected) {
      writer.write(command + "\r\n")
      writer.flush()
    }
  }

  protected[Server] def write(command: String): Unit = if (isConnected) writer.write(command + "\r\n")

  protected[Server] def connect(): Unit = {
    try {
      socket = new Socket(identity.host, identity.port)
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
      writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream))

      // Login.
      isConnected = true
      write("NICK " + identity.nickname)
      write("USER " + identity.nickname + " 0 * :mvlabat's IRC client")
      writer.flush()

      for ((key, channel) <- channels) {
        write("JOIN " + channel.name)
      }

    }
    catch {
      case e: Exception =>
        isConnected = false
        println("something's went wrong")
    }
  }

  def addChannel(name: String): Unit = channels.put(name, new Channel(name))

  // TODO: will not be needed as public.
  def addChannel(channel: Channel): Unit = channels.put(channel.name, channel)

  def getChannel(name: String): Option[Channel] = channels.get(name)

  def disconnect(): Unit = {
    socket.close()
  }

  def read(): String = if (isConnected) reader.readLine() else ""
}

protected class Channel(val name: String) {
  val savedHtml: StringBuilder = new StringBuilder()
}
