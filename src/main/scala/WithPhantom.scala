package org.scalatools.hmap

import java.net.URI
                                                                                                                                                                                                                                          
case class WithPhantom[T, Phantom: Manifest](v: T) {
  private val m = implicitly[Manifest[Phantom]]

  override def equals(other: Any) = other.isInstanceOf[WithPhantom[T, Phantom]] && {
    val otherPh = other.asInstanceOf[WithPhantom[T, Phantom]]
    (otherPh.m.erasure == this.m.erasure) && (otherPh.v == this.v)
  }

  override def hashCode = (v, implicitly[Manifest[Phantom]].hashCode).hashCode

  override def toString = "WithPhantom[%s](%s)".format(implicitly[Manifest[Phantom]].erasure.getName, v)
}                                                                                                                                                                                                                                           

object WithPhantom {
  type TInt[T] = WithPhantom[Int, T]
  type TString[T] = WithPhantom[String, T]
  type TURI[T] = WithPhantom[URI, T]
}
