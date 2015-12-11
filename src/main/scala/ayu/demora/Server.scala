package ayu.demora

import java.net.InetSocketAddress
import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.httpx.{Http, Request, Response, Status}
import com.twitter.util.Future._
import com.twitter.util._
import com.typesafe.config.ConfigFactory


object Server extends App {
  private val config = ConfigFactory.load()

  private val host = config.getString("host")
  private val port = config.getInt("port")

  private val address = new InetSocketAddress(host, port)

  private val requestProcessingTime = Duration.fromSeconds(1)

  private val service = new Service[Request, Response] {
    implicit val timer = new ResponseDelayTimer()

    def apply(req: Request): Future[Response] = req.uri match {
      case "/foo/bar" => sleep(requestProcessingTime) before value(Response(Status.Ok))
      case other      => value(Response(Status.NotFound))
    }
  }

  {
    val server = ServerBuilder()
      .codec(Http())
      .bindTo(address)
      .name("Demora")
      .build(service)

    Await.ready(server)
  }
}
