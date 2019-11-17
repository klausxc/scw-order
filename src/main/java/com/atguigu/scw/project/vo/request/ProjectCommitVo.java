package com.atguigu.scw.project.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ProjectCommitVo extends BaseVo {

	
	@ApiModelProperty(value="易付宝企业账号：")
	private String account;
	
	@ApiModelProperty(value="法人身份证号：")
	private String idCard;
	
	@ApiModelProperty(value="项目token临时")
	private String projectToken; // 储存在redis中的key
}
