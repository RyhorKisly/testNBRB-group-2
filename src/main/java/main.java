import core.StatisticCurrency;
import dao.api.IDaoCurrency;
import dao.fabric.DaoCurrencySingleton;
import servise.ServiceSend;
import servise.ServiceStatistic;
import servise.api.IServiceCurrency;
import servise.api.IServiceSend;
import servise.api.IServiceStatistic;
import servise.fabric.ServiceCurrencySingleton;
import servise.fabric.ServiceSendSingleton;
import servise.fabric.ServiceStatisticSingleton;

import java.time.LocalDate;
import java.util.List;

public class main {
    public static void main(String[] args) {
        IServiceSend serviceSend = ServiceSendSingleton.getInstance();
        IServiceStatistic serviceStatistic = ServiceStatisticSingleton.getInstance();
        IServiceCurrency serviceCurrency = ServiceCurrencySingleton.getInstance();
//        serviceCurrency.updateCurrency();
//        IDaoCurrency daoCurrency = DaoCurrencySingleton.getInstance();
//        String sss = daoCurrency.getCurrency().toString();
//        System.out.print(sss);
//        System.out.println(serviceCurrency.getId("USD"));
////        System.out.println(serviceCurrency.getId("AAa"));
//        System.out.println(serviceCurrency.exist("EUR"));
//        System.out.println(serviceCurrency.exist("AAA"));
//        LocalDate dateStart = LocalDate.of(2018,02,15);
//        LocalDate dateEnd = LocalDate.of(2018,03,15);
//        List<StatisticCurrency> statisticCurrencies = serviceStatistic.getCurrency("USD",dateStart,dateEnd);
//        System.out.print(statisticCurrencies.toString());
        List<StatisticCurrency> list = serviceStatistic.getCurrency("USD");
    }
}
