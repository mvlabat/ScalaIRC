package ChatView

import com.trolltech.qt.QSignalEmitter

object View extends QSignalEmitter {
  protected var initialized = false
  def Initialize(): Unit = {
    if (initialized) return
    initialized = true

    // Connect signals and slots.
    Widgets.chatInput.returnPressed.connect(this, "emitInputSent()")

    // Initialize the window.
    Widgets.window.setLayout(Widgets.mainLayout)
    Widgets.window.resize(800, 500)
    Widgets.window.show()
  }

  val inputSent: Signal1[String] = new Signal1[String]()
  protected def emitInputSent(): Unit = {
    inputSent.emit(Widgets.chatInput.text())
    Widgets.chatInput.setText("")
  }

  def log(log: String): Unit = {
    Widgets.chatTextAreaLog.append(log)
  }
}
