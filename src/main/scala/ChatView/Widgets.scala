package ChatView

import com.trolltech.qt.gui._

protected object Widgets {
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
}
