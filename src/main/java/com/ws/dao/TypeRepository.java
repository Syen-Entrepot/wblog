package com.ws.dao;

import com.ws.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wusen
 * @date 2019/10/31 0031-下午 14:04
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);

    @Query("select t from Type t")//自定义查询语句
    List<Type> findTop(Pageable pageable);

}
