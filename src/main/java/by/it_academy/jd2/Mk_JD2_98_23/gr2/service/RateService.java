package by.it_academy.jd2.Mk_JD2_98_23.gr2.service;

import by.it_academy.jd2.Mk_JD2_98_23.gr2.core.dto.RateDto;
import by.it_academy.jd2.Mk_JD2_98_23.gr2.dao.RateDao;
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

public class RateService {
    private static final Logger logger = LoggerFactory.getLogger(RateService.class);
    private final RateDao rateDao;

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RateService() {
        this.rateDao = new RateDao();
        this.httpClient = new OkHttpClient();
        this.objectMapper =new ObjectMapper();
    }

    public List<RateDto> addRates() {
        String url = "https://api.nbrb.by/exrates/rates?periodicity=0";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                List<RateDto> rates = objectMapper.readValue(responseBody, new TypeReference<>() {
                });
                return rateDao.addRates(rates);
            }
        } catch (IOException e) {
            logger.error("Error occurred while adding rates from API to the database.", e);
        }
        return Collections.emptyList();
    }

    public List<RateDto> getAllRates() {
        logger.debug("Retrieving all rates from the database");
        List<RateDto> allRates = rateDao.getAllRates();
        if (allRates.isEmpty()) {
            logger.debug("No rates found in the database");
        } else {
            logger.debug("{} rates were retrieved successfully", allRates.size());
        }
        return allRates;
    }
}
