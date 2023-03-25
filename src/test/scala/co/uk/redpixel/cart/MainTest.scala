package co.uk.redpixel.cart

import cats.effect.IO
import co.uk.redpixel.cart.PricingClient.PricingData
import co.uk.redpixel.cart.stub.{ConsoleStub, PricingClientStub}
import munit.CatsEffectSuite

class MainTest extends CatsEffectSuite {

  test("enter product") {
    implicit val console = ConsoleStub.read[IO]("Cheerios")
    implicit val client  = PricingClientStub.build[IO](
      _.withSuccessfulResponse(PricingData("Cheerios", 8.43))
    )
    Main.enterProduct.assertEquals(Product("Cheerios", Price(8.43)))
  }

  test("enter quantity") {
    implicit val console = ConsoleStub.read[IO]("3")
    Main.enterQuantity.assertEquals(3)
  }
}
