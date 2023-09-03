package com.cheny.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cheny.pojo.Grade;
import com.cheny.service.GradeService;
import com.cheny.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping("/getGrades")
    public Result getGrades() {
        List<Grade> grades = gradeService.getAll();
        return Result.ok(grades);
    }

    @ApiOperation("根据id数组批量删除班级")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("要删除的所有班级的id的JSON集合") @RequestBody List<Integer> ids) {
        gradeService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("添加班级")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("JSON格式的班级对象") @RequestBody Grade grade) {
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("带条件的分页查询")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGradesByPageAndCondition(@ApiParam("页码") @PathVariable("pageNo") Integer pageNo,
                            @ApiParam("页条数") @PathVariable("pageSize") Integer pageSize,
                            @ApiParam("条件") String gradeName) {
        //分页带条件查询
        IPage<Grade> page = new Page<>(pageNo, pageSize);
        //调用服务层
        IPage<Grade> byPageAndCondition = gradeService.getByPageAndCondition(page, gradeName);
        return Result.ok(byPageAndCondition);
    }

}
