package servise.fabric;

import dao.fabric.DaoCurrencySingleton;
import servise.ServiceCurrency;
import servise.api.IServiceCurrency;
import servise.api.IServiceStatistic;

import java.beans.PropertyVetoException;

public class ServiceCurrencySingleton {
    private static volatile IServiceCurrency instance;

    private ServiceCurrencySingleton() {
    }

    public static IServiceCurrency getInstance(){
        if(instance==null){
            synchronized (ServiceCurrencySingleton.class){
                if(instance==null){
                    instance = new ServiceCurrency(DaoCurrencySingleton.getInstance());
                }
            }
        }
        return instance;
    }
}
