package support

import java.io.IOException
import java.net.ServerSocket

import scala.util.Try

object PortFinder {

  def findFreePort(): Int = {
    val socket: ServerSocket = new ServerSocket(0)
    var port = -1

    try {
      socket.setReuseAddress(true)
      port = socket.getLocalPort

      Try(socket.close())
    } catch{
      case e: IOException =>
    } finally {
      if (socket != null) {
        Try(socket.close())
      }
    }

    if (port == -1)
      throw new IllegalStateException("Could not find a free TCP/IP port")

    port
  }

}
