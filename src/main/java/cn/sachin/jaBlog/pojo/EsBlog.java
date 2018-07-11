package cn.sachin.jaBlog.pojo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

@Document(indexName = "blog", type = "blog")
@XmlRootElement   //MediaType 转为 xml
@Data
public class EsBlog implements Serializable {

    @Id
    private String id;

    @Field(index = FieldIndex.not_analyzed)
    private String blogId; //不做全文检索

    private String title;

    private String content;

    @Field(index = FieldIndex.not_analyzed)
    private String username;

    @Field(index = FieldIndex.not_analyzed)
    private String avatar;

    @Field(index = FieldIndex.not_analyzed)
    private Timestamp createTime;

    @Field(index = FieldIndex.not_analyzed)
    private Integer readSize = 0;

    @Field(index = FieldIndex.not_analyzed)
    private Integer voteSize = 0;

    private String tags;

    protected EsBlog(){}

    public EsBlog(Blog blog) {
        this.blogId = blog.getId();
        this.avatar = blog.getUser().getAvatar();
        this.content = blog.getContent();
        this.readSize = blog.getReadSize();
        this.title = blog.getTitle();
        this.tags = blog.getTags();
        this.username = blog.getUser().getLoginName();
        this.voteSize = blog.getVoteSize();
        this.createTime = blog.getCreateTime();
    }

    public void update(Blog blog) {
        this.blogId = blog.getId();
        this.avatar = blog.getUser().getAvatar();
        this.content = blog.getContent();
        this.readSize = blog.getReadSize();
        this.title = blog.getTitle();
        this.tags = blog.getTags();
        this.username = blog.getUser().getLoginName();
        this.voteSize = blog.getVoteSize();
        this.createTime = blog.getCreateTime();
    }

}
