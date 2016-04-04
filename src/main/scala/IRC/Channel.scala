package IRC

class Channel(val name: String) {
  var isActive: Boolean = false
  val savedHtml: StringBuilder = new StringBuilder()
}