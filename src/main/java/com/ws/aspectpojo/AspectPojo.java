package com.ws.aspectpojo;

import java.util.Arrays;

/**
 * @author wusen
 * @date 2019/10/28 0028-下午 15:10
 */
public class AspectPojo {
    private String url;
    private String ip;
    private String classMethod;
    private Object[] args;

    public AspectPojo(String url, String ip, String classMethod, Object[] args) {
        this.url = url;
        this.ip = ip;
        this.classMethod = classMethod;
        this.args = args;
    }

    @Override
    public String toString() {
        return "aspectPojo{" +
                "url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
