package servise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Currency;
import core.StatisticCurrency;
import dao.api.IDaoCurrency;
import servise.api.IServiceCurrency;
import servise.api.IServiceSend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ServiceSend implements IServiceSend {
    private final ObjectMapper objectMapper;
    private final IServiceCurrency serviceCurrency;

    public ServiceSend(IServiceCurrency serviceCurrency) {
        this.objectMapper = new ObjectMapper();
        this.serviceCurrency = serviceCurrency;
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
        LinkedList<Currency> currencies = null;
        URL url = null;
        try {
            url = new URL("https://api.nbrb.by/exrates/currencies");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))){
                    String listOfCurrencies = bufferedReader.readLine();
                    currencies = objectMapper.readValue(listOfCurrencies, new TypeReference<LinkedList<Currency>>() {});
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }

    @Override
    public Currency getCurrency(String type) {
        long idCurrency = serviceCurrency.getId(type);
        if(idCurrency == 0){
            throw new IllegalArgumentException("Не верно указана аббревиатура валюты");
        }
        String urlString = "https://api.nbrb.by/exrates/currencies/"+ idCurrency;
        Currency currency = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))){
                    String currencyOnID = bufferedReader.readLine();
                    currency = objectMapper.readValue(currencyOnID, Currency.class);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currency;
    }
}
