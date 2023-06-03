package core;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class StatisticCurrency {
    @JsonSetter("Cur_ID")
    private long id;
    @JsonSetter("Date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;
    @JsonSetter("Cur_Abbreviation")
    private String abbreviation;//
    @JsonSetter("Cur_Scale")
    private long scale;
    @JsonSetter("Cur_Name")
    private String name;
    @JsonSetter("Cur_OfficialRate")
    private double officialRate;

    public StatisticCurrency() {
    }

    public StatisticCurrency(long id, LocalDateTime date, String abbreviation, long scale, String name, double officialRate) {
        this.id = id;
        this.date = date;
        this.abbreviation = abbreviation;
        this.scale = scale;
        this.name = name;
        this.officialRate = officialRate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public long getScale() {
        return scale;
    }

    public void setScale(long scale) {
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOfficialRate() {
        return officialRate;
    }

    public void setOfficialRate(double officialRate) {
        this.officialRate = officialRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticCurrency that = (StatisticCurrency) o;
        return id == that.id && scale == that.scale && Double.compare(that.officialRate, officialRate) == 0 && Objects.equals(date, that.date) && Objects.equals(abbreviation, that.abbreviation) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, abbreviation, scale, name, officialRate);
    }

    @Override
    public String toString() {
        return "StatisticCurrency{" +
                "id=" + id +
                ", date=" + date +
                ", abbreviation='" + abbreviation + '\'' +
                ", scale=" + scale +
                ", name='" + name + '\'' +
                ", officialRate=" + officialRate +
                '}';
    }
}
