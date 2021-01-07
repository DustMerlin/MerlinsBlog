package com.merlin.merlinsblog.dao;

import com.merlin.merlinsblog.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlogRespository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
//    JpaRepository<Blog,Long> 按照jpa的基本语法可以实现普通的增删改查
//    JpaSpecificationExecutor<Blog>该类可实现组合查询
}
