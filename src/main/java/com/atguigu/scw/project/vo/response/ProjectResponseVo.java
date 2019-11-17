package com.atguigu.scw.project.vo.response;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TReturn;

import lombok.Data;

@Data
public class ProjectResponseVo implements Serializable {
	private Integer id;
	private String name;
	private String remark;
	private Long money;
	private Integer day;
	private Byte status;
	private String deploydate;
	private Long supportmoney;
	private Integer supporter;
	private Integer completion;
	private Integer memberid;
	private String createdate;
	private Integer follower;
	private String headerImage;
	private List<String> detailsImage = new ArrayList<>();
	// 项目的所有支持档位
	private List<TReturn> returns = new ArrayList<>();
	
	
}
