package com.ws.service.impl;

import com.ws.dao.TypeRepository;
import com.ws.exception.NotFoundException;
import com.ws.pojo.Type;
import com.ws.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wusen
 * @date 2019/10/31 0031-下午 14:01
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional//放进事务中
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).orElse(null);
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        /*Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable =PageRequest.of(0,size,sort);*/
        return typeRepository.findTop();
    }

    @Override
    public List<Type> ListType() {
        return typeRepository.findAll();
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);

    }
}
