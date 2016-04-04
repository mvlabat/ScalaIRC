package IRC

import java.io.{BufferedReader, BufferedWriter, InputStreamReader, OutputStreamWriter}
import java.net.{Socket, SocketAddress}

import scala.collection.mutable

class Server(
              private var host: String = "irc.freenode.net",
              private var port: Int = 6667,
              private var nickname: String = "ScalaIRC"
            ) {
  private val channels: mutable.HashMap[String, Channel] = new mutable.HashMap[String, Channel]()
  private var socket: Socket = _
  private var reader: BufferedReader = _
  private var writer: BufferedWriter = _
  private var isConnected: Boolean = false

  def setHost(host: String = "irc.freenode.net"): Unit = {
    this.host = host
  }

  def setPort(port: Int = 6667): Unit = {
    this.port = port
  }

  def setNickname(nickname: String): Unit = {
    this.nickname = nickname
  }

  def write(command: String): Unit = if (isConnected) writer.write(command + "\r\n")

  def sendMessage(target: String, message: String): Unit = {
    write("PRIVMSG " + target + " :" + message)
    writer.flush()
  }

  def connect(): Unit = {
    try {
      socket = new Socket(host, port)
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
      writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream))

      // Login.
      isConnected = true
      write("NICK " + nickname)
      write("USER " + nickname + " 0 * :mvlabat's IRC client")
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

  def addChannel(channel: Channel): Unit = channels.put(channel.name, channel)

  def getChannel(name: String): Channel = channels.getOrElse(name, null.asInstanceOf[Channel])

  def disconnect(): Unit = {
    socket.close()
  }

  def read(): String = if (isConnected) reader.readLine() else ""
}
