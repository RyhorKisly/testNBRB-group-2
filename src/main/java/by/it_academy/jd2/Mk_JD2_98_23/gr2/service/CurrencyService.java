package by.it_academy.jd2.Mk_JD2_98_23.gr2.service;

import by.it_academy.jd2.Mk_JD2_98_23.gr2.bot.CurrencyBot;
import by.it_academy.jd2.Mk_JD2_98_23.gr2.core.dto.CurrencyDto;
import by.it_academy.jd2.Mk_JD2_98_23.gr2.dao.CurrencyDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private final CurrencyDao currencyDao;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CurrencyService() {
        this.currencyDao = new CurrencyDao();
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<CurrencyDto> addCurrencies() {
        String url = "https://api.nbrb.by/exrates/currencies";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                List<CurrencyDto> currencies = objectMapper.readValue(responseBody, new TypeReference<>() {});
                return currencyDao.addCurrencies(currencies);
            }
        } catch (IOException e) {
            logger.error("Error occurred while adding currencies from API to the database.", e);
        }
        return Collections.emptyList();
    }

    public List<CurrencyDto> getAllCurrencies() {
        logger.debug("Retrieving all currencies from the database");
        List<CurrencyDto> allCurrencies = currencyDao.getAllCurrencies();
        if (allCurrencies.isEmpty()) {
            logger.debug("No currencies found in the database");
        } else {
            logger.debug("{} currencies were retrieved successfully", allCurrencies.size());
        }
        return allCurrencies;
    }
}
