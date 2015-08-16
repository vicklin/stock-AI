package cn.iyowei.stockai.web.crawler.vo;

/**
 * Created by vick on 2015/8/16.
 */
public class Summary {

    private Integer mstat;
    private Integer pages; // total
    private Integer page;
    private Integer total;
    private String hqtime; // 20150814152958

    public Integer getMstat() {
        return mstat;
    }

    public void setMstat(Integer mstat) {
        this.mstat = mstat;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getHqtime() {
        return hqtime;
    }

    public void setHqtime(String hqtime) {
        this.hqtime = hqtime;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "mstat=" + mstat +
                ", pages=" + pages +
                ", page=" + page +
                ", total=" + total +
                ", hqtime='" + hqtime + '\'' +
                '}';
    }
}
