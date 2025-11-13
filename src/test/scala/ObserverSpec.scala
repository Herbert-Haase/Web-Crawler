package de.htwg.webscraper.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.webscraper.controller.{Observable, Observer}

class ObserverSpec extends AnyWordSpec with Matchers {

  "An Observable" should {
    "add an observer" in {
      val observable = new Observable()
      val observer = new TestObserver()
      observable.subscribers should be(empty)
      observable.add(observer)
      observable.subscribers should contain(observer)
    }

    "remove an observer" in {
      val observable = new Observable()
      val observer1 = new TestObserver()
      val observer2 = new TestObserver()
      observable.add(observer1)
      observable.add(observer2)
      observable.subscribers should contain allOf (observer1, observer2)
      
      observable.remove(observer1)
      observable.subscribers should not contain (observer1)
      observable.subscribers should contain(observer2)
    }

    "notify observers" in {
      val observable = new Observable()
      val observer1 = new TestObserver()
      val observer2 = new TestObserver()
      observable.add(observer1)
      observable.add(observer2)

      observer1.updated should be(false)
      observer2.updated should be(false)

      observable.notifyObservers()

      observer1.updated should be(true)
      observer2.updated should be(true)
    }
  }
}

// A helper class for testing
class TestObserver extends Observer {
  var updated = false
  override def update(): Unit = updated = true
}