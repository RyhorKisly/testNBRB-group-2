package by.it_academy.jd2.Mk_JD2_98_23.gr2.controller;

import by.it_academy.jd2.Mk_JD2_98_23.gr2.core.dto.RateDto;
import by.it_academy.jd2.Mk_JD2_98_23.gr2.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RateController {
    private static final Logger logger = LoggerFactory.getLogger(RateController.class);
    private final RateService rateService = new RateService();

    public List<RateDto> addRates() {
        return rateService.addRates();
    }

    public List<RateDto> getAllRates() {
        return rateService.getAllRates();
    }

}
