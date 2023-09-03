package com.cheny.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cheny.pojo.Clazz;
import com.cheny.pojo.Student;
import com.cheny.service.ClazzService;
import com.cheny.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> students = clazzService.getAll();

        return Result.ok(students);
    }

    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@PathVariable Integer pageNo,
                                 @PathVariable Integer pageSize,
                                 Clazz clazz) {
        IPage<Clazz> page = new Page<>(pageNo, pageSize);
        IPage<Clazz> byPageAndCondition = clazzService.getByPageAndCondition(page, clazz);
        return Result.ok(byPageAndCondition);
    }

    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz) {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> ids) {
        clazzService.removeByIds(ids);
        return Result.ok();
    }

}
