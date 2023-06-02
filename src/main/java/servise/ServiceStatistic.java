package servise;

import core.StatisticCurrency;
import dao.api.IDaoStatisticCurrency;
import servise.api.IServiceCurrency;
import servise.api.IServiceSend;
import servise.api.IServiceStatistic;

import java.time.LocalDate;
import java.util.List;

public class ServiceStatistic implements IServiceStatistic {
    private final IServiceCurrency serviceCurrency;
    private final IServiceSend serviceSend;
    private final IDaoStatisticCurrency daoStatistic;

    public ServiceStatistic(IServiceCurrency serviceCurrency, IServiceSend serviceSend, IDaoStatisticCurrency daoStatisticCurrency) {
        this.serviceCurrency = serviceCurrency;
        this.serviceSend = serviceSend;
        this.daoStatistic = daoStatisticCurrency;
    }

    @Override
    public List<StatisticCurrency> getCurrency(String typeCurrency, LocalDate dateStart, LocalDate dateEnd) {
        long id = serviceCurrency.getId(typeCurrency);
        List<StatisticCurrency> statistics = daoStatistic.getCurrency(id,dateStart,dateEnd);
        if(statistics.size()==0||statistics==null){
            statistics.addAll(serviceSend.sendGetDynamics(id,dateStart,dateEnd));
            daoStatistic.saveStatisticCurrency(statistics);
        }else {
            if(!statistics.get(0).getDate().equals(dateStart)){
                List<StatisticCurrency> beforeList = serviceSend.sendGetDynamics(id,dateStart,statistics.get(0).getDate().toLocalDate().minusDays(1));
                daoStatistic.saveStatisticCurrency(beforeList);
                statistics.addAll(beforeList);
            }
            if(!statistics.get(statistics.size()-1).getDate().equals(dateEnd)){
                List<StatisticCurrency> afterList = serviceSend.sendGetDynamics(
                        id,statistics.get(statistics.size()-1).getDate().toLocalDate().plusDays(1),dateEnd);
                daoStatistic.saveStatisticCurrency(afterList);
                statistics.addAll(afterList);

            }
        }
        return statistics;
    }

    //Вывести все курсы по заданному типу валюты, имеющиеся в базе данных. Пользователь указывает тип валюты. В качестве ответа получает список курсов валют имеющихся в базе данных.
    @Override
    public List<StatisticCurrency> getCurrency(String typeCurrency) {
        long idCurrency = serviceCurrency.getId(typeCurrency);
        if(idCurrency==0){
            throw new IllegalArgumentException("Не верно введена абривеатиура валюты");
        }
         return daoStatistic.getCurrency(idCurrency);
    }

    @Override
    public double getAvgCurrency(String typeCurrency, int monthMM, int yearYYYY) { //дописать проверку на недостающую дату
        LocalDate dateStart = LocalDate.of(yearYYYY,monthMM,1);
        LocalDate dateEnd = LocalDate.of(yearYYYY,monthMM,1);
        dateEnd.plusMonths(1);
        dateEnd.minusDays(1);
        long id = serviceCurrency.getId(typeCurrency);
        List<StatisticCurrency> statisticCurrencies = daoStatistic.getCurrency(id,dateStart,dateEnd);
        double avg = 0;
        for(StatisticCurrency statisticCurrency : statisticCurrencies){
            avg = avg + statisticCurrency.getOfficialRate();
        }
        avg = avg/statisticCurrencies.size();
        return avg;
    }
}
