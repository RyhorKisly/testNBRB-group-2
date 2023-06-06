package dao.api;

import core.Currency;

import java.util.List;

public interface IDaoCurrency {
    void saveCurrency(List<Currency> currencies);

    List<Currency> getCurrency();

    List<Currency> getCurrency(String typeCurrency);

    void remove(long id);
}
