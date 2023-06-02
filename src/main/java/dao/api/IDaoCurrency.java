package dao.api;

import core.Currency;

import java.util.List;

public interface IDaoCurrency {
    long getID(String typeCurrency);
    void saveCurrency(List<Currency> currencies);
    boolean exist(String typeCurrency);
    List<Currency> getCurrency();
    void remove(long id);
}
