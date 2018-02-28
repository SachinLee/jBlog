package cn.sachin.jaBlog.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageConfig implements Serializable {

    private int pageNum = 1; //当前页码数

    private int pageSize = Constant.DEFAULT_PAGESIZE.intValue();

    private long rowCount; //总条目数

    private int pageCount; //总页数


    public int getPageCount() {
        int count = (int) this.rowCount / pageSize;
        return this.rowCount % pageSize == 0L ? count : count + 1;
    }

    @JsonIgnore
    public int getFirst() {
        return (pageNum - 1) * pageSize;
    }
}
