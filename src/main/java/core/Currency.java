package core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.Objects;

public class Currency {
    @JsonSetter("Cur_ID")
    private long id;
    @JsonSetter("Cur_ParentID")
    private long parentId;
    @JsonSetter("Cur_Code")
    private long code;
    @JsonSetter("Cur_Abbreviation")
    private String abbreviation;
    @JsonSetter("Cur_Name")
    private String name;
    @JsonSetter("Cur_Name_Bel")
    private String nameBel;
    @JsonSetter("Cur_Name_Eng")
    private String nameEng;
    @JsonSetter("Cur_QuotName")
    private String quotName;
    @JsonSetter("Cur_QuotName_Bel")
    private String quotNameBel;
    @JsonSetter("Cur_QuotName_Eng")
    private String quotNameEng;
    @JsonSetter("Cur_NameMulti")
    private String nameMulti;
    @JsonSetter("Cur_Name_BelMulti")
    private String nameBelMulti;
    @JsonSetter("Cur_Name_EngMulti")
    private String nameEngMulti;
    @JsonSetter("Cur_Scale")
    private long scale;
    @JsonSetter("Cur_Periodicity")
    private long periodicity;
    @JsonSetter("Cur_DateStart")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateStart;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSetter("Cur_DateEnd")
    private LocalDateTime dateEnd;//Cur_DateEnd

    public Currency() {
    }

    public Currency(long id, long parentId, long code, String abbreviation, String name, String nameBel, String nameEng, String quotName, String quotNameBel, String quotNameEng, String nameMulti, String nameBelMulti, String nameEngMulti, long scale, long periodicity, LocalDateTime dateStart, LocalDateTime dateEnd) {
        this.id = id;
        this.parentId = parentId;
        this.code = code;
        this.abbreviation = abbreviation;
        this.name = name;
        this.nameBel = nameBel;
        this.nameEng = nameEng;
        this.quotName = quotName;
        this.quotNameBel = quotNameBel;
        this.quotNameEng = quotNameEng;
        this.nameMulti = nameMulti;
        this.nameBelMulti = nameBelMulti;
        this.nameEngMulti = nameEngMulti;
        this.scale = scale;
        this.periodicity = periodicity;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameBel() {
        return nameBel;
    }

    public void setNameBel(String nameBel) {
        this.nameBel = nameBel;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getQuotName() {
        return quotName;
    }

    public void setQuotName(String quotName) {
        this.quotName = quotName;
    }

    public String getQuotNameBel() {
        return quotNameBel;
    }

    public void setQuotNameBel(String quotNameBel) {
        this.quotNameBel = quotNameBel;
    }

    public String getQuotNameEng() {
        return quotNameEng;
    }

    public void setQuotNameEng(String quotNameEng) {
        this.quotNameEng = quotNameEng;
    }

    public String getNameMulti() {
        return nameMulti;
    }

    public void setNameMulti(String nameMulti) {
        this.nameMulti = nameMulti;
    }

    public String getNameBelMulti() {
        return nameBelMulti;
    }

    public void setNameBelMulti(String nameBelMulti) {
        this.nameBelMulti = nameBelMulti;
    }

    public String getNameEngMulti() {
        return nameEngMulti;
    }

    public void setNameEngMulti(String nameEngMulti) {
        this.nameEngMulti = nameEngMulti;
    }

    public long getScale() {
        return scale;
    }

    public void setScale(long scale) {
        this.scale = scale;
    }

    public long getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(long periodicity) {
        this.periodicity = periodicity;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return id == currency.id && parentId == currency.parentId && code == currency.code && scale == currency.scale && periodicity == currency.periodicity && Objects.equals(abbreviation, currency.abbreviation) && Objects.equals(name, currency.name) && Objects.equals(nameBel, currency.nameBel) && Objects.equals(nameEng, currency.nameEng) && Objects.equals(quotName, currency.quotName) && Objects.equals(quotNameBel, currency.quotNameBel) && Objects.equals(quotNameEng, currency.quotNameEng) && Objects.equals(nameMulti, currency.nameMulti) && Objects.equals(nameBelMulti, currency.nameBelMulti) && Objects.equals(nameEngMulti, currency.nameEngMulti) && Objects.equals(dateStart, currency.dateStart) && Objects.equals(dateEnd, currency.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, code, abbreviation, name, nameBel, nameEng, quotName, quotNameBel, quotNameEng, nameMulti, nameBelMulti, nameEngMulti, scale, periodicity, dateStart, dateEnd);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", code=" + code +
                ", abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", nameBel='" + nameBel + '\'' +
                ", nameEng='" + nameEng + '\'' +
                ", quotName='" + quotName + '\'' +
                ", quotNameBel='" + quotNameBel + '\'' +
                ", quotNameEng='" + quotNameEng + '\'' +
                ", nameMulti='" + nameMulti + '\'' +
                ", nameBelMulti='" + nameBelMulti + '\'' +
                ", nameEngMulti='" + nameEngMulti + '\'' +
                ", scale=" + scale +
                ", periodicity=" + periodicity +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
