package ru.university.textbot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.university.textbot.config.BotConfig;
import ru.university.textbot.processor.TextProcessor;

public class TextProcessorBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final TextProcessor textProcessor;

    public TextProcessorBot(String botToken) {
        this.telegramClient = new OkHttpTelegramClient(botToken);
        this.textProcessor = new TextProcessor();
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getFrom().getFirstName();

            System.err.println("[LOG] Сообщение от " + userName + " (chatId=" + chatId + "): " + userText);

            String answer;

            if (userText.equals("/start")) {
                answer = "Привет, " + userName + "! Я бот-удалитель гласных.\nНапиши текст, и я удалю из него все гласные буквы.\nКоманды: /help, /stop";
            } else if (userText.equals("/help")) {
                answer = "Я удаляю гласные: а, е, ё, и, о, у, ы, э, ю, я\nПросто пришли мне любой текст!";
            } else if (userText.equals("/stop")) {
                answer = "Пока, " + userName + "! Для продолжения напиши /start";
            } else {
                String result = textProcessor.removeVowels(userText);
                int removed = textProcessor.countRemovedVowels(userText);
                answer = "Результат: " + result + "\nУдалено гласных: " + removed;
            }

            sendText(chatId, answer);
        }
    }

    private void sendText(long chatId, String text) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            telegramClient.execute(msg);
            System.err.println("[LOG] Ответ отправлен (chatId=" + chatId + ")");
        } catch (TelegramApiException e) {
            System.err.println("[ERROR] Ошибка отправки: " + e.getMessage());
            e.printStackTrace();
        }
    }
}