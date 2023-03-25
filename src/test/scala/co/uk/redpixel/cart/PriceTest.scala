package co.uk.redpixel.cart

import co.uk.redpixel.cart.data.ArbitraryData._
import munit.ScalaCheckSuite
import org.scalacheck.Prop._

class PriceTest extends ScalaCheckSuite {

  property("price must be rounded up to 2 decimal places up") {
    forAll { (price: Price) =>
      val newPrice = Price.from(price.value + 0.005)
      assertEquals(newPrice, Price(value = price.value + 0.01))
    }
  }

  property("price must be rounded up to 2 decimal places down") {
    forAll { (price: Price) =>
      val newPrice = Price.from(price.value + 0.004)
      assertEquals(newPrice, price)
    }
  }

  property("product of price and quantity") {
    forAll { (price: Price, quantity: Quantity) =>
      assertEquals(price * quantity, Price.from(price.value * quantity))
    }
  }

  property("sum of two prices") {
    forAll { (price1: Price, price2: Price) =>
      assertEquals(price1 + price2, Price.from(price1.value + price2.value))
    }
  }

}
