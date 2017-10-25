package applications

import _root_.controllers._
import com.softwaremill.macwire._
import org.webjars.play.{RequireJS, WebJarComponents}
import play.api._
import play.api.cache.ehcache.EhCacheComponents
import play.api.i18n.I18nComponents
import play.api.inject.{NewInstanceInjector, SimpleInjector}
import play.api.mvc.BodyParsers
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import router.Routes
import views.html._

class MyComponents(context: ApplicationLoader.Context)
  extends BuiltInComponentsFromContext(context) with I18nComponents with AssetsComponents with WebJarComponents
    with HttpFiltersComponents with EhCacheComponents {

  override lazy val injector: SimpleInjector = new SimpleInjector(NewInstanceInjector) +
    router +
    cookieSigner +
    csrfTokenSigner +
    httpConfiguration +
    tempFileCreator +
    messagesApi

  val bodyParsers: BodyParsers.Default = new BodyParsers.Default(parse)
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  val prefix: String = "/"
  val main: main = wire[main]
  val index: index = wire[index]

  lazy val router: Router = new Routes(
    httpErrorHandler,
    new HomeController(defaultCacheApi, index, controllerComponents),
    assets,
    new webjars.Routes(httpErrorHandler, new RequireJS(webJarsUtil), webJarAssets, prefix),
    prefix
  )
}
