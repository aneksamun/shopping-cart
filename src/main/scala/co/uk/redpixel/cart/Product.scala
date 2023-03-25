package co.uk.redpixel.cart

import cats.Show
import cats.syntax.show._
import co.uk.redpixel.cart.PricingClient.PricingData

final case class Product(name: String, price: Price)

object Product {
  implicit val show: Show[Product] =
    Show.show(product => s"${product.name} @ ${product.price.show}")

  def apply(pricing: PricingData): Product =
    Product(name = pricing.title, price = Price.from(pricing.price))
}
