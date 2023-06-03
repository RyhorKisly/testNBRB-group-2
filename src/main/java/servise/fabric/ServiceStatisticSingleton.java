package servise.fabric;

import dao.fabric.DaoStatisticSingleton;
import servise.api.IServiceStatistic;

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
