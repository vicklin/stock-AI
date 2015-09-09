package cn.iyowei.stockai.page;

import java.io.Serializable;
import java.util.*;


/**
 * 分页信息
 * 第一页从1开始
 *
 * @author badqiu
 */
public class Page<E> implements Serializable, Iterable<E> {

    private static final long serialVersionUID = -5169270853506142512L;

    protected List<E> result;

    protected int pageSize;

    protected int page;

    protected int totalCount = 0;

    public Page(PageQuery p, int totalCount) {
        this(p.getPage(), p.getPageSize(), totalCount);
    }

    public Page(int page, int pageSize, int totalCount) {
        this(page, pageSize, totalCount, new ArrayList<E>(0));
    }

    public Page(int page, int pageSize, int totalCount, List<E> result) {
        if (pageSize <= 0) throw new IllegalArgumentException("[pageSize] must great than zero");
        this.pageSize = pageSize;
        this.page = PageUtils.computePage(page, pageSize, totalCount);
        this.totalCount = totalCount;
        setResult(result);
    }

    /**
     * 当前页包含的数据
     *
     * @return 当前页数据源
     */
    public List<E> getResult() {
        return result;
    }

    public void setResult(List<E> elements) {
        if (elements == null) throw new IllegalArgumentException("'result' must be not null");
        this.result = elements;
    }


    /**
     * 是否是首页（第一页），第一页页码为1
     *
     * @return 首页标识
     */
    public boolean isFirstPage() {
        return getThisPage() == 1;
    }

    /**
     * 是否是最后一页
     *
     * @return 末页标识
     */
    public boolean isLastPage() {
        return getThisPage() >= getLastPage();
    }

    /**
     * 是否有下一页
     *
     * @return 下一页标识
     */
    public boolean isHasNextPage() {
        return getLastPage() > getThisPage();
    }

    /**
     * 是否有上一页
     *
     * @return 上一页标识
     */
    public boolean isHasPreviousPage() {
        return getThisPage() > 1;
    }

    /**
     * 获取最后一页页码，也就是总页数
     *
     * @return 最后一页页码
     */
    public int getLastPage() {
        return PageUtils.computeLastPage(totalCount, pageSize);
    }

    /**
     * 总的数据条目数量，0表示没有数据
     *
     * @return 总数量
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 获取当前页的首条数据的行编码
     *
     * @return 当前页的首条数据的行编码
     */
    public int getThisPageFirstElementNum() {
        return (getThisPage() - 1) * getPageSize() + 1;
    }

    /**
     * 获取当前页的末条数据的行编码
     *
     * @return 当前页的末条数据的行编码
     */
    public int getThisPageLastElementNum() {
        int fullPage = getThisPageFirstElementNum() + getPageSize() - 1;
        return getTotalCount() < fullPage ? getTotalCount() : fullPage;
    }

    /**
     * 获取下一页编码
     *
     * @return 下一页编码
     */
    public int getNextPage() {
        return getThisPage() + 1;
    }

    /**
     * 获取上一页编码
     *
     * @return 上一页编码
     */
    public int getPreviousPage() {
        return getThisPage() - 1;
    }

    /**
     * 每一页显示的条目数
     *
     * @return 每一页显示的条目数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 当前页的页码
     *
     * @return 当前页的页码
     */
    public int getThisPage() {
        return page;
    }

    /**
     * 得到用于多页跳转的页码
     *
     * @return
     */
    public List<Integer> getLinkPages() {
        return PageUtils.generateLinkPages(getThisPage(), getLastPage(), 10);
    }

    /**
     * 得到数据库的第一条记录号
     *
     * @return
     */
    public int getFirstResult() {
        return PageUtils.getFirstResult(page, pageSize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) (result == null ? Collections.emptyList().iterator() : result.iterator());
    }

    public Map<String, Integer> toMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("totalCount", this.getTotalCount());
        map.put("totalPage", this.getLastPage());
        map.put("page", this.getThisPage());
        map.put("pageSize", this.getPageSize());
        return map;
    }

}
