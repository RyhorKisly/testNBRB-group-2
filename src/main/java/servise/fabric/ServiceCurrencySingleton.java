package servise.fabric;

import dao.fabric.DaoCurrencySingleton;
import servise.api.IServiceCurrency;

public class ServiceCurrencySingleton {
    private static volatile IServiceCurrency instance;

    private ServiceCurrencySingleton() {
    }

    public static IServiceCurrency getInstance(){
        if(instance==null){
            synchronized (ServiceCurrencySingleton.class){
                if(instance==null){
//                    instance = new ServiceCurrency(DaoCurrencySingleton.getInstance());
                }
            }
        }
        return instance;
    }
}
