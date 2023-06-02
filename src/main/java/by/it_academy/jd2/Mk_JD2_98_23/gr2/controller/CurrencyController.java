package by.it_academy.jd2.Mk_JD2_98_23.gr2.controller;

import by.it_academy.jd2.Mk_JD2_98_23.gr2.core.dto.CurrencyDto;
import by.it_academy.jd2.Mk_JD2_98_23.gr2.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CurrencyController {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private final CurrencyService currencyService = new CurrencyService();

    public List<CurrencyDto> addCurrencies() {
        return currencyService.addCurrencies();
    }

    public List<CurrencyDto> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

}
