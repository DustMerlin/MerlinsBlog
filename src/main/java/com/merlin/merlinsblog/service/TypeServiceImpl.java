package com.merlin.merlinsblog.service;

import com.merlin.merlinsblog.Exception.NotFoundBlogException;
import com.merlin.merlinsblog.dao.TypeRespository;
import com.merlin.merlinsblog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeRespository typeRespository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRespository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRespository.findById(id).orElse(null);
    }

    @Override
    public List<Type> listType() {
        return typeRespository.findAll();
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRespository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRespository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRespository.findById(id).orElse(null);
        if(t == null){
            throw new NotFoundBlogException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRespository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRespository.deleteById(id);
    }
}
