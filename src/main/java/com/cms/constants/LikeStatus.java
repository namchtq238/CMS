package com.cms.constants;

public enum LikeStatus {
    LIKE(2, "LIKE"), DISLIKE(3, "DISLIKE");
    private Integer type;
    private String typeInStr;
    LikeStatus(Integer type, String typeInStr){
        this.type = type;
        this.typeInStr = typeInStr;

    }
    public Integer getValue() {
        return this.type;
    }

    public String getTypeInStr() {
        return typeInStr;
    }

    }
