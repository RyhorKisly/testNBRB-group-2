package servise;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Currency;
import core.StatisticCurrency;
import servise.api.IServiceSend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceSend implements IServiceSend {
    private final ObjectMapper objectMapper;

    public ServiceSend() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<StatisticCurrency> sendGetDynamics(long idCurrency, LocalDate dateStart, LocalDate dateEnd) {
        List<StatisticCurrency> statisticCurrencies = null;
        StringBuilder builder = new StringBuilder();
        builder.append("https://api.nbrb.by/exrates/rates/dynamics/");
        builder.append(idCurrency);
        builder.append("?startdate=");
        builder.append(dateStart.toString());
        builder.append("&enddate=");
        builder.append(dateEnd.toString());

        try {
            URL url = new URL(builder.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String listOfCurrencies = bufferedReader.readLine();
                statisticCurrencies = objectMapper.readValue(listOfCurrencies, new TypeReference<List<StatisticCurrency>>() {
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return statisticCurrencies;
    }

    @Override
    public List<Currency> getCurrency() {
        List<Currency> result = new ArrayList<>();

        try {
            URL url = new URL("https://api.nbrb.by/exrates/currencies");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            Currency[] currencies = objectMapper.readValue(connection.getInputStream(), Currency[].class);

            Collections.addAll(result, currencies);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
