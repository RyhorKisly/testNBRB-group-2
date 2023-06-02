package by.it_academy.jd2.Mk_JD2_98_23.gr2.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyDto {

    @JsonProperty("Cur_ID")
    private int curId;

    @JsonProperty("Cur_ParentID")
    private int curParentId;

    @JsonProperty("Cur_Code")
    private String curCode;

    @JsonProperty("Cur_Abbreviation")
    private String curAbbreviation;

    @JsonProperty("Cur_Name")
    private String curName;

    @JsonProperty("Cur_Name_Bel")
    private String curNameBel;

    @JsonProperty("Cur_Name_Eng")
    private String curNameEng;

    @JsonProperty("Cur_QuotName")
    private String curQuotName;

    @JsonProperty("Cur_QuotName_Bel")
    private String curQuotNameBel;

    @JsonProperty("Cur_QuotName_Eng")
    private String curQuotNameEng;

    @JsonProperty("Cur_NameMulti")
    private String curNameMulti;

    @JsonProperty("Cur_Name_BelMulti")
    private String curNameBelMulti;

    @JsonProperty("Cur_Name_EngMulti")
    private String curNameEngMulti;

    @JsonProperty("Cur_Scale")
    private int curScale;

    @JsonProperty("Cur_Periodicity")
    private int curPeriodicity;

    @JsonProperty("Cur_DateStart")
    private String curDateStart;

    @JsonProperty("Cur_DateEnd")
    private String curDateEnd;

    public CurrencyDto() {
    }

    public CurrencyDto(int curId,
                       int curParentId,
                       String curCode,
                       String curAbbreviation,
                       String curName,
                       String curNameBel,
                       String curNameEng,
                       String curQuotName,
                       String curQuotNameBel,
                       String curQuotNameEng,
                       String curNameMulti,
                       String curNameBelMulti,
                       String curNameEngMulti,
                       int curScale,
                       int curPeriodicity,
                       String curDateStart,
                       String curDateEnd) {
        this.curId = curId;
        this.curParentId = curParentId;
        this.curCode = curCode;
        this.curAbbreviation = curAbbreviation;
        this.curName = curName;
        this.curNameBel = curNameBel;
        this.curNameEng = curNameEng;
        this.curQuotName = curQuotName;
        this.curQuotNameBel = curQuotNameBel;
        this.curQuotNameEng = curQuotNameEng;
        this.curNameMulti = curNameMulti;
        this.curNameBelMulti = curNameBelMulti;
        this.curNameEngMulti = curNameEngMulti;
        this.curScale = curScale;
        this.curPeriodicity = curPeriodicity;
        this.curDateStart = curDateStart;
        this.curDateEnd = curDateEnd;
    }

    public int getCurId() {
        return curId;
    }

    public void setCurId(int curId) {
        this.curId = curId;
    }

    public int getCurParentId() {
        return curParentId;
    }

    public void setCurParentId(int curParentId) {
        this.curParentId = curParentId;
    }

    public String getCurCode() {
        return curCode;
    }

    public void setCurCode(String curCode) {
        this.curCode = curCode;
    }

    public String getCurAbbreviation() {
        return curAbbreviation;
    }

    public void setCurAbbreviation(String curAbbreviation) {
        this.curAbbreviation = curAbbreviation;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public String getCurNameBel() {
        return curNameBel;
    }

    public void setCurNameBel(String curNameBel) {
        this.curNameBel = curNameBel;
    }

    public String getCurNameEng() {
        return curNameEng;
    }

    public void setCurNameEng(String curNameEng) {
        this.curNameEng = curNameEng;
    }

    public String getCurQuotName() {
        return curQuotName;
    }

    public void setCurQuotName(String curQuotName) {
        this.curQuotName = curQuotName;
    }

    public String getCurQuotNameBel() {
        return curQuotNameBel;
    }

    public void setCurQuotNameBel(String curQuotNameBel) {
        this.curQuotNameBel = curQuotNameBel;
    }

    public String getCurQuotNameEng() {
        return curQuotNameEng;
    }

    public void setCurQuotNameEng(String curQuotNameEng) {
        this.curQuotNameEng = curQuotNameEng;
    }

    public String getCurNameMulti() {
        return curNameMulti;
    }

    public void setCurNameMulti(String curNameMulti) {
        this.curNameMulti = curNameMulti;
    }

    public String getCurNameBelMulti() {
        return curNameBelMulti;
    }

    public void setCurNameBelMulti(String curNameBelMulti) {
        this.curNameBelMulti = curNameBelMulti;
    }

    public String getCurNameEngMulti() {
        return curNameEngMulti;
    }

    public void setCurNameEngMulti(String curNameEngMulti) {
        this.curNameEngMulti = curNameEngMulti;
    }

    public int getCurScale() {
        return curScale;
    }

    public void setCurScale(int curScale) {
        this.curScale = curScale;
    }

    public int getCurPeriodicity() {
        return curPeriodicity;
    }

    public void setCurPeriodicity(int curPeriodicity) {
        this.curPeriodicity = curPeriodicity;
    }

    public String getCurDateStart() {
        return curDateStart;
    }

    public void setCurDateStart(String curDateStart) {
        this.curDateStart = curDateStart;
    }

    public String getCurDateEnd() {
        return curDateEnd;
    }

    public void setCurDateEnd(String curDateEnd) {
        this.curDateEnd = curDateEnd;
    }

    @Override
    public String toString() {
        return "CurrencyDto{" + "curId=" + curId + ", curParentId=" + curParentId + ", curCode='" + curCode + '\'' + ", curAbbreviation='" + curAbbreviation + '\'' + ", curName='" + curName + '\'' + ", curNameBel='" + curNameBel + '\'' + ", curNameEng='" + curNameEng + '\'' + ", curQuotName='" + curQuotName + '\'' + ", curQuotNameBel='" + curQuotNameBel + '\'' + ", curQuotNameEng='" + curQuotNameEng + '\'' + ", curNameMulti='" + curNameMulti + '\'' + ", curNameBelMulti='" + curNameBelMulti + '\'' + ", curNameEngMulti='" + curNameEngMulti + '\'' + ", curScale=" + curScale + ", curPeriodicity=" + curPeriodicity + ", curDateStart='" + curDateStart + '\'' + ", curDateEnd='" + curDateEnd + '\'' + '}';
    }
}
