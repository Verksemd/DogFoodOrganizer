package command

import Notifier
import com.github.kotlintelegrambot.dispatcher.handlers.CommandHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.handlers.HandleCommand

class CheckEndDate(val notifier: Notifier): HandleCommand {
    override suspend fun invoke(p1: CommandHandlerEnvironment) {
        val chatId = p1.message.chat.id
        notifier.informAboutFinishDate(chatId)
    }
}