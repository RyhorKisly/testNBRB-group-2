package servise.api;

import core.Currency;

import java.util.List;

public interface IServiceCurrency {
    void updateCurrency();
    void save(List<Currency> currencies);
    long getId(String typeCurrency);
    boolean exist(String type);
}
