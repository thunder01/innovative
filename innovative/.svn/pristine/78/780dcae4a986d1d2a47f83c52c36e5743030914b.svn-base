package com.innovative.utils;

/**
 * 分页工具
 */
public class PageInfo {

    private int currentPageNum = 1;//当前传递的第几页
    private int pageSize = 10;//每页显示多少条数
    private int totalSize;//总条数
    private int startIndex;//从第几条开始查

    /**
     * 获取从第几条开始offset
     *
     * @return
     */
    public int getStartIndex() {
        this.startIndex = (this.currentPageNum - 1) * this.pageSize;
        return startIndex;
    }

    public PageInfo() {
    }

    public PageInfo(int currentPageNum, int pageSize, int totalSize, int startIndex) {
        this.currentPageNum = currentPageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.startIndex = startIndex;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
