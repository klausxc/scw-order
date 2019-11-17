package com.atguigu.scw.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.service.ProjectService;
import com.atguigu.scw.project.vo.response.ProjectDetailsVo;
import com.atguigu.scw.project.vo.response.ProjectResponseVo;
import com.atguigu.scw.vo.response.ResponseVo;

import lombok.extern.slf4j.Slf4j;

@Controller
public class ProjectController {
	@Autowired
	ProjectService projectService;
	
	
	@GetMapping("/getReturn")
	@ResponseBody
	public ResponseVo<TReturn> getReturn(@RequestParam("id")Integer id){
		TReturn tReturn=projectService.getReturn(id);
		return ResponseVo.ok(tReturn);
	}
	
	
	@GetMapping("/getProjectDetailById")
	@ResponseBody
	public ResponseVo<ProjectDetailsVo> getProjectById(@RequestParam("projectid")Integer id){
		ProjectDetailsVo vo=projectService.getProjectDetailById(id);
		return ResponseVo.ok(vo);
	}
	
	
	
	@GetMapping("/getAllProjects")
	@ResponseBody
	public ResponseVo<List<ProjectResponseVo>> getAllProject() {
		List<TProject> projects=projectService.getAllProject();
		ArrayList<ProjectResponseVo> vos = new ArrayList<>();
		
		for (TProject project : projects) {
			ProjectResponseVo vo = new ProjectResponseVo();
			BeanUtils.copyProperties(project, vo);
			Integer id = project.getId();
			List<TProjectImages> images=projectService.getProjectImags(id);
			for (TProjectImages image : images) {
				if (image.getImgtype()==0) {
					vo.setHeaderImage(image.getImgurl());
				}else {
					List<String> detailsImage = vo.getDetailsImage();
					detailsImage.add(image.getImgurl());
				}
			}
			vos.add(vo);
		}
		return ResponseVo.ok(vos);
	}
}
