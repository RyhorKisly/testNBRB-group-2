-- Добавить валюту в таблицу
INSERT INTO curr.statistic(id, date_curr, abbreviation, scale, name, official_rate)
VALUES (?, ?, ?, ?, ?, ?);