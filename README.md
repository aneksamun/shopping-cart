# Shopping cart

### Usage example 

```bash
Enter product:
cornflakes
Enter quantity:
2
Do you wish to continue (y/n)?
y
Enter product:
weetabix
Enter quantity:
1
Do you wish to continue (y/n)?
n
Add 2 x Corn Flakes @ 2.52 each
Add 1 x Weetabix @ 9.98 each
Subtotal = 15.02
Tax = 1.88
Total = 16.90
```

### Technology stack
- [scala 2.13.10](http://www.scala-lang.org/) as the main application programming language
- [cats-effect](https://github.com/typelevel/cats-effect) the Haskell IO monad for Scala
- [sttp](https://sttp.softwaremill.com/en/stable/) the Scala HTTP client
