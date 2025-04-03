class OutputAggregator(val outputList: List<Output>): Output {
    override fun send(recipient: Long, message: String) {
        outputList.forEach { it.send(recipient,message) }
    }
}