package com.atguigu.scw.project.vo.request;

import java.util.List;

import com.atguigu.scw.project.bean.TProjectInitiator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ProjectStep1Vo extends BaseVo{
	
	@ApiModelProperty(value="项目token临时")
	private String projectToken; // 储存在redis中的key
	
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
}
