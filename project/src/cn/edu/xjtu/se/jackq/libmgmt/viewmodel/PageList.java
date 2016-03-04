package cn.edu.xjtu.se.jackq.libmgmt.viewmodel;


import java.util.ArrayList;
import java.util.List;

public class PageList<Item> {

    private int currentPage;
    private int totalPage;
    private List<Item> items;
    private int itemsPerPage;

    public PageList() {
        this.items = new ArrayList<>();
    }

    public PageList(List<Item> list) {

        this.items = list;
    }

    public int getSize() {
        return this.items.size();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
