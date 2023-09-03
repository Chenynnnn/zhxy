package com.cheny.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 班级信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_clazz")
public class Clazz {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;     //班级编号
    private String name;    //班级名称
    private Integer number;     //班级人数
    private String introducation;    //班级介绍
    private String headmaster;      //班主任姓名
    private String telephone;       //班主任电话
    private String gradeName;       //班级年级
}
