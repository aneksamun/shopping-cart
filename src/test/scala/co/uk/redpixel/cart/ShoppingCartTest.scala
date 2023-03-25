package co.uk.redpixel.cart

import cats.implicits.toShow
import co.uk.redpixel.cart
import co.uk.redpixel.cart.data.ArbitraryData._
import munit.ScalaCheckSuite
import org.scalacheck.Gen
import org.scalacheck.Prop.forAll

class ShoppingCartTest extends ScalaCheckSuite {

  property("get product from empty cart") {
    forAll { (product: cart.Product) =>
      assertEquals(ShoppingCart.empty.get(product), expected = 0)
    }
  }

  property("add product to empty cart") {
    forAll { (product: cart.Product, quantity: Quantity) =>
      assertEquals(ShoppingCart.empty.add(product, quantity).get(product), expected = quantity)
    }
  }

  property("update product in cart") {
    forAll { (product: cart.Product, quantity1: Quantity, quantity2: Quantity) =>
      assertEquals(
        ShoppingCart.empty
          .add(product, quantity1)
          .add(product, quantity2)
          .get(product), expected = quantity1 + quantity2
      )
    }
  }

  test("subtotal of empty cart") {
    assertEquals(ShoppingCart.empty.subtotal, expected = Price(0))
  }

  property("subtotal of cart with multiple products") {
    forAll(Gen.posNum[Quantity], arbitraryProduct.arbitrary) { (quantity, product) =>
      val cart = ShoppingCart.empty.add(product, quantity)
      val subtotal = (1 to quantity).map(_ => product.price).sum
      assertEquals(cart.subtotal, expected = subtotal)
    }
  }

  test("payable tax of empty cart") {
    assertEquals(ShoppingCart.empty.payableTax, expected = Price(0))
  }

  test("payable tax of cart with multiple products") {
    val cart = ShoppingCart(
      Product("Apple", Price(1)) -> 1,
      Product("Orange", Price(2)) -> 2
    )
    assertEquals(cart.payableTax, expected = Price(0.63))
  }

  test("total of empty cart") {
    assertEquals(ShoppingCart.empty.total, expected = Price(0))
  }

  test("total of cart with multiple products") {
    val cart = ShoppingCart(
      Product("Apple", Price(1)) -> 1,
      Product("Orange", Price(2)) -> 2
    )
    assertEquals(cart.total, expected = Price(5.63))
  }

  test("show cart") {
    val cart = ShoppingCart(
      Product("Apple", Price(1)) -> 1,
      Product("Orange", Price(2)) -> 2
    )
    assertEquals(cart.show, expected = """Add 1 x Apple @ 1 each
                                         |Add 2 x Orange @ 2 each""".stripMargin)
  }
}
