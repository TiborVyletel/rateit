package rateit.frontend.html

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js.JSApp

/**
  * Created by Tibor Vyletel on 2017-04-03.
  */
object Client extends JSApp {

  val div = dom.document.getElementById("contents").asInstanceOf[HTMLElement]

  def main() = {
    println("Hello world!")
    div.textContent = "~~~Here I am !~~~"

    val p = dom.document.createElement("p")
    p.innerHTML = "Next child!"

    div.appendChild(p)
  }
}
