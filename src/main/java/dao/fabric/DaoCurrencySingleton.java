package dao.fabric;

import dao.DaoCurrency;
import dao.api.IDaoCurrency;
import dao.ds.fabric.DataSourceSingleton;

import java.beans.PropertyVetoException;

public class DaoCurrencySingleton {
    private static volatile IDaoCurrency instance;

    private DaoCurrencySingleton() {
    }

    public static IDaoCurrency getInstance(){
        if(instance==null){
            synchronized (DaoCurrencySingleton.class){
                if(instance==null){
                    try {
                        instance = new DaoCurrency(DataSourceSingleton.getInstance());
                    } catch (PropertyVetoException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }
}
