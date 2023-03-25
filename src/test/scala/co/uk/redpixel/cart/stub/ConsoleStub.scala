package co.uk.redpixel.cart.stub

import cats.effect.std.Console
import cats.{Applicative, Show}

import java.nio.charset.Charset

class ConsoleStub[F[_]](
  readLineWithCharsetResponse: F[String],
  printResponse: F[Unit],
  printlnResponse: F[Unit],
  errorResponse: F[Unit],
  errorlnResponse: F[Unit]
) extends Console[F] {

  def readLineWithCharset(charset: Charset): F[String] =
    readLineWithCharsetResponse

  def print[A](a: A)(implicit S: Show[A]): F[Unit] =
    printResponse

  def println[A](a: A)(implicit S: Show[A]): F[Unit] =
    printlnResponse

  def error[A](a: A)(implicit S: Show[A]): F[Unit] =
    errorResponse

  def errorln[A](a: A)(implicit S: Show[A]): F[Unit] =
    errorlnResponse

}

object ConsoleStub {

  def read[F[_]](
    readLineWithCharsetResponse: String,
  )(implicit F: Applicative[F]): ConsoleStub[F] = new ConsoleStub[F](
    readLineWithCharsetResponse = F.pure(readLineWithCharsetResponse),
    printResponse = F.unit,
    printlnResponse = F.unit,
    errorResponse = F.unit,
    errorlnResponse = F.unit
  )
}
