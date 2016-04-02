package IRC

import java.io.{BufferedReader, BufferedWriter, InputStreamReader, OutputStreamWriter}
import java.net.{Socket, SocketAddress}

import scala.collection.mutable.ArrayBuffer

class Server(
              private var host: String = "irc.freenode.net",
              private var port: Int = 6667,
              private var nickname: String = "ScalaIRC"
            ) {
  private var channels: ArrayBuffer[Channel] = new ArrayBuffer[Channel]()
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

  def connect(): Unit = {
    try {
      socket = new Socket(host, port)
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
      writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream))

      // Login.
      writer.write("NICK " + nickname + "\r\n")

      for (channel <- channels) {

      }

      isConnected = true
    }
    catch {
      case e: Exception =>
        isConnected = false
        println("something's went wrong")
    }
  }

  def read() = if (isConnected) reader.readLine()
}
