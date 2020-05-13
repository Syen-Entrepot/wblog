package com.ws.dao;

import com.ws.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wusen
 * @date 2019/10/31 0031-下午 14:04
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);

    //@Query("select t from Type t")//自定义查询语句
    @Query(nativeQuery = true,value = "SELECT distinct t.id,t.name FROM w_type t LEFT JOIN w_blog b on b.type_id=t.id WHERE b.polished = true ORDER BY t.id desc LIMIT 100")
    List<Type> findTop();
}
