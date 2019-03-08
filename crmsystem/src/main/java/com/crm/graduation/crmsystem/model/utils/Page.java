package com.crm.graduation.crmsystem.model.utils;

/**
 * 分页类
 */
public class Page {

    /**
     * 开始索引
     */
    private Integer startIndex;

    private Integer showCount;

    public Integer getShowCount() {
        return showCount;
    }

    public void setShowCount(Integer showCount) {
        this.showCount = showCount;
    }

    /**
     * 总数量
     */
    private Integer allCount;

    /**
     * 总页数
     */
    private Integer allPage;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 每页显示的数据数
     */
    private Integer currentCount;

    public Integer getStartIndex() {
        startIndex = (getCurrentPage()-1)*getShowCount();
        if(startIndex<0)
            startIndex = 0;
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getAllPage() {
        if(allCount%5==0){
            allPage = allCount/5;
        }else{
            allPage=allCount/5+1;
        }
        return allPage;
    }

    public Page(){
        this.showCount=5;
    }

    public void setAllPage(Integer allPage) {
        this.allPage = allPage;
    }

    public Integer getCurrentPage() {
        if(currentPage<=0)
            currentPage = 1;
        if(currentPage>getAllPage())
            currentPage = getAllPage();
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Integer currentCount) {
        this.currentCount = currentCount;
    }
}
