package com.example.mytelegrambot;


import com.example.mytelegrambot.bots.Buttons;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootApplication
public class MyTelegramBotApplication extends TelegramLongPollingBot {

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            handleMessage(update.getMessage());
            Message message = update.getMessage();
            if(message.hasText()){
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("Please press /login").build()
                );
            }
            if(message.hasText()){

            }
        }


    }
    @SneakyThrows
    private void handleMessage(Message message) {
        //handle command
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if(commandEntity.isPresent()){
                String command =
                        message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command){
                    case "/login":
                        List<List< InlineKeyboardButton>> buttons = new ArrayList<>();
                        for (Buttons buttons1 : Buttons.values()){
                            buttons.add(Arrays.asList(
                                    InlineKeyboardButton.builder().text(buttons1.name()).callbackData("Admin" + buttons1).build()
                                    /*InlineKeyboardButton.builder().text(buttons1.name()).callbackData("Moderator" + buttons1).build()*/));
                        }
                        execute(
                                SendMessage.builder()
                                        .text("Who are you?")
                                        .chatId(message.getChatId().toString())
                                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                        .build());

                }
            }
        }

    }

    public MyTelegramBotApplication(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotUsername() {
        return "@sca19b_bot";
    }

    @Override
    public String getBotToken() {
        return "5397233464:AAFXgvdDiz55DxMP7HRc2-jEtnbz0qTKi2I";
    }

    public static void main(String[] args) throws TelegramApiException {
        MyTelegramBotApplication bot = new MyTelegramBotApplication(new DefaultBotOptions());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
}
