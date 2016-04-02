package main.scala

import IRC.Controller
import com.trolltech.qt.gui._

object init {
  def main(args: Array[String]): Unit = {
    QApplication.initialize(args)

    val window = new QWidget()
    val mainLayout = new QHBoxLayout()
    var channelsTree = new QTreeWidget()
    val chatLayout = new QVBoxLayout()
    val chatTextArea = new QTextEdit()
    val chatInput = new QLineEdit()

    // Setting up layouts.
    mainLayout.addLayout(chatLayout)
    chatLayout.addWidget(chatTextArea)
    chatLayout.addWidget(chatInput)

    // Setting up widgets.
    channelsTree.setColumnCount(1)
    chatTextArea.setReadOnly(true)

    // Initialize the controller.
    val controller = new Controller(chatInput, chatTextArea)

    // Initialize the window.
    window.setLayout(mainLayout)
    window.show()

    QApplication.execStatic()
  }
}
