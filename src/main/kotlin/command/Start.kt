package command

import Output
import com.github.kotlintelegrambot.dispatcher.handlers.CommandHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.handlers.HandleCommand

class Start(val sender: Output) : HandleCommand {
    override suspend fun invoke(p1: CommandHandlerEnvironment) {
        val chatId = p1.message.chat.id
        val message = "Привет! Я бот-кормилец 🐶. Доступные команды:\n" +
                    "/checkenddate — когда закончится корм\n" +
                    "/checktoday - есть ли у собаки корм на сегодня"
        sender.send(chatId, message)
    }
}