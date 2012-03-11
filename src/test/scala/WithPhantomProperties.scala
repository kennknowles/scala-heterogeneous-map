package scala.collection.hmap
package test

import org.scalacheck._
import org.scalacheck.Prop._
import org.scalacheck.Gen._
import org.scalacheck.Arbitrary._

object WithPhantomProperties extends Properties("WithPhantom") {

  /** Value1 and Value2 must be different types */
  def prop_typeMiss[T, Value1: Manifest, Value2: Manifest](x: T) : Prop =
    WithPhantom[T, Value1](x) != WithPhantom[T, Value2](x)
 
  property("typeMiss") = forAll { x:Int => prop_typeMiss[Int, Boolean, String](x) }
}
