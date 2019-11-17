package com.atguigu.scw.project.vo.request;

import java.util.List;

import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TReturn;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class ProjectRedisStorageVo {
	
	@ApiModelProperty(value="项目token临时")
	private String projectToken; // 储存在redis中的key
	
	@ApiModelProperty(value="用户登录token")
	private String accessToken; // 储存在redis中的key
	
	@ApiModelProperty(value="会员id")
	private Integer memberid;//会员id 
	
	@ApiModelProperty(value="分类信息")
	private List<Integer> typeids;
	
	@ApiModelProperty(value="分类标签")
	private List<Integer> tagsids;

	@ApiModelProperty(value="项目名字")
	private String name;

	@ApiModelProperty(value="项目简介")
	private String remark;

//	@ApiModelProperty(value="筹集金额")
	private Long money;
	
//	@ApiModelProperty(value="筹集天数")
	private Integer day;
	
	@ApiModelProperty(value="项目头图")
	private String headurl;
	
	@ApiModelProperty(value="项目详情图")
	private List<String> detailurls;
	
	@ApiModelProperty(value="发起人信息")
	private TProjectInitiator initiator;
	
	@ApiModelProperty(value="项目回报")
	private List<TReturn> returns;
	
	@ApiModelProperty(value="易付宝企业账号：")
	private String account;
	
	@ApiModelProperty(value="法人身份证号：")
	private String idCard;
	
}
