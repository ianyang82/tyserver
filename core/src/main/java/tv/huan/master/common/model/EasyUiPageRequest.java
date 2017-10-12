package tv.huan.master.common.model;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2015/1/23
 * Time: 17:27
 * To change this template use File | Settings | File Templates
 */
public class EasyUiPageRequest implements java.io.Serializable {
    private static final long serialVersionUID = 7232798260610351343L;
    private int page; //当前页,名字必须为page
    private int rows; //每页大小,名字必须为rows
    private String sort; //排序字段
    private String order; //排序规则
    private int firstResult;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getFirstResult() {
        return (page - 1) * rows;
    }
}
