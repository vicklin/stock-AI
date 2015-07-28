package cn.iyowei.stockai.page;

import cn.iyowei.stockai.util.SqlUtils;

/**
 * 分页查询对象
 *
 * @author badqiu
 */
public class PageQuery implements java.io.Serializable {
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 999;
    private static final long serialVersionUID = -8000900575354501298L;
    /**
     * 页数
     */
    private int page;
    /**
     * 分页大小
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 排序的多个列,如: username desc
     */
    private String sortColumns;

    public PageQuery() {
    }

    public PageQuery(int pageSize) {
        this.pageSize = pageSize;
    }

    public PageQuery(PageQuery query) {
        this.page = query.page;
        this.pageSize = query.pageSize;
    }

    public PageQuery(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public String toString() {
        return "page:" + page + ",pageSize:" + pageSize;
    }

    public String getSortColumns() {
        return sortColumns;
    }

    /**
     * 排序的列,可以同时多列,使用逗号分隔,如 username desc,age asc
     *
     * @return
     */
    public void setSortColumns(String sortColumns) {
        checkSortColumnsSqlInjection(sortColumns);
        if (sortColumns != null && sortColumns.length() > 200) {
            throw new IllegalArgumentException("sortColumns.length() <= 200 must be true");
        }
        this.sortColumns = sortColumns;
    }

    /**
     * 检测sql注入
     *
     * @param sortColumns
     */
    private void checkSortColumnsSqlInjection(String sortColumns) {
        if (sortColumns == null) return;
        if (SqlUtils.hasInjection(sortColumns)) {
            throw new IllegalArgumentException("sortColumns:" + sortColumns + " has SQL Injection risk");
        }
    }

}
