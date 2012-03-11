package org.scalatools.hmap

import org.scalacheck._
import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._
import org.scalacheck.Pretty._

object ScalaCheckInstances {

  implicit def arbWithPhantom[T: Arbitrary, Phantom: Manifest] : Arbitrary[WithPhantom[T, Phantom]] = Arbitrary(for(v <- arbitrary[T]) yield WithPhantom[T, Phantom](v))

  def genHMap[Value1, Value2, TypedKey[_]](implicit arbV1: Arbitrary[Value1], arbV2: Arbitrary[Value2], arbK1: Arbitrary[TypedKey[Value1]], arbK2: Arbitrary[TypedKey[Value2]]) : Gen[HMap[TypedKey]] = {
    for {
      kv1List <- arbitrary[List[(TypedKey[Value1], Value1)]]
      kv2List <- arbitrary[List[(TypedKey[Value2], Value2)]]
    } yield {
      var hmap = HMap.empty[TypedKey]
      for ((k, v) <- kv1List) { hmap = hmap.put(k, v) }
      for ((k, v) <- kv2List) { hmap = hmap.put(k, v) }
      hmap
    }
  }

  // For arbitrary, I just choose Int and String as the two phantom types and TInt for the key
  implicit def arbHMap[TypedKey[_]](implicit arbKInt: Arbitrary[TypedKey[Int]], arbKString: Arbitrary[TypedKey[String]]) = Arbitrary(genHMap[Int, String, TypedKey])
}

