package cn.iyowei.stockai.collector.resolver.business.jrj.vo;

/**
 * Created by vick on 2015/8/16.
 */
public class Hqa {
    private Summary summary;
    private Column column;
    private HqData hqData;

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public HqData getHqData() {
        return hqData;
    }

    public void setHqData(HqData hqData) {
        this.hqData = hqData;
    }

    @Override
    public String toString() {
        return "Hqa{" +
                "summary=" + summary +
                ", column=" + column +
                ", hqData=" + hqData +
                '}';
    }
}
