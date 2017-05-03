package rateit.backend.http

import scalatags.Text.all._

object Index {

  private val skeleton =
    html(
          head(
                link(
                      rel := "stylesheet",
                      href := "https://cdnjs.cloudflare.com/ajax/libs/pure/0.5.0/pure-min.css"
                    )
              ),
          body(
                div(id := "contents"),
                script(src := "html-ui-fastopt.js")
              )
        )


  def render(): String = skeleton.render
}
