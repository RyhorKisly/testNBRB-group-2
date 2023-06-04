package servise;

import core.StatisticCurrency;
import dao.api.IDaoStatisticCurrency;
import servise.api.IServiceCurrency;
import servise.api.IServiceSend;
import servise.api.IServiceStatistic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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
        if(!serviceCurrency.exist(typeCurrency))
            throw new IllegalArgumentException("Данной валюты не существует");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(LocalDate.parse("2023-31-05", formatter).isAfter(dateEnd)||
                LocalDate.parse("2022-12-01", formatter).isBefore(dateStart)){
            throw new IllegalArgumentException("Время не пошпо по веренному диапазону");
        }
        long id = serviceCurrency.getId(typeCurrency);
        List<StatisticCurrency> currencies = daoStatisticCurrency.getCurrencyFrom(id,dateStart,dateEnd);
        currencies.sort(Comparator.comparing(StatisticCurrency::getDate));
        if(currencies==null){
            currencies = serviceSend.sendGetDynamics(id,dateStart,dateEnd);
            daoStatisticCurrency.saveStatisticCurrency(currencies);
            return currencies;
        } else {
            if(!dateStart.isEqual(currencies.get(0).getDate().toLocalDate())){
                List<StatisticCurrency> listAdd = serviceSend.sendGetDynamics(id,dateStart,currencies.get(0).getDate().toLocalDate().minusDays(1));
                currencies.addAll(listAdd);
                currencies.sort(Comparator.comparing(StatisticCurrency::getDate));
                daoStatisticCurrency.saveStatisticCurrency(listAdd);
            }
            if(!dateEnd.isEqual(currencies.get(currencies.size()-1).getDate().toLocalDate())){
                List<StatisticCurrency> listAdd = serviceSend.sendGetDynamics(id,currencies.get(currencies.size()+1).getDate().toLocalDate().plusDays(1),dateEnd);
                currencies.addAll(listAdd);
                currencies.sort(Comparator.comparing(StatisticCurrency::getDate));
                daoStatisticCurrency.saveStatisticCurrency(listAdd);
            }
        }
        return currencies;
    }

    @Override
    public List<StatisticCurrency> getCurrency(String typeCurrency) {
        if(!serviceCurrency.exist(typeCurrency)){
            throw new IllegalArgumentException("Данной валюты не существует");
        }
        long id = serviceCurrency.getId(typeCurrency);
        return daoStatisticCurrency.getCurrency(id);
    }

    @Override
    public double getAvgCurrency(String typeCurrency, int monthMM) {
        if(!serviceCurrency.exist(typeCurrency))
            throw new IllegalArgumentException("Данной валюты не существует");
        if(monthMM>12||monthMM<1)
            throw new IllegalArgumentException("Данного месяца не существует");
        long id = serviceCurrency.getId(typeCurrency);
        double avg = 0;
        List<StatisticCurrency> currencies = daoStatisticCurrency.getCurrencyFromMonthWithoutWeekend(id,monthMM);
        for(StatisticCurrency statisticCurrency : currencies){
            avg = avg+statisticCurrency.getOfficialRate();
        }
        avg = avg/currencies.size();
        return avg;
    }
}
