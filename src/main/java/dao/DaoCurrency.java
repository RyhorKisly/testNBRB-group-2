package dao;

import core.Currency;
import dao.api.IDaoCurrency;
import dao.ds.api.IDataSourceWrapper;

import java.util.List;

public class DaoCurrency implements IDaoCurrency {
    IDataSourceWrapper iDataSourceWrapper;
    public DaoCurrency(IDataSourceWrapper iDataSourceWrapper) {
        this.iDataSourceWrapper = iDataSourceWrapper;
    }

    @Override
    public long getID(String typeCurrency) {
        return 0;
    }

    @Override
    public void saveCurrency(List<Currency> currencies) {

    }

    @Override
    public boolean exist(String typeCurrency) {
        return false;
    }

    @Override
    public List<Currency> getCurrency() {
        return null;
    }

    @Override
    public void remove(long id) {

    }
}
