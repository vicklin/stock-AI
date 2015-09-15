package cn.iyowei.stockai.data.manager;

/**
 * 数据阀值
 * Created by liuguanglin on 15/9/15.
 */
public class DataThreshold {
    private int type; // 类型
    private long threshold; // 阀值
    private long amount; // 当前数量

    public DataThreshold(int type, long threshold) {
        this.type = type;
        this.threshold = threshold;
    }

    public boolean isOverThreshold() {
        return this.amount > this.threshold;
    }

    public boolean isUnderThreshold() {
        return this.amount < this.threshold;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public long getAmount() {
        return amount;
    }

    public DataThreshold setAmount(long amount) {
        this.amount = amount;
        return this;
    }
}
