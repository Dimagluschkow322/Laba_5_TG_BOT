package ru.university.textbot;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import ru.university.textbot.config.BotConfig;

public class TextBotApplication {
    public static void main(String[] args) throws Exception {

        System.out.println("Запуск бота...");
        TextProcessorBot bot = new TextProcessorBot(BotConfig.BOT_TOKEN);

        TelegramBotsLongPollingApplication app = new TelegramBotsLongPollingApplication();
        app.registerBot(BotConfig.BOT_TOKEN, bot);

        System.out.println("Бот успешно запущен!");
        System.out.println("Имя: @" + BotConfig.BOT_USERNAME);
        System.out.println("Пиши /start → @Echoletter_bot");

        while (true) {
            Thread.sleep(1000);
        }
    }
}