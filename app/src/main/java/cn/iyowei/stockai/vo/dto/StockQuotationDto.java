package cn.iyowei.stockai.vo.dto;

/**
 * Created by vick on 2015/8/16.
 */
public class StockQuotationDto {
    private String code;
    private String shortCode; //1
    private String name; //2
    private Double lcp; //3
    private Double stp; //4
    private Double np; //5
    private Double ta; //6
    private Double tm; //7
    private Double hlp; //8
    private Double pl; //9
    private Double sl; //10
    private Double cat; //11
    private Double cot; //12
    private Double tr; //13
    private Double ape; //14
    private Double min5pl; //15

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLcp() {
        return lcp;
    }

    public void setLcp(Double lcp) {
        this.lcp = lcp;
    }

    public Double getStp() {
        return stp;
    }

    public void setStp(Double stp) {
        this.stp = stp;
    }

    public Double getNp() {
        return np;
    }

    public void setNp(Double np) {
        this.np = np;
    }

    public Double getTa() {
        return ta;
    }

    public void setTa(Double ta) {
        this.ta = ta;
    }

    public Double getTm() {
        return tm;
    }

    public void setTm(Double tm) {
        this.tm = tm;
    }

    public Double getHlp() {
        return hlp;
    }

    public void setHlp(Double hlp) {
        this.hlp = hlp;
    }

    public Double getPl() {
        return pl;
    }

    public void setPl(Double pl) {
        this.pl = pl;
    }

    public Double getSl() {
        return sl;
    }

    public void setSl(Double sl) {
        this.sl = sl;
    }

    public Double getCat() {
        return cat;
    }

    public void setCat(Double cat) {
        this.cat = cat;
    }

    public Double getCot() {
        return cot;
    }

    public void setCot(Double cot) {
        this.cot = cot;
    }

    public Double getTr() {
        return tr;
    }

    public void setTr(Double tr) {
        this.tr = tr;
    }

    public Double getApe() {
        return ape;
    }

    public void setApe(Double ape) {
        this.ape = ape;
    }

    public Double getMin5pl() {
        return min5pl;
    }

    public void setMin5pl(Double min5pl) {
        this.min5pl = min5pl;
    }
}
