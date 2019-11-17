package com.atguigu.scw.project.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BaseVo {
	@ApiModelProperty(value="用户登录token")
	private String accessToken; // 储存在redis中的key
	
}
