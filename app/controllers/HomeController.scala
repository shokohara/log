package controllers

import play.api.cache.AsyncCacheApi
import play.api.mvc._
import views.html.index

import scala.concurrent.ExecutionContext

class HomeController(defaultCacheApi: AsyncCacheApi, indexView: index, cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  var data: List[AnyContent] = List.empty[AnyContent]

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(indexView(data.map(_.toString)))
  }

  def indexPost() = Action { implicit request: Request[AnyContent] =>
    data = data :+ request.body
    Redirect(routes.HomeController.index())
  }

  def clear = Action { implicit request: Request[AnyContent] =>
    data = List.empty[AnyContent]
    Redirect(routes.HomeController.index())
  }
}
