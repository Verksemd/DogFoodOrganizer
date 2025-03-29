interface Output<T> {
    fun send(recipient: Any, message: String): T
}