package curri

case class NotFoundException(val msg: String) extends RuntimeException(msg) {

}
