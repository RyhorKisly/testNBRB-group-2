-- Добавить валюту в таблицу
INSERT INTO curr.statistic(id, date_curr, abbreviation, scale, name, official_rate, weekend_id)
VALUES (?, ?, ?, ?, ?, ?);

-- getCurrency(long curId)
SELECT id, date_curr, abbreviation, scale, name, official_rate
FROM curr.statistic
WHERE id = ?;

-- getCurrencyFrom(long typeCurrency, curId dateStart, LocalDate dateEnd)
SELECT id, date_curr, abbreviation, scale, name, official_rate
FROM curr.statistic
WHERE date_curr >= ? AND date_curr <= ? AND id = ?;

-- List<StatisticCurrency> getCurrencyFromMonthWithoutWeekend(long curId, int month)
SELECT
id, date_curr, abbreviation, scale, name, official_rate
FROM curr.statistic
WHERE id = ?
AND EXTRACT(MONTH FROM date_curr) = ?
AND EXTRACT(YEAR FROM date_curr) = ?
AND date_curr = (SELECT calendar_date
FROM curr.weekends
WHERE is_day_off = '0' AND calendar_date = date_curr);





