package com.sample.unimedical.util;

import java.util.HashMap;

public class DataMapper {

    public static HashMap<String,String> getSidoMap() {

        HashMap<String, String> sidoName = new HashMap<>();

        sidoName.put("서울특별시", "서울");
        sidoName.put("서울시", "서울");
        sidoName.put("경기도", "경기");
        sidoName.put("인천광역시", "인천");
        sidoName.put("대전광역시", "대전");
        sidoName.put("대구광역시", "대구");
        sidoName.put("부산광역시", "부산");
        sidoName.put("울산광역시", "울산");
        sidoName.put("광주광역시", "광주");
        sidoName.put("경상남도", "경남");
        sidoName.put("경상북도", "경북");
        sidoName.put("전라남도", "전남");
        sidoName.put("전라북도", "전북");
        sidoName.put("충청남도", "충남");
        sidoName.put("충청북도", "충북");
        sidoName.put("세종특별자치시", "세종시");
        sidoName.put("제주특별자치도", "제주");

        return sidoName;
    }
}
