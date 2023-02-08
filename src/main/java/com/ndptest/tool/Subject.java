package com.ndptest.tool;

public enum Subject {
    NONE("==========조회할 정보 선택=========="),
    PROFILE("전체 유저 프로필 조회"),
    NOTE("전체 유저 노트 조회"),
    STROKE("전체 유저 스트로크 조회"),
    TAG("전체 유저 태그 조회");

    private final String displayString;

    private Subject(String displayString){
        this.displayString = displayString;
    }

    public String getDisplayString(){
        return displayString;
    }
}
