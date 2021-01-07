package com.merlin.merlinsblog.dao;

import com.merlin.merlinsblog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRespository extends JpaRepository<Type,Long> {

    Type findByName(String name);
}
