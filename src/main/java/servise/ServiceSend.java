package servise.fabric;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Currency;
import core.StatisticCurrency;
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
    private ObjectMapper objectMapper;

    public ServiceSend() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<StatisticCurrency> sendGetDynamics(long idCurrency, LocalDate dateStart, LocalDate dateEnd) {
        return null;
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
                    String sss = bufferedReader.readLine();
                    currencies = objectMapper.readValue(sss,
                            new TypeReference<LinkedList<Currency>>() {});
                }
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }

    @Override
    public Currency getCurrency(String type) {
        return null;
    }
}
