package com.zwy.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwy.zhxy.pojo.Grade;
import com.zwy.zhxy.service.GradeService;
import com.zwy.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵文渊
 * @version 1.0
 * @date 2022/4/16 1:23
 */
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    //http://localhost:9001/sms/gradeController/getGrades/1/3
    @ApiOperation("根据年级名称模糊分页查询所有Grade信息")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGradesByPage(@ApiParam("分页查询的当前页码") @PathVariable() Integer pageNo,
                            @ApiParam("分页查询的每页的数量") @PathVariable() Integer pageSize,
                            @ApiParam("分页查询模糊匹配的年级名称") @RequestParam(value = "gradeName", required = false) String gradeName) {
        Map<String, Object> map = new LinkedHashMap<>();
        //分页查询
        Page<Grade> page = new Page<>(pageNo, pageSize);
        //通过 service 查询
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page, gradeName);
        return Result.ok(pageRs);
    }

    //GET
    //http://localhost:9001/sms/gradeController/getGrades
    @ApiOperation("获取所有年级信息")
    @GetMapping("/getGrades")
    public Result getGrades() {
        List<Grade> grades = gradeService.list();
        return Result.ok(grades);
    }

    //POST
    //	http://localhost:9001/sms/gradeController/saveOrUpdateGrade
    @ApiOperation("保存或修改Grade信息，有id属性是修改，没有id是新建")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("JSON格式的Grade对象") @RequestBody Grade grade) {
        boolean b = gradeService.saveOrUpdate(grade);
        if (b) {
            return Result.ok().message("创建成功");
        }
        return Result.fail().message("创建失败");
    }

    //DELETE /sms/gradeController/deleteGrade
    @ApiOperation("删除Grade信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("要删除所有班级的id的JSON集合") @RequestBody List<Integer> ids) {
        boolean b = gradeService.removeByIds(ids);
        if (!b) {
            return Result.fail().message("删除失败");
        }
        return Result.ok().message("删除成功");
    }
}
