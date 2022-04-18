package com.zwy.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwy.zhxy.pojo.Clazz;
import com.zwy.zhxy.service.ClazzService;
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
@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @ApiOperation("获取所有班级信息")
    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> list = clazzService.list();
        return Result.ok(list);
    }

    //GET
    //	/sms/clazzController/getClazzsByOpr/1/3
    @ApiOperation("根据班级名称模糊分页查询所有Grade信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@ApiParam("分页查询的当前页码") @PathVariable Integer pageNo,
                                 @ApiParam("分页查询的每页的数量") @PathVariable Integer pageSize,
                                 @ApiParam("分页查询的查询条件") Clazz clazz) {
        Map<String, Object> map = new LinkedHashMap<>();
        //分页查询
        Page<Clazz> page = new Page<>(pageNo, pageSize);
        //通过 service 查询
        IPage<Clazz> pageRs = clazzService.getClazzByOpr(page, clazz);
        return Result.ok(pageRs);
    }

    //POST
    //	http://localhost:9001/sms/clazzController/saveOrUpdateClazz
    @ApiOperation("添加或修改班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("JSON格式的Clazz对象") @RequestBody Clazz clazz) {
        boolean b = clazzService.saveOrUpdate(clazz);
        if (!b) {
            return Result.fail().message("添加失败");
        }
        return Result.ok().message("添加成功");
    }

    //DELETE
    //	http://localhost:9001/sms/clazzController/deleteClazz
    @ApiOperation("删除单个或多个Clazz")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@ApiParam("要删除Clazz的id集合") @RequestBody List<Integer> ids) {
        boolean b = clazzService.removeByIds(ids);
        if (!b) {
            return Result.fail().message("删除失败");
        }
        return Result.ok().message("删除成功");
    }


}
