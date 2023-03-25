package co.uk.redpixel.cart.data

import co.uk.redpixel.cart.{Price, Product}
import org.scalacheck.{Arbitrary, Gen}

trait ArbitraryData {

  implicit val arbitraryPrice: Arbitrary[Price] = Arbitrary {
    Gen.posNum[BigDecimal].map(Price.from)
  }

  implicit val arbitraryProduct: Arbitrary[Product] = Arbitrary {
    for {
      name  <- Gen.alphaStr
      price <- arbitraryPrice.arbitrary
    } yield Product(name, price)
  }

}

object ArbitraryData extends ArbitraryData
