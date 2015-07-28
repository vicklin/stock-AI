package cn.iyowei.stockai.page;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    private PageUtils() {
    }

    public static int getFirstResult(int pageNumber, int pageSize) {
        if (pageSize <= 0) throw new IllegalArgumentException("[pageSize] must great than zero");
        return (pageNumber - 1) * pageSize;
    }

    public static List<Integer> generateLinkPages(int currentPage, int lastPage, int count) {
        int avg = count / 2;

        int startPage = currentPage - avg;
        if (startPage <= 0) {
            startPage = 1;
        }

        int endPage = startPage + count - 1;
        if (endPage > lastPage) {
            endPage = lastPage;
        }

        if (endPage - startPage < count) {
            startPage = endPage - count;
            if (startPage <= 0) {
                startPage = 1;
            }
        }

        java.util.List<Integer> result = new ArrayList<Integer>();
        for (int i = startPage; i <= endPage; i++) {
            result.add(new Integer(i));
        }
        return result;
    }

    public static int computeLastPage(int totalElements, int pageSize) {
        int result = totalElements % pageSize == 0 ?
                totalElements / pageSize
                : totalElements / pageSize + 1;
        if (result <= 1)
            result = 1;
        return result;
    }

    public static int computePage(int pageNum, int pageSize, int totalElements) {
        if (pageNum <= 1) {
            return 1;
        }
        if (Integer.MAX_VALUE == pageNum
                || pageNum > computeLastPage(totalElements, pageSize)) { //last page
            return computeLastPage(totalElements, pageSize);
        }
        return pageNum;
    }
}
