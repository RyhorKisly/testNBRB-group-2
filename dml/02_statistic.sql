-- Добавить валюту в таблицу
INSERT INTO curr.statistic(id, date_curr, abbreviation, scale, name, official_rate, weekend_id)
VALUES (?, ?, ?, ?, ?, ?, (
    SELECT weekend_id
    FROM curr.weekends
    WHERE ? = calendar_date
));

-- getCurrency(long curId)
SELECT id, date_curr, abbreviation, scale, name, official_rate
FROM curr.statistic
WHERE id = ?;

-- getCurrencyFrom(long typeCurrency, curId dateStart, LocalDate dateEnd)
SELECT id, date_curr, abbreviation, scale, name, official_rate
FROM curr.statistic
WHERE date_curr >= ? AND date_curr <= ? AND id = ?;

-- List<StatisticCurrency> getCurrencyFromMonthWithoutWeekend(long curId, int month)
SELECT id, date_curr, abbreviation, scale, name, official_rate
FROM curr.weekends
         INNER JOIN curr.statistic USING(weekend_id)
WHERE id = ? AND EXTRACT(MONTH FROM calendar_date) = ? AND is_day_off = '0';





