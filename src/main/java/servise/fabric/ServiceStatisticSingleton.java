package servise.fabric;

import dao.fabric.DaoCurrencySingleton;
import dao.fabric.DaoStatisticSingleton;
import servise.ServiceSend;
import servise.ServiceStatistic;
import servise.api.IServiceStatistic;

import java.beans.PropertyVetoException;

public class ServiceStatisticSingleton {
    private static volatile IServiceStatistic instance;

    private ServiceStatisticSingleton() {
    }

    public static IServiceStatistic getInstance() {
        if(instance==null){
            synchronized (ServiceStatisticSingleton.class){
                if(instance==null){
                    instance = new ServiceStatistic(
                            ServiceCurrencySingleton.getInstance(),
                            ServiceSendSingleton.getInstance(),
                            DaoStatisticSingleton.getInstance()
                            );
                }
            }
        }
        return instance;
    }
}
