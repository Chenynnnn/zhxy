package com.cheny.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cheny.pojo.Teacher;
import com.cheny.service.TeacherService;
import com.cheny.util.MD5;
import com.cheny.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(@PathVariable Integer pageNo,
                              @PathVariable Integer pageSize,
                              Teacher teacher) {
        IPage<Teacher> page = new Page<>(pageNo, pageSize);
        IPage<Teacher> teacherIPage = teacherService.getByPageAndCondition(page, teacher);
        return Result.ok(teacherIPage);
    }

    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher) {
        //将密码加密存储
        teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids) {
        teacherService.removeByIds(ids);
        return Result.ok();
    }

}
