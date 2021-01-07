package com.merlin.merlinsblog.service;

import com.merlin.merlinsblog.Exception.NotFoundBlogException;
import com.merlin.merlinsblog.dao.BlogRespository;
import com.merlin.merlinsblog.po.Blog;
import com.merlin.merlinsblog.po.Type;
import com.merlin.merlinsblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRespository blogRespository;


    @Override
    public Blog getBlog(Long id) {
        return blogRespository.findById(id).orElse(null);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRespository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                //非空判断
                if("".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;

            }
        },pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRespository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRespository.findById(id).orElse(null);
        if(b == null){
            throw new NotFoundBlogException("该博客不存在");
        }
//      把blog深拷贝给b
        BeanUtils.copyProperties(b,blog);
        return blogRespository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRespository.deleteById(id);
    }
}
