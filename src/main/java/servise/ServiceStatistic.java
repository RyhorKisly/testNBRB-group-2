package servise;

import core.Currency;
import core.StatisticCurrency;
import dao.api.IDaoStatisticCurrency;
import servise.api.IServiceCurrency;
import servise.api.IServiceSend;
import servise.api.IServiceStatistic;

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
        if(currencies==null||currencies.size()==0) {
            throw new IllegalArgumentException("Данной валюты не существует");
        }
        List<StatisticCurrency> statistics = new ArrayList<>();
        currencies.sort(Comparator.comparing(Currency::getDateStart));
        boolean start = false;
        for(Currency currency : currencies){
            LocalDate localDateStartTemp;
            LocalDate localDateEndTemp;
            //if(currency.getDateEnd().toLocalDate().)
            List<StatisticCurrency> currenciesTemp = new ArrayList<>();
            if(currency.getDateStart().toLocalDate().isBefore(dateStart)||currency.getDateStart().toLocalDate().isEqual(dateStart)){
                localDateStartTemp = dateStart;
            } else {
                localDateStartTemp = currency.getDateStart().toLocalDate();
            }
            if(currency.getDateEnd().toLocalDate().isBefore(dateEnd)||currency.getDateStart().toLocalDate().isEqual(dateEnd)){
                localDateEndTemp = currency.getDateEnd().toLocalDate();
            } else {
                localDateEndTemp = dateEnd;
            }
//-----------------------1------|---------------2--------|-----------------currency1 +
//------------------------------|---------------1----2---|-----------------currency2 1+
//------------------------------|--------------------1---|-----------------currency3
//------------------------------|1-----------------------|-----------------currency4 1+
////----------------------------|------------------------|2----------------currency5
//------------------------------|1-----------------------|2----------------need
            currenciesTemp = daoStatisticCurrency.getCurrencyFrom(currency.getId(),localDateStartTemp,localDateEndTemp);
            if(currenciesTemp.size()==0){
                currenciesTemp = serviceSend.sendGetDynamics(currency.getId(),localDateStartTemp,localDateEndTemp);
                if(currenciesTemp.size()==0)
                    continue;
                daoStatisticCurrency.saveStatisticCurrency(currenciesTemp);
            }
            currenciesTemp.sort(Comparator.comparing(StatisticCurrency::getDate));
            if(currenciesTemp.get(0).getDate().toLocalDate().isAfter(localDateStartTemp)){
                List<StatisticCurrency> temp = serviceSend.sendGetDynamics(currency.getId(),
                        localDateStartTemp,currenciesTemp.get(0).getDate().minusDays(1).toLocalDate());
                daoStatisticCurrency.saveStatisticCurrency(temp);
                currenciesTemp.addAll(temp);
            }
            currenciesTemp.sort(Comparator.comparing(StatisticCurrency::getDate));
            if(currenciesTemp.get(currencies.size()-1).getDate().toLocalDate().isBefore(localDateEndTemp)){
                List<StatisticCurrency> temp = serviceSend.sendGetDynamics(currency.getId(),
                        currenciesTemp.get(currencies.size()-1).getDate().plusDays(1).toLocalDate(),localDateEndTemp);
                daoStatisticCurrency.saveStatisticCurrency(temp);
                currenciesTemp.addAll(temp);
            }
            for(StatisticCurrency statisticCurrency : currenciesTemp){
                statistics.add(statisticCurrency);
            }

        }
        return statistics;
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
