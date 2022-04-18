package com.zwy.zhxy.pojo;

import lombok.Data;

/**
 * @author 赵文渊
 * @version 1.0
 * @date 2022/4/16 1:19
 */
@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
