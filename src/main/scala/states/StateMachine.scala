package states

trait State {

  def doWork: Unit

  def onEnter: Unit = {}

  def onClose: Unit = {}

}

class NoSuchTransitionExceptions extends IllegalStateException

case class Transition(
  from: State,
  to:   State
)

object StateMachine {

  implicit class TransitionBuilder(from: State) {
    def -->(to: State) = Transition(from, to)
  }

}

class StateMachine(
  initialState: State,
  transitions:  Transition*
) {

  var currentState = initialState

  def takeTransitionTo(nextState: State): State = {
      transitions find ( t => t.from == currentState && t.to == nextState) match {
        case None     => throw new NoSuchTransitionExceptions
        case Some(s)  => {
          currentState.onClose
          currentState = nextState
          currentState.onEnter

          return currentState
        }
      }
  }

  def doWork: Unit = currentState.doWork

  def getCurrentState: State = currentState

  def reset: State = {
    currentState = initialState
    return currentState
  }

}
