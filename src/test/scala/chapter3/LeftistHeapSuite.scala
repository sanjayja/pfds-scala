package chapter3

import org.scalatest.FunSuite
import scala.annotation.tailrec
 
class LeftistHeapSuite extends FunSuite {
  
  test("empty heap is empty") {
    val h = LeftistHeap.empty[Int]
    assert(h.isEmpty)
  }
  
  test("empty heap has rank 0") {
    val h = LeftistHeap.empty[Int]
    expect(0) { h.rank }
  }
  
  test("findMin of empty heap fails") {
    val h = LeftistHeap.empty[Int]
    intercept[Exception] {
      h.findMin
    }
  }
  
  test("deleteMin of empty heap fails") {
    val h = LeftistHeap.empty[Int]
    intercept[Exception] {
      h.deleteMin
    }
  }
  
  test("singleton heap is non-empty") {
    val h = LeftistHeap(1)
    assert(!h.isEmpty)
  }
  
  test("singleton heap has rank 1") {
    val h = LeftistHeap(1)
    expect(1) { h.rank }
  }
  
  test("findMin in range 1 to 10") {
    val h = LeftistHeap(2,4,6,8,10,1,3,5,7,9)
    expect(1) { h.findMin }
  }
  
  def checkRank[A](h: LeftistHeap[A]): Boolean = {
    import LeftistHeap._
    @tailrec def rightDepthAcc[A](h: LeftistHeap[A], acc: Int): Int = h match {
      case Leaf => acc
      case Branch(_, _, _, r) => rightDepthAcc(r, acc+1)
    }
    h match {
      case Leaf => true
      case Branch(d, _, l, r) =>
        (d == rightDepthAcc(h, 0)) &&
        checkRank(l) &&
        checkRank(r)
    }
  }
  
  test("check rank of empty heap") {
    val h = LeftistHeap.empty[Int]
    assert(checkRank(h))
  }
  
  test("check rank of singleton heap") {
    val h = LeftistHeap(1)
    assert(checkRank(h))
  }
  
  test("check ranks of heap with 1 to 10") {
    val h = LeftistHeap(2,4,6,8,10,1,3,5,7,9)
    assert(checkRank(h))
  }
  
  def checkLeftist[A](h: LeftistHeap[A]): Boolean = {
    import LeftistHeap._
    h match {
      case Leaf => true
      case Branch(_, _, l, r) =>
        (l.rank >= r.rank) &&
        checkLeftist(l) &&
        checkLeftist(r)
    }
  }
  
  test("check leftist property with 1 to 10") {
    val h = LeftistHeap(2,4,6,8,10,1,3,5,7,9)
    assert(checkLeftist(h))
  }
}