import Services.{GiftService, endpoint}
import com.twitter.util.Await
import com.twitter.finagle.Http
/**
 * A tiny Finch application that serves a single endpoint `GET /hello` that returns
 * HTTP 200 response saying "Hello, World!"
 *
 * Use the following sbt command to run the application.
 *
 * {{{
 *   $ sbt run Main
 * }}}
 *
 * Use the following HTTPie commands to test endpoints.
 *
 * {{{
 *   $ http GET :8080/hello
 * }}}
 */
object Main extends App {

  Await.ready(Http.serve(":8080", endpoint.getService))
}
