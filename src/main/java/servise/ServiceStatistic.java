package servise;

import core.Currency;
import core.StatisticCurrency;
import dao.api.IDaoStatisticCurrency;
import servise.api.IServiceCurrency;
import servise.api.IServiceSend;
import servise.api.IServiceStatistic;

import javax.sql.XAConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ServiceStatistic implements IServiceStatistic {
    private final IDaoStatisticCurrency daoStatisticCurrency;
    private final IServiceCurrency serviceCurrency;
    private final IServiceSend serviceSend;

    public ServiceStatistic(IDaoStatisticCurrency daoStatisticCurrency, IServiceCurrency serviceCurrency, IServiceSend serviceSend) {
        this.daoStatisticCurrency = daoStatisticCurrency;
        this.serviceCurrency = serviceCurrency;
        this.serviceSend = serviceSend;
    }

    @Override
    public List<StatisticCurrency> getCurrency(String typeCurrency, LocalDate dateStart, LocalDate dateEnd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(!LocalDate.parse("2023-05-31", formatter).isAfter(dateEnd)||
                LocalDate.parse("2022-12-01", formatter).isAfter(dateStart)){
            throw new IllegalArgumentException("Время не пошпо по веренному диапазону");
        }
        List<Currency> currencies = serviceCurrency.getCurrency(typeCurrency);
        List<StatisticCurrency> statisticCurrencies = new ArrayList<>();
        if(currencies==null||currencies.size()==0) {
            throw new IllegalArgumentException("Данной валюты не существует");
        }
        LocalDate start = dateStart;
        LocalDate end = dateEnd;
        for(Currency currency : currencies){
            if(currency.getDateEnd().toLocalDate().isBefore(dateStart)||
                    currency.getDateStart().toLocalDate().isAfter(dateEnd)){
                continue;
            }
            if(currency.getDateStart().toLocalDate().isAfter(dateStart)||currency.getDateStart().toLocalDate().isEqual(dateStart)){
                start = currency.getDateStart().toLocalDate();
            } else
            {
                start = dateStart;
            }
            if(currency.getDateEnd().toLocalDate().isBefore(dateEnd)||currency.getDateEnd().toLocalDate().isEqual(dateEnd)){
                end = currency.getDateEnd().toLocalDate();
            } else {
                end = dateEnd;
            }
            statisticCurrencies.addAll(getAllCurrency(currency,start,end));
        }
        return statisticCurrencies;
    }

    private List<StatisticCurrency> getAllCurrency(Currency currency, LocalDate dayStart, LocalDate dayEnd){
        List<StatisticCurrency> statisticCurrencies = daoStatisticCurrency.getCurrencyFrom(currency.getId(),dayStart,dayEnd);
        if((statisticCurrencies.size()!=0)){
            List<StatisticCurrency> list = new ArrayList<>();
            statisticCurrencies.sort(Comparator.comparing(StatisticCurrency::getDate));
            for(int i = 1;i<statisticCurrencies.size()-1;i++){
                List<StatisticCurrency> temp = new ArrayList<>();
                if (!statisticCurrencies.get(i).getDate().minusDays(1).isEqual(statisticCurrencies.get(i-1).getDate())){
                    temp = serviceSend.sendGetDynamics(currency.getId(),
                            statisticCurrencies.get(i-1).getDate().plusDays(1).toLocalDate(),
                            statisticCurrencies.get(i).getDate().minusDays(1).toLocalDate());
                    daoStatisticCurrency.saveStatisticCurrency(temp);
                    list.addAll(temp);
                }
            }
            if(!statisticCurrencies.get(0).getDate().toLocalDate().isEqual(dayStart)){
                List<StatisticCurrency> temp = serviceSend.sendGetDynamics(currency.getId(),
                        dayStart,
                        statisticCurrencies.get(0).getDate().toLocalDate().minusDays(1));
                list.addAll(temp);
                daoStatisticCurrency.saveStatisticCurrency(temp);
            }
            if(!statisticCurrencies.get(statisticCurrencies.size()-1).getDate().toLocalDate().isEqual(dayEnd)){
                List<StatisticCurrency> temp = serviceSend.sendGetDynamics(currency.getId(),
                        statisticCurrencies.get(statisticCurrencies.size()-1).getDate().toLocalDate().plusDays(1),
                        dayEnd);
                list.addAll(temp);
                daoStatisticCurrency.saveStatisticCurrency(temp);
            }
            statisticCurrencies.addAll(list);
        } else {
            statisticCurrencies = serviceSend.sendGetDynamics(currency.getId(),dayStart,dayEnd);
            daoStatisticCurrency.saveStatisticCurrency(statisticCurrencies);
        }
        statisticCurrencies.sort(Comparator.comparing(StatisticCurrency::getDate));
        return statisticCurrencies;
    }

    @Override
    public List<StatisticCurrency> getCurrency(String typeCurrency) {
       List<StatisticCurrency> statisticCurrencies = new ArrayList<>();
       List<Currency> currencies = serviceCurrency.getCurrency(typeCurrency);
       if(currencies==null||currencies.size()==0){
           throw new IllegalArgumentException("Такой валюты не существует");
       }
       for(Currency currency : currencies){
           statisticCurrencies.addAll(daoStatisticCurrency.getCurrency(currency.getId()));
       }
       return statisticCurrencies;
    }

    @Override
    public double getAvgCurrency(String typeCurrency, int monthMM) {
        List<Currency> currencies = serviceCurrency.getCurrency(typeCurrency);
        if(currencies==null||currencies.size()==0)
            throw new IllegalArgumentException("Такой валюты не существует");
        List<StatisticCurrency> statisticCurrencies = new ArrayList<>();
        for(Currency currency : currencies){
            statisticCurrencies.addAll(daoStatisticCurrency.getCurrencyFromMonthWithoutWeekend(currency.getId(),monthMM));
        }
        double avg = 0;
        for(StatisticCurrency statisticCurrency : statisticCurrencies){
            avg = avg+statisticCurrency.getOfficialRate();
        }
        avg = avg/statisticCurrencies.size();
        return avg;
    }
}
