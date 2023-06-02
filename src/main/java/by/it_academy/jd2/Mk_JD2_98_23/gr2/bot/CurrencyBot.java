package by.it_academy.jd2.Mk_JD2_98_23.gr2.bot;

import by.it_academy.jd2.Mk_JD2_98_23.gr2.controller.CurrencyController;
import by.it_academy.jd2.Mk_JD2_98_23.gr2.controller.RateController;
import by.it_academy.jd2.Mk_JD2_98_23.gr2.core.dto.CurrencyDto;
import by.it_academy.jd2.Mk_JD2_98_23.gr2.core.dto.RateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class CurrencyBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyBot.class);

    private final CurrencyController currencyController;
    private final RateController rateController;

    public CurrencyBot() {
        this.currencyController = new CurrencyController();
        this.rateController = new RateController();
    }

    @Override
    public String getBotUsername() {
        return "WayneFinancials_bot";
    }

    @Override
    public String getBotToken() {
        return "6250232141:AAEFgyfNM2xQf8AMyAiuSXd1QGmoNxDS04Y";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            //TODO Добавить кейс для обработки '/start' команды, которая будет выводить все поддерживаемые ботом
            // запросы, а так же будет вызывать метод currencyController.addCurrencies()

            if ("/start".equals(messageText)) {
                currencyController.addCurrencies();
                sendResponse(chatId, "Supported commands: ...");
            } else if ("/addRates".equals(messageText)) {
                List<RateDto> addedRates = rateController.addRates();
                sendResponse(chatId, "Added rates: " + addedRates.toString());
            } else {
                sendResponse(chatId, "Invalid command.");
            }
        }
    }

    private void sendResponse(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
            logger.debug("Response sent to chatId: {}, Text: {}", chatId, text);
        } catch (TelegramApiException e) {
            logger.error("Error occurred while sending response to chatId: {}", chatId, e);
        }
    }

}
