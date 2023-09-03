package com.cheny.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_admin")
public class Admin {
    @TableId(value = "id", type = IdType.AUTO)      //设置id主键自增
    private Integer id;     //id主键
    private String name;    //用户名
    private Character gender;   //性别
    private String password;    //密码
    private String email;       //邮箱
    private String telephone;   //电话号码
    private String address;     //住址
    private String portraitPath;    //头像图片路径
}
