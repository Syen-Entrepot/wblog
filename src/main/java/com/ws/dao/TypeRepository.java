package com.ws.dao;

import com.ws.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Syen
 * @date 2019/10/31 0031-下午 14:04
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);

}
