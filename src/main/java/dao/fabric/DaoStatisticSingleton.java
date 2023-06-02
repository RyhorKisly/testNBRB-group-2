package dao.fabric;

import dao.DaoStatistic;
import dao.api.IDaoStatisticCurrency;
import dao.api.IDaoCurrency;
import dao.ds.fabric.DataSourceSingleton;

import java.beans.PropertyVetoException;

public class DaoStatisticSingleton {
    private static volatile IDaoStatisticCurrency instance;

    private DaoStatisticSingleton() {
    }

    public static IDaoStatisticCurrency getInstance() {
        if(instance==null){
            synchronized (DaoStatisticSingleton.class){
                if(instance==null){
                    try {
                        instance = new DaoStatistic(DataSourceSingleton.getInstance());
                    } catch (PropertyVetoException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }
}
