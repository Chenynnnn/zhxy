package com.cheny.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cheny.pojo.Student;
import com.cheny.service.StudentService;
import com.cheny.util.MD5;
import com.cheny.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> ids) {
        studentService.removeByIds(ids);
        return Result.ok();
    }

    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByPageAndCondition(@PathVariable Integer pageNo,
                                               @PathVariable Integer pageSize,
                                               Student student) {
        IPage<Student> page = new Page<>(pageNo, pageSize);
        IPage<Student> byPageAndCondition = studentService.getByPageAndCondition(page, student);

        return Result.ok(byPageAndCondition);
    }

    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student) {
        //将密码加密存储
        student.setPassword(MD5.encrypt(student.getPassword()));
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

}
