package io.ron.common.exchange;

import java.util.List;

public class Page<E> {

    private int curPage;

    private int perPage;

    private int totalPage;

    private int totalCount;

    private List<E> items;

    private Page() {}

    public static Page of(List<?> items, int totalCount, int curPage, int perPage) {
        Page page = new Page<>();
        page.setItems(items);
        page.setCurPage(curPage);
        page.setPerPage(perPage);
        page.setTotalCount(totalCount);
        page.setTotalPage((int) Math.ceil((double) totalCount/perPage));
        return page;
    }

    public long getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Page{" +
                "curPage=" + curPage +
                ", perPage=" + perPage +
                ", totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                ", items=" + items +
                '}';
    }
}
