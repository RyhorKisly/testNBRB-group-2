package servise;

import core.Currency;
import dao.api.IDaoCurrency;
import servise.api.IServiceCurrency;
import servise.api.IServiceSend;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceCurrency implements IServiceCurrency {
    private final IDaoCurrency daoCurrency;
    private final IServiceSend serviceSend;

    public ServiceCurrency(IDaoCurrency daoCurrency, IServiceSend serviceSend) {
        this.daoCurrency = daoCurrency;
        this.serviceSend = serviceSend;
    }

    @Override
    public void updateCurrency() {
        List<Currency> currencyList = serviceSend.getCurrency();

        save(currencyList);
    }

    @Override
    public void save(List<Currency> currencies) {
        List<Currency> currenciesListFromDataBase = daoCurrency.getCurrency();
        if (currenciesListFromDataBase == null || currenciesListFromDataBase.size() == 0){
            daoCurrency.saveCurrency(currencies);
        } else {
            List<Currency> currenciesToAdd = new LinkedList<>();
            for (Currency newCurrency : currencies) {
                boolean needToAdd = true;
                for (Currency currencyFromDataBase : currenciesListFromDataBase) {
                    if (newCurrency.getId() == currencyFromDataBase.getId() ){
                        needToAdd = false;
                        if(!newCurrency.equals(currencyFromDataBase)) {
                            daoCurrency.remove(currencyFromDataBase.getId());
                            currenciesToAdd.add(newCurrency);
                        }
                        break;
                    }
                }
                if (needToAdd){
                    currenciesToAdd.add(newCurrency);
                }
            }
            daoCurrency.saveCurrency(currenciesToAdd);
        }
    }

    @Override
    public List<Currency> getCurrency() {
        if (daoCurrency.getCurrency() != null || daoCurrency.getCurrency().size() != 0){
            return daoCurrency.getCurrency();
        }
        updateCurrency();
        if (daoCurrency.getCurrency() != null || daoCurrency.getCurrency().size() != 0){
            return daoCurrency.getCurrency();
        }
        else {
            throw new IllegalArgumentException("Такого типа не существует");
        }
    }

    @Override
    public List<Currency> getCurrencyByType(String type) {
        List<Currency> currencies = daoCurrency.getCurrency();
        if (currencies == null || currencies.size() == 0){
            throw new IllegalArgumentException("No currency data available.");
        }

        return currencies.stream()
                .filter(currency -> currency.getAbbreviation().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }
}
