package servise.api;

import core.Currency;

import java.util.List;

public interface IServiceCurrency {
    void updateCurrency();

    void save(List<Currency> currencies);

    List<Currency> getCurrency(String typeCurrency);

}
