package com.cheny.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheny.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
