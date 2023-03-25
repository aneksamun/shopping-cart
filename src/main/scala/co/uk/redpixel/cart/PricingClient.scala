package co.uk.redpixel.cart

import cats.Monad
import cats.data.EitherT
import cats.effect.Async
import cats.effect.kernel.Resource
import cats.syntax.all._
import co.uk.redpixel.cart.PricingClient.{PricingClientError, PricingData}
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import sttp.client3._
import sttp.client3.circe.asJson
import sttp.client3.httpclient.cats.HttpClientCatsBackend

trait PricingClient[F[_]] {
  def getPricingData(product: String): EitherT[F, PricingClientError, PricingData]
}

object PricingClient {
  def apply[F[_]](implicit ev: PricingClient[F]): PricingClient[F] = ev

  final case class PricingClientError(throwable: Throwable)
  final case class PricingData(title: String, price: BigDecimal)

  object PricingData {
    implicit val decoder: Decoder[PricingData] =
      deriveDecoder
  }

  def build[F[_] : Async : Monad]: Resource[F, PricingClient[F]] =
    HttpClientCatsBackend.resource[F]().map { usingBackend =>
      (product: String) =>
        EitherT {
          basicRequest
            .get(
              uri"https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main/${product.toLowerCase()}.json"
            )
            .response(asJson[PricingData].mapLeft(PricingClientError(_)))
            .send(usingBackend)
            .map(_.body)
        }
    }

}
