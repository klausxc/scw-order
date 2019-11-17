package com.atguigu.scw.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.atguigu.scw.common.util.ScwAppUtils;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.service.ProjectService;
import com.atguigu.scw.project.utils.OSSTemplate;
import com.atguigu.scw.project.vo.request.ProjectCommitVo;
import com.atguigu.scw.project.vo.request.ProjectRedisStorageVo;
import com.atguigu.scw.project.vo.request.ProjectStep1Vo;
import com.atguigu.scw.project.vo.request.ProjectStep2Vo;
import com.atguigu.scw.project.vo.response.ProjectResponseVo;
import com.atguigu.scw.vo.response.ResponseVo;
import com.atguigu.scw.vo.response.UserLoginVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/project")
@Api(tags="项目模块")
@Slf4j
public class ProjectCreateController {
	
	@Autowired
	OSSTemplate oSSTemplate;
	@Autowired
	StringRedisTemplate redisTemplate;
	@Autowired
	ProjectService projectService;
	
	
	@PostMapping("/ProjectStep3")
	@ApiOperation("确认信息")
	public ResponseVo<Object> ProjectStep3(ProjectCommitVo commitVo){
		if (StringUtils.isEmpty(commitVo.getAccessToken())) {
			return ResponseVo.fail("请登录后再发起项目");
		}
		String userLoginVo = redisTemplate.opsForValue().get(commitVo.getAccessToken());
		if (StringUtils.isEmpty(userLoginVo)) {
			return ResponseVo.fail("登录信息已过期，请重新登录");
		}
		String token = commitVo.getProjectToken();
		ProjectRedisStorageVo bigVo = ScwAppUtils.changeJsonToObject(token,ProjectRedisStorageVo.class, redisTemplate);
		BeanUtils.copyProperties(commitVo, bigVo);
		projectService.insertProject(bigVo);
		redisTemplate.delete(token);
		return ResponseVo.ok("项目创建成功");
	}
	
	
	@PostMapping("/ProjectStep2")
	@ApiOperation("回报设置")
	public ResponseVo<ProjectRedisStorageVo> ProjectStep2(@RequestBody List<ProjectStep2Vo> step2Vo){
		if (CollectionUtils.isEmpty(step2Vo)) {
			return ResponseVo.fail("请登录后再发起项目");
		}
		ProjectStep2Vo vo = step2Vo.get(0);
		String token = vo.getProjectToken();
		String userLoginVo = redisTemplate.opsForValue().get(token);
		if (StringUtils.isEmpty(userLoginVo)) {
			return ResponseVo.fail("登录信息已过期，请重新登录");
		}
		ArrayList<TReturn> list = new ArrayList<>();
		for (ProjectStep2Vo projectStep2Vo : step2Vo) {
			TReturn tReturn = new TReturn(); // 提取外面是否可以
			BeanUtils.copyProperties(projectStep2Vo, tReturn);
			list.add(tReturn);
		}
		System.out.println("list"+list);
		ProjectRedisStorageVo bigVo = ScwAppUtils.changeJsonToObject(token, ProjectRedisStorageVo.class, redisTemplate);
		bigVo.setReturns(list);
		ScwAppUtils.saveVoToRedis(token, bigVo, redisTemplate);
		return ResponseVo.ok(bigVo);
	}
	
	@PostMapping("/ProjectStep1")
	@ApiOperation("提交项目信息")
	public ResponseVo<ProjectRedisStorageVo> ProjectStep1(ProjectStep1Vo step1Vo){
		if (StringUtils.isEmpty(step1Vo.getAccessToken())) {
			return ResponseVo.fail("请登录后再发起项目");
		}
		String userLoginVo = redisTemplate.opsForValue().get(step1Vo.getAccessToken());
		if (StringUtils.isEmpty(userLoginVo)) {
			return ResponseVo.fail("登录信息已过期，请重新登录");
		}
		String projectToken = step1Vo.getProjectToken();
		ProjectRedisStorageVo bigVo = ScwAppUtils.changeJsonToObject(projectToken, ProjectRedisStorageVo.class, redisTemplate);
		BeanUtils.copyProperties(step1Vo, bigVo);
		ScwAppUtils.saveVoToRedis(projectToken, bigVo, redisTemplate);
		
		return ResponseVo.ok(bigVo);
	}
	
	
	@PostMapping("/initProject")
	@ApiOperation("同意协议")
	public ResponseVo<ProjectRedisStorageVo> initProject(String accessToken){
		if (StringUtils.isEmpty(accessToken)) {
			return ResponseVo.fail("请登录后再发起项目");
		}
		String userLoginVo = redisTemplate.opsForValue().get(accessToken);
		if (StringUtils.isEmpty(userLoginVo)) {
			return ResponseVo.fail("登录信息已过期，请重新登录");
		}
		UserLoginVo loginVo = JSON.parseObject(userLoginVo, UserLoginVo.class);
		ProjectRedisStorageVo bigVo = new ProjectRedisStorageVo();
		bigVo.setAccessToken(accessToken);
		bigVo.setMemberid(loginVo.getId());
		String projectToken="project:create:token:"+UUID.randomUUID().toString().replace("-", "");
		bigVo.setProjectToken(projectToken);
		
		ScwAppUtils.saveVoToRedis(projectToken, bigVo, redisTemplate);
		return ResponseVo.ok(bigVo);
	}
	
	
	@PostMapping("/uploadFile")
	@ApiOperation("上传文件")
	public ResponseVo<Object> uploadFile(MultipartFile[] files)  {
		if (ArrayUtils.isEmpty(files)) {
			return ResponseVo.fail("文件为空，请上传文件");
		}
		ArrayList<String> list = new ArrayList<>();
		int count=0;
		for (MultipartFile file : files) {
			String path = oSSTemplate.uploadFile(file);
			if (path==null) {
				count++;
			}
			list.add(path);
		}
		log.debug("成功：{}个,失败:{}个",files.length,count);
		return ResponseVo.ok(list);
	}
	
}
