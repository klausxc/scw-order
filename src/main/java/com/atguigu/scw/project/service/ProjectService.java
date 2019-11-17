package com.atguigu.scw.project.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.vo.request.ProjectRedisStorageVo;
import com.atguigu.scw.project.vo.response.ProjectDetailsVo;

public interface ProjectService {

	void insertProject(ProjectRedisStorageVo bigVo);

	List<TProject> getAllProject();

	List<TProjectImages> getProjectImags(Integer id);

	ProjectDetailsVo getProjectDetailById(Integer id);

	TReturn getReturn(Integer id);

}
