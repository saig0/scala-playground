package states

import states.StateMachine._
import org.scalatest.FlatSpec
import org.scalatest.Matchers

class StateMachineTest extends FlatSpec with Matchers {

  class S(name: String) extends State {
    def doWork = println(s"in state '$name'")
  }

  val s1 = new S("s1")
  val s2 = new S("s2")
  val s3 = new S("s3")

  def stateMachine = new StateMachine(
      initialState  = s1,
      transitions   = s1 --> s2,
                      s2 --> s1,
                      s2 --> s3
  )

  "A state machine" should "be in initial state" in {

      val sm = stateMachine
      sm.getCurrentState shouldBe s1
  }

  it should "take transition to next state" in {

      val sm = stateMachine
      val newState = sm.takeTransitionTo(s2)

      newState            shouldBe s2
      sm.getCurrentState  shouldBe s2
  }

  it should "take transition to end state " in {

      val sm = stateMachine
      sm.takeTransitionTo(s2)
      sm.takeTransitionTo(s3)

      sm.getCurrentState  shouldBe s3
  }

  it should "take transition to previous state " in {

      val sm = stateMachine
      sm.takeTransitionTo(s2)
      sm.takeTransitionTo(s1)

      sm.getCurrentState  shouldBe s1
  }

  it should "reset current state" in {

      val sm = stateMachine
      sm.takeTransitionTo(s2)

      sm.reset            shouldBe s1
      sm.getCurrentState  shouldBe s1
  }

  it should "throw exception if transition not exists" in {

    val sm = stateMachine

    an [NoSuchTransitionExceptions] should be thrownBy sm.takeTransitionTo(s3)
  }

}
