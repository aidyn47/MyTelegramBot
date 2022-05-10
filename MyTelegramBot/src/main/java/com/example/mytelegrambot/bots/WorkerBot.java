package com.example.mytelegrambot.bots;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class WorkerBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "@sca19b_bot";
    }

    @Override
    public String getBotToken() {
        return "5397233464:AAFXgvdDiz55DxMP7HRc2-jEtnbz0qTKi2I";
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            JsonMapper jsonMapper = new JsonMapper();
            String json = jsonMapper.writeValueAsString(update);
            Files.writeString(Path.of("jsons", "result.json"), json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
