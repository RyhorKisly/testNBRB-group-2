package servise;

import controllerrs.SendController;
import controllerrs.api.ISendController;
import controllerrs.fabric.SendControllerSingleton;
import core.Currency;
import dao.api.IDaoCurrency;
import servise.api.IServiceCurrency;

import java.util.LinkedList;
import java.util.List;

public class ServiceCurrency implements IServiceCurrency {
    private final IDaoCurrency daoCurrency;
    private final ISendController sendController;

    public ServiceCurrency(IDaoCurrency daoCurrency) {
        this.daoCurrency = daoCurrency;
        this.sendController = SendControllerSingleton.getInstance();
    }

    @Override
    public void updateCurrency() {
        List<Currency> currencies = sendController.getCurrency();
        save(currencies);
    }

    @Override
    public void save(List<Currency> currencies) {
        List<Currency> oldCurrencies = daoCurrency.getCurrency();
        if(oldCurrencies==null||oldCurrencies.size()==0){
            daoCurrency.saveCurrency(currencies);
        } else {
            List<Currency> currenciesAdd = new LinkedList<>();
            for(Currency currency : currencies){
                boolean newCurrency = true;
                for(Currency oldCurrency : oldCurrencies){
                    if(oldCurrency.getId()==currency.getId()){
                        newCurrency = false;
                        if(!oldCurrency.equals(currency)){
                            daoCurrency.remove(oldCurrency.getId());
                            currenciesAdd.add(currency);
                        }
                        break;
                    }
                }
                if(newCurrency){
                    currenciesAdd.add(currency);
                }
            }
        }
    }

    @Override
    public long getId(String typeCurrency) {
        long id = daoCurrency.getID(typeCurrency);
        if(id==0)
            throw new IllegalArgumentException("Такой абривиатуры не существует");
        return id;
    }

    @Override
    public boolean exist(String type) {
        return daoCurrency.exist(type);
    }
}
