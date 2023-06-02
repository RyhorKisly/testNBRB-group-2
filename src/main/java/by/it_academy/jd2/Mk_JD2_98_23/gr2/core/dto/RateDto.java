package by.it_academy.jd2.Mk_JD2_98_23.gr2.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class RateDto {
    @JsonProperty("Cur_ID")
    private int curId;

    @JsonProperty("Date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @JsonProperty("Cur_Abbreviation")
    private String curAbbreviation;

    @JsonProperty("Cur_Scale")
    private int curScale;

    @JsonProperty("Cur_Name")
    private String curName;

    @JsonProperty("Cur_OfficialRate")
    private double curOfficialRate;

    public RateDto() {
    }

    public RateDto(int curId,
                   LocalDate date,
                   String curAbbreviation,
                   int curScale,
                   String curName,
                   double curOfficialRate) {
        this.curId = curId;
        this.date = date;
        this.curAbbreviation = curAbbreviation;
        this.curScale = curScale;
        this.curName = curName;
        this.curOfficialRate = curOfficialRate;
    }

    public int getCurId() {
        return curId;
    }

    public void setCurId(int curId) {
        this.curId = curId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCurAbbreviation() {
        return curAbbreviation;
    }

    public void setCurAbbreviation(String curAbbreviation) {
        this.curAbbreviation = curAbbreviation;
    }

    public int getCurScale() {
        return curScale;
    }

    public void setCurScale(int curScale) {
        this.curScale = curScale;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public double getCurOfficialRate() {
        return curOfficialRate;
    }

    public void setCurOfficialRate(double curOfficialRate) {
        this.curOfficialRate = curOfficialRate;
    }

    @Override
    public String toString() {
        return "RateDto{" +
                "curId=" + curId +
                ", date=" + date +
                ", curAbbreviation='" + curAbbreviation + '\'' +
                ", curScale=" + curScale +
                ", curName='" + curName + '\'' +
                ", curOfficialRate=" + curOfficialRate +
                '}';
    }
}
