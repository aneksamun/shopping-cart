package co.uk.redpixel.cart

import cats.Show
import cats.syntax.show._

final case class ShoppingCart private(productUnits: ProductUnits) {

  def add(product: Product, quantity: Quantity): ShoppingCart =
    copy(productUnits.updated(product, get(product) + quantity))

  def get(product: Product): Quantity =
    productUnits.getOrElse(product, 0)

  def remove(product: Product, quantity: Quantity): ShoppingCart =
    productUnits
      .get(product)
      .map(_ - quantity)
      .fold(this) { remaining =>
        copy(
          if (remaining > 0)
            productUnits.updated(product, remaining)
          else productUnits - product
        )
      }

  def subtotal: Price =
    productUnits.map { case (product, quantity) =>
      product.price * quantity
    }.sum

  def payableTax = Price.from((subtotal.value * 12.5) / 100)

  def total: Price = subtotal + payableTax
}

object ShoppingCart {

  implicit val show: Show[ShoppingCart] = Show.show(
    _.productUnits.map {
      case (product, quantity) => s"Add $quantity x ${product.show} each"
    }.mkString(System.lineSeparator)
  )

  val empty = new ShoppingCart(productUnits = Map.empty)

  def apply(productUnits: (Product, Quantity)*): ShoppingCart =
    productUnits.foldLeft(empty) {
      case (cart, (product, quantity)) => cart.add(product, quantity)
    }

}
