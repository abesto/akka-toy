package net.abesto.akkatoy

// standard library
import scala.util.Success
// scalatest
import org.scalatest._
// akka
import akka.actor.ActorSystem
import akka.pattern.ask
// akka test helpers
import akka.testkit.{DefaultTimeout, TestActorRef, TestKit}
// system under test
import AddActor.{GetOffset, SetOffset, Offset}



class AddActorSpec 
  extends TestKit(ActorSystem("AddActorSpec")) with DefaultTimeout
  with WordSpecLike with Matchers with BeforeAndAfterAll with BeforeAndAfterEach {

  override def afterAll() {
    shutdown()
  }

  type AddRef = TestActorRef[AddActor]
  def AddRef = TestActorRef[AddActor]

  "A fresh AddActor" should { implicit val addRef = AddRef
    "add up 10 + 15 to 25" in { (10, 15) produces 25 }
    "have an offset of 0" in { GetOffset produces Offset(0) }
  }
  "An AddActor" when { implicit val addRef = AddRef
    "it received SetOffset(3)" should { addRef ! SetOffset(3) 
      "add up 10 + 15 to 28" in { (10, 15) produces 28 }
      "have an offset of 3" in { GetOffset produces Offset(3) }
    }
  }

  class Message(message: Any)(implicit addRef: AddRef) {
    def produces(expected: Any) {
      val future = addRef ? message
      val Success(actual: Any) = future.value.get
      actual shouldEqual expected
    }
  }
  implicit def anythingCanBeAMessage(x: Any)(implicit addRef: AddRef): Message = new Message(x)
}
