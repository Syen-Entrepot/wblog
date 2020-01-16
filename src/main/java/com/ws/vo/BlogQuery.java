package com.ws.vo;

/**
 * @author wusen
 * @date 2019/11/4 0004-下午 17:12
 */
public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommend;///是boolean不是Boolean，不需要大写

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
