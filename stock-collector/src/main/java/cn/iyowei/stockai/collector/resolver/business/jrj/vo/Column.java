package cn.iyowei.stockai.collector.resolver.business.jrj.vo;

/**
 * Created by vick on 2015/8/16.
 */
public class Column {

    private String id; //0 证券代码，含sh，sz
    private String code; //1 证券代码（简）
    private String name; //2 证券简称
    private Double lcp; //3 昨收
    private Double stp; //4
    private Double np; //5 最新价格
    private Double ta; //6 成交量
    private Double tm; //7 成交额
    private Double hlp; //8 涨跌额
    private Double pl; //9 涨跌幅
    private Double sl; //10 振幅
    private Double cat; //11 量比
    private Double cot; //12 委比
    private Double tr; //13 换手率
    private Double ape; //14 市盈率
    private Double min5pl; //15 五分钟涨幅

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "Column{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", lcp=" + lcp +
                ", stp=" + stp +
                ", np=" + np +
                ", ta=" + ta +
                ", tm=" + tm +
                ", hlp=" + hlp +
                ", pl=" + pl +
                ", sl=" + sl +
                ", cat=" + cat +
                ", cot=" + cot +
                ", tr=" + tr +
                ", ape=" + ape +
                ", min5pl=" + min5pl +
                '}';
    }
}
