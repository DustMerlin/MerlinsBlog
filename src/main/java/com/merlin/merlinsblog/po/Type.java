package com.merlin.merlinsblog.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
//import org.hibernate.validator.constraints.NotBlank;
//此处的校验功能已废弃，需要使用以下依赖才能完成相应的功能
//import javax.validation.constraints.NotBlank; 使用这种写法
//博客地址 caorui.net/blog/111848631920754688.html
//经常查找文档发现，validation-api 只是一套标准，
//而具体是实现是依赖 hibernate-validator 库，所以在引用 validation-api 库的前提下，
//还要引用 hibernate-validator 库才能正常使用，具体的 pom 信息如下
//<dependency>
//<groupId>javax.validation</groupId>
//<artifactId>validation-api</artifactId>
//<version>2.0.1.Final</version>
//</dependency>
//<dependency>
//<groupId>org.hibernate</groupId>
//<artifactId>hibernate-validator</artifactId>
//<version>6.0.13.Final</version>
//</dependency>

@Entity
@Table(name="type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名不能为空")
    private String name;

    // 此处一（类型）对多（博客），在一端使用mappedBy = "type",type被维护，blog 维护
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    public Type(Long id, String name, List<Blog> blogs) {
        this.id = id;
        this.name = name;
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
