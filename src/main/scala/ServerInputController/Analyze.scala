package ServerInputController

protected object Analyze {
  def apply(input: String): Command = {
    val command = new Command(input)
    command
  }
}
