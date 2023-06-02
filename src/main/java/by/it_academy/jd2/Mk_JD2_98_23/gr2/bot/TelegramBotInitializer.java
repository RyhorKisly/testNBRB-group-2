package by.it_academy.jd2.Mk_JD2_98_23.gr2.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBotInitializer {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotInitializer.class);

    public static void init() {
        TelegramBotsApi botsApi;

        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new CurrencyBot());
            logger.info("Telegram bot successfully initialized.");
        } catch (TelegramApiException e) {
            logger.error("Failed to initialize Telegram bot.", e);
        }
    }
}
