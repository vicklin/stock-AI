package cn.iyowei.stockai.data.manager;

import java.util.Collection;

/**
 * 数据阀值
 * Created by liuguanglin on 15/9/15.
 */
public class DataThreshold {
    private long threshold; // 阀值
    private long amount; // 当前数量

    private String mainSet;

    private Collection<String> targetSets;

    public DataThreshold(String mainSet, Collection<String> targetSets, long threshold) {
        this.mainSet = mainSet;
        this.targetSets = targetSets;
        this.threshold = threshold;
    }

    public boolean isOverThreshold() {
        return this.amount > this.threshold;
    }

    public boolean isUnderThreshold() {
        return this.amount < this.threshold;
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

    public Collection<String> getTargetSets() {
        return targetSets;
    }

    public void setTargetSets(Collection<String> targetSets) {
        this.targetSets = targetSets;
    }

    public String getMainSet() {
        return mainSet;
    }

    public void setMainSet(String mainSet) {
        this.mainSet = mainSet;
    }
}
