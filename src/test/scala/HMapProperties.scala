package org.scalatools.hmap
package test

import org.scalacheck._
import org.scalacheck.Prop._
import org.scalacheck.Gen._
import org.scalacheck.Arbitrary._

import WithPhantom._

object HMapProperties extends Properties("HMap") {
  import ScalaCheckInstances._
  import WithPhantom._ // for TInt, etc
 
  def prop_empty[TypedKey[_], T](x: TypedKey[T]) : Prop =
    HMap.empty.get(x) ?= None
 
  def prop_hit[TypedKey[_], T](m: HMap[TypedKey], x: TypedKey[T], v: T) : Prop =
    m.put(x, v).get(x) ?= Some(v)
 
  def prop_miss[TypedKey[_], T](m: HMap[TypedKey], x: TypedKey[T],
                                y: TypedKey[T], v: T) : Prop =
      (x != y) ==> (m.put(y, v).get(x) ?= m.get(x))
     
  // When x == y but T != U
  def prop_typeMiss[TypedKey[_], T, U](m: HMap[TypedKey], x: TypedKey[T],
                                       y: TypedKey[U], v: U) : Prop =
    m.put(y, v).get(x) ?= m.get(x)
 
  property("empty") = forAll { x:TInt[Int] => prop_empty(x) }
 
  property("hit") = forAll { (m:HMap[TInt], x: TInt[Int], v: Int) => prop_hit(m, x, v) }
   
  property("miss") = forAll { (m:HMap[TInt], x: TInt[Int], y: TInt[Int], v: Int) =>
    prop_miss(m, x, y, v)
                           }
   
  property("typeMiss") = forAll { (m:HMap[TInt], x: Int, v: Boolean) =>
    prop_typeMiss(m,
                  WithPhantom[Int, Int](x).asInstanceOf[TInt[Int]],
                  WithPhantom[Int, Boolean](x).asInstanceOf[TInt[Boolean]],
                  v) }
}

