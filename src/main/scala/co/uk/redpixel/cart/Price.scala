package co.uk.redpixel.cart

import cats.Show

import scala.math.BigDecimal.RoundingMode.HALF_UP
import scala.util.Try

final case class Price private(value: BigDecimal) extends AnyVal {
  def *(quantity: Quantity): Price = Price(value * quantity)
  def +(other: Price): Price = Price(value + other.value)
}

object Price {

  implicit val show: Show[Price] = Show.show(_.value.toString)

  implicit val numeric: Numeric[Price] =
    new Numeric[Price] {
      def plus(x: Price, y: Price): Price = Price(x.value + y.value)
      def minus(x: Price, y: Price): Price = Price(x.value - y.value)
      def times(x: Price, y: Price): Price = Price(x.value * y.value)
      def negate(price: Price): Price = Price(-price.value)
      def fromInt(n: Int): Price = Price(BigDecimal.valueOf(n.toLong))
      def parseString(str: String): Option[Price] = Try(BigDecimal(str)).toOption.map(Price.from)
      def toInt(price: Price): Int = price.value.toInt
      def toLong(price: Price): Long = price.value.toLong
      def toFloat(price: Price): Float = price.value.toFloat
      def toDouble(price: Price): Double = price.value.toDouble
      def compare(price: Price, y: Price): Int = price.value.compareTo(y.value)
    }

  def from(value: BigDecimal): Price =
    Price(value.setScale(2, HALF_UP))

}
