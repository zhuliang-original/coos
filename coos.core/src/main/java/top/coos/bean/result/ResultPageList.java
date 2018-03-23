package top.coos.bean.result;

public class ResultPageList extends Result {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ResultPageList() {
        super.setDatatype(DataType.LIST);
    }

    // 当前页
    private int currentpage = 1;

    // 总页数
    private int totalpages = 1;

    // 总记录数
    private int totalcount = 1;

    // 每页记录
    private int pagesize = 10;

    // 上一页
    private int uppage = 1;

    // 下一页
    private int nextpage = 1;

    private boolean hasNext;

    public boolean isHasNext() {
        if (currentpage >= totalpages) {
            hasNext = false;
        } else {
            hasNext = true;
        }
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getUppage() {
        uppage = currentpage - 1;
        if (uppage <= 1) {
            uppage = 1;
        }
        return uppage;
    }

    public int getNextpage() {

        nextpage = currentpage + 1;
        if (nextpage >= totalpages) {
            nextpage = totalpages;
        }
        return nextpage;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
        totalpages = 0;
        if (totalcount % pagesize == 0) {
            totalpages = totalcount / pagesize;
        } else {
            totalpages = totalcount / pagesize + 1;
        }
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public void setUppage(int uppage) {
        this.uppage = uppage;
    }

    public void setNextpage(int nextpage) {
        this.nextpage = nextpage;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
