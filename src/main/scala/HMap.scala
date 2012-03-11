package org.scalatools.hmap

trait HMap[TypedKey[_]] { self =>
  def get[T](key: TypedKey[T]) : Option[T]
  def put[T](key: TypedKey[T], value: T) : HMap[TypedKey]
}

object HMap {
  private class WrappedMap[TypedKey[_]](m: Map[TypedKey[_], AnyRef]) extends HMap[TypedKey] {
    def get[T](key: TypedKey[T]) = m.get(key).asInstanceOf[Option[T]]
    def put[T](key: TypedKey[T], value: T) = new WrappedMap(m + (key -> value.asInstanceOf[AnyRef]))
  }

  def empty[TypedKey[_]] : HMap[TypedKey] = new WrappedMap[TypedKey](Map())
}
