# Shopping cart

Shopping cart is a simple application that allows you to add and remove products from the shopping cart and calculate the total price, similar to what you would see on any e-commerce website.
Pricing data for each product is retrieved via an HTTP call. You can find example pricing data for a set of sample products at the URI’s below:
- https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main/cheerios.json
- https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main/cornflakes.json
- https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main/frosties.json
- https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main/shreddies.json
- https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main/weetabix.json

Total price is calculated by:
- Calculating cart subtotal (sum of price for all items)
- Payable tax, charged at 12.5% on the subtotal 
- Total payable (subtotal + tax)

### Usage example 

```
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
