package com.ws.util;

/**
 * @author Syen
 * @date 2019/11/5 0005-下午 15:14
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串转集合
 *
 */
public class StringToList {
    public List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids !=null){
            String[] idarray = ids.split(",");
            for(int i=0; i<idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
