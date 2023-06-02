package servise;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllerrs.api.ISendController;
import core.Currency;
import core.StatisticCurrency;
import servise.api.IServiceSend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ServiceSend implements IServiceSend {
    private ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();


    public ServiceSend(ISendController sendController) {
    }

    @Override
    public List<StatisticCurrency> sendGetDynamics(long idCurrency, LocalDate dateStart, LocalDate dateEnd) {
        List<StatisticCurrency> statisticCurrencies = null;
        StringBuilder urlStringBuilder = new StringBuilder();
        urlStringBuilder.append("https://api.nbrb.by/exrates/rates/dynamics/");
        urlStringBuilder.append(idCurrency);
        urlStringBuilder.append("?startdate=");
        urlStringBuilder.append(dateStart.toString());
        urlStringBuilder.append("&enddate=");
        urlStringBuilder.append(dateEnd.toString());
        URL url = new URL(urlStringBuilder.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))){
                String sss = bufferedReader.readLine();
                statisticCurrencies = mapper.readValue(sss,
                        new TypeReference<LinkedList<StatisticCurrency>>() {});
            }
        }
        return statisticCurrencies;
        }
    }

    @Override
    public List<Currency> getCurrency() {
        return sendController.getCurrency();
    }
}
