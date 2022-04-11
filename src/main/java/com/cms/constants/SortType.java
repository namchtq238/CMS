package com.cms.constants;


public enum SortType {
    DEFAULT(1, "DEFAULT"), MOSTPOPULAR(2, "MOSTPOPULAR"),
    MOSTVIEWED(3, "MOSTVIEWED"), LASTESTIDEA(4, "LASTESTIDEA"),
    LASTEDCOMMENT(5, "LASTESTCOMMENT");
    private Integer type;
    private String typeInStr;
    SortType(Integer type, String typeInStr){
        this.type = type;
        this.typeInStr = typeInStr;

    }
    public Integer getValue() {
        return this.type;
    }

    public String getTypeInStr() {
        return typeInStr;
    }

    public static SortType getTypeByValue(Integer value){
        for (SortType type : SortType.values() ){
            if(type.type.equals(value)) return type;
        }
        return null;
    }

}
