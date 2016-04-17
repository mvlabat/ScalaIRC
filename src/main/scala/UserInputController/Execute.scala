package UserInputController

object Execute {
  def apply(input: String): Unit = {
    val command = Analyze(input)
    command.execute()
  }
}
