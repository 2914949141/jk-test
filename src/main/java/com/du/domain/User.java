//package com.du.domain;
//
//import cn.afterturn.easypoi.excel.annotation.Excel;
//import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class User {
//
//    @Excel(name = "序号", orderNum = "0", format = "isAddIndex")
//    private Integer index = 1;
//
//    @Excel(name = "用户id *")
//    private String id;
//
//    @Excel(name = "用户id *")
//    private String username;
//
//    @Excel(name = "性别 *", replace = {"男_1", "女_0"})
//    private Integer age;
//
//    @ExcelCollection(name = "商品信息")
//    private List<Goods> goodsList;
//
//    public User(String id, String username) {
//        this.id = id;
//        this.username = username;
//    }
//}
