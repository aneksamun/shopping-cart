package co.uk.redpixel.cart

import cats.implicits.toShow
import co.uk.redpixel.cart.data.ArbitraryData._
import munit.ScalaCheckSuite
import org.scalacheck.Prop.forAll

class ProductTest extends ScalaCheckSuite {

  property("show product") {
    forAll { (product: Product) =>
      assertEquals(product.show, s"${product.name} @ ${product.price.value}")
    }
  }
}
