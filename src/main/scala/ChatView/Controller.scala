package ChatView

import com.trolltech.qt.QSignalEmitter
import com.trolltech.qt.gui._

object Controller extends QSignalEmitter {
  val window = new QWidget()
  val mainLayout = new QHBoxLayout()
  val channelsTree = new QTreeWidget()
  val chatLayout = new QVBoxLayout()
  val chatTextAreas = new QHBoxLayout()
  val chatTextAreaMessages = new QTextEdit()
  val chatTextAreaLog = new QTextEdit()
  val chatInput = new QLineEdit()

  // Setting up layouts.
  mainLayout.addLayout(chatLayout)
  chatLayout.addLayout(chatTextAreas)
  chatTextAreas.addWidget(chatTextAreaMessages)
  chatTextAreas.addWidget(chatTextAreaLog)
  chatLayout.addWidget(chatInput)

  // Setting up widgets.
  channelsTree.setColumnCount(1)
  chatTextAreaMessages.setReadOnly(true)
  chatTextAreaLog.setReadOnly(true)

  protected var initialized = false
  def Initialize(): Unit = {
    if (initialized) return
    initialized = true

    // Connect signals and slots.
    chatInput.returnPressed.connect(this, "emitInputSent()")

    // Initialize the window.
    window.setLayout(mainLayout)
    window.resize(800, 500)
    window.show()
  }

  val inputSent: Signal1[String] = new Signal1[String]()
  protected def emitInputSent() = {
    inputSent.emit(chatInput.text())
    chatInput.setText("")
  }

  def log(log: String): Unit = {
    chatTextAreaLog.append(log)
  }
}
