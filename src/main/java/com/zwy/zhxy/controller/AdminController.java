package com.zwy.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwy.zhxy.pojo.Admin;
import com.zwy.zhxy.pojo.Student;
import com.zwy.zhxy.service.AdminService;
import com.zwy.zhxy.util.MD5;
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
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //GET
    //	http://localhost:9001/sms/adminController/getAllAdmin/1/3
    @ApiOperation("根据管理员名称模糊分页查询所有Student信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@PathVariable Integer pageNo,
                              @PathVariable Integer pageSize,
                              @ApiParam("分页查询模糊匹配的管理员名称") String adminName) {
        Map<String, Object> map = new LinkedHashMap<>();
        //分页查询
        Page<Admin> page = new Page<>(pageNo, pageSize);
        //通过 service 查询
        IPage<Admin> pageRs = adminService.getClazzByOpr(page, adminName);
        return Result.ok(pageRs);
    }

    //POST
    //	http://localhost:9001/sms/adminController/saveOrUpdateAdmin
    @ApiOperation("保存或删除Admin")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@ApiParam("JSON格式的admin") @RequestBody Admin admin) {
        Integer id = admin.getId();
        if (null == id || 0 == id) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        boolean b = adminService.saveOrUpdate(admin);
        if (b) {
            return Result.ok().message("操作成功");
        }
        return Result.fail().message("操作失败");
    }

    //DELETE
    //	http://localhost:9001/sms/adminController/deleteAdmin
    @ApiOperation("删除一个或多个Admin")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@ApiParam("要删除admin的id") @RequestBody List<Integer> ids) {
        boolean b = adminService.removeByIds(ids);
        if (b) {
            return Result.ok().message("删除成功");
        }
        return Result.fail().message("删除失败");
    }
}
