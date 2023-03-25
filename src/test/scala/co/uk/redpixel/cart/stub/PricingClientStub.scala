package co.uk.redpixel.cart.stub

import cats.Applicative
import cats.data.EitherT
import co.uk.redpixel.cart.PricingClient
import PricingClient.{PricingClientError, PricingData}

class PricingClientStub[F[_] : Applicative](getPricingResponse: Either[PricingClientError, PricingData])
  extends PricingClient[F] {

  def getPricingData(product: String): EitherT[F, PricingClientError, PricingData] =
    EitherT.fromEither[F](getPricingResponse)
}

object PricingClientStub {

  def build[F[_] : Applicative](apply: Builder[F] => Builder[F]): PricingClientStub[F] =
    apply(new Builder[F]()).build

  final case class Builder[F[_] : Applicative](
    response: Either[PricingClientError, PricingData] =
      Left(PricingClientError(new UnsupportedOperationException("Unexpected to call")))
  ) {

    def withSuccessfulResponse(pricingData: PricingData): Builder[F] =
      copy(response = Right(pricingData))

    def withErrorResponse(throwable: Throwable): Builder[F] =
      copy(response = Left(PricingClientError(throwable)))

    def build: PricingClientStub[F] = new PricingClientStub[F](response)
  }
}
