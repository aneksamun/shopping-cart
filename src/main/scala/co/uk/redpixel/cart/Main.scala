package co.uk.redpixel.cart

import cats.Monad
import cats.data.OptionT
import cats.effect.std.Console
import cats.effect.{IO, IOApp}
import cats.implicits._
import co.uk.redpixel.cart

object Main extends IOApp.Simple {
  def run: IO[Unit] =
    PricingClient.build[IO].use { implicit client =>
      doShopping[IO]()
    }

    def doShopping[F[_] : Console : PricingClient : Monad](
      cart: ShoppingCart = ShoppingCart.empty
    ): F[Unit] =
      for {
        product <- enterProduct
        qty     <- enterQuantity
        _       <- Console[F].println("Do you wish to continue (y/n)?")
        answer  <- Console[F].readLine
        updated =  cart.add(product, qty)
        _       <- if (answer == "y")
                     doShopping(updated)
                   else print(updated)
      } yield ()

  def enterProduct[F[_] : Console : PricingClient : Monad]: F[Product] =
    Console[F].println("Enter product:") *>
      Console[F].readLine.flatMap { product =>
        PricingClient[F].getPricingData(product)
          .foldF(
            error =>
              Console[F].println(s"Failed retrieved pricing: ${error.throwable.getMessage}") *>
                enterProduct,
            cart.Product(_).pure[F]
          )
      }

  def enterQuantity[F[_] : Console : Monad]: F[Quantity] =
    Console[F].println("Enter quantity:") *>
      OptionT(Console[F].readLine.map(_.toIntOption))
        .foldF(
          Console[F].println("Invalid quantity. Please try again.") *>
            enterQuantity
        )(_.pure[F])

  def print[F[_] : Console : Monad](cart: ShoppingCart): F[Unit] =
    Console[F].println(cart) *>
      Console[F].println(s"Subtotal = ${cart.subtotal.show}") *>
      Console[F].println(s"Tax = ${cart.payableTax.show}") *>
      Console[F].println(s"Total = ${cart.total.show}")
}
