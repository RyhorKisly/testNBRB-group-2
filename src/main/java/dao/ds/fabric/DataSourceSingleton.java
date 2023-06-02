package dao.ds.fabric;

import dao.ds.DataSourceC3P0;
import dao.ds.api.IDataSourceWrapper;

import java.beans.PropertyVetoException;

public class DataSourceSingleton {
    private static volatile IDataSourceWrapper instance;

    private DataSourceSingleton() {
    }

    public static IDataSourceWrapper getInstance() throws PropertyVetoException {
        if(instance==null){
            synchronized (DataSourceSingleton.class){
                if(instance==null){
                    instance = new DataSourceC3P0();
                }
            }
        }
        return instance;
    }
}
