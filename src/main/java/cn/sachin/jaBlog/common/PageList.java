package cn.sachin.jaBlog.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据
 * @param <T>
 */
@Data
public final class PageList<T> implements Serializable {

    private List<T> resultList;

    private PageConfig pageConfig;

    public PageList() {
    }

    public PageList(List<T> resultList) {
        this.resultList = resultList;
    }

    public PageList(List<T> resultList, PageConfig pageConfig) {
        this.resultList = resultList;
        this.pageConfig = pageConfig;
    }
}
