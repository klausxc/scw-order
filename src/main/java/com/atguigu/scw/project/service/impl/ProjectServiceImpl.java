package com.atguigu.scw.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.common.util.AppDateUtils;
import com.atguigu.scw.project.bean.TMemberProjectFollow;
import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectImagesExample;
import com.atguigu.scw.project.bean.TProjectInitiator;
import com.atguigu.scw.project.bean.TProjectInitiatorExample;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.bean.TReturnExample;
import com.atguigu.scw.project.mapper.TMemberProjectFollowMapper;
import com.atguigu.scw.project.mapper.TProjectImagesMapper;
import com.atguigu.scw.project.mapper.TProjectInitiatorMapper;
import com.atguigu.scw.project.mapper.TProjectMapper;
import com.atguigu.scw.project.mapper.TProjectTagMapper;
import com.atguigu.scw.project.mapper.TProjectTypeMapper;
import com.atguigu.scw.project.mapper.TReturnMapper;
import com.atguigu.scw.project.service.ProjectService;
import com.atguigu.scw.project.vo.request.ProjectRedisStorageVo;
import com.atguigu.scw.project.vo.response.ProjectDetailsVo;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Return;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	TMemberProjectFollowMapper memberProjectMapper;
	@Autowired
	TProjectMapper projectMapper;
	@Autowired
	TProjectTypeMapper typeMapper;
	@Autowired
	TProjectTagMapper tagMapper;
	@Autowired
	TProjectImagesMapper imagesMapper;
	@Autowired
	TProjectInitiatorMapper initiatorMapper;
	@Autowired
	TReturnMapper returnMapper;
	
	@Override
	public void insertProject(ProjectRedisStorageVo bigVo) {
		TProject project = new TProject();
		project.setStatus(0+"");
		project.setSupportmoney(0L);
		project.setSupporter(0);
		project.setCompletion(0);
		project.setCreatedate(AppDateUtils.getFormatTime());
		project.setFollower(0);
		BeanUtils.copyProperties(bigVo, project);
		log.debug("项目是:{}",project);
		projectMapper.insertSelective(project);
		System.out.println(project.getId());
		Integer projectid = project.getId();
		memberProjectMapper.insertSelective(new TMemberProjectFollow(null,projectid,bigVo.getMemberid()));
		typeMapper.batchInsert(projectid,bigVo.getTypeids());
		tagMapper.batchInsert(projectid,bigVo.getTagsids());
		
		ArrayList<TProjectImages> list = new ArrayList<>();
		//头部信息
		String headurl = bigVo.getHeadurl();
		list.add(new TProjectImages(null,projectid,headurl,(byte) 0));
		//尾部图片
		for (String detailurl : bigVo.getDetailurls()) {
			list.add(new TProjectImages(null, projectid, detailurl, (byte) 1));
		}
		imagesMapper.batchInsert(list);
		
		TProjectInitiator initiator = bigVo.getInitiator();
		initiator.setProjectid(projectid);
		initiatorMapper.insertSelective(initiator);
		
		List<TReturn> returns = bigVo.getReturns();
		for (TReturn tReturn : returns) {
			tReturn.setProjectid(projectid);
			System.out.println("treturn"+tReturn);
			returnMapper.insertSelective(tReturn);
		}
		
	}

	@Override
	public List<TProject> getAllProject() {
		return projectMapper.selectByExample(null);
	}

	@Override
	public List<TProjectImages> getProjectImags(Integer id) {
		TProjectImagesExample example=new TProjectImagesExample();
		example.createCriteria().andProjectidEqualTo(id);
		List<TProjectImages> list = imagesMapper.selectByExample(example);
		return list;
	}

	@Override
	public ProjectDetailsVo getProjectDetailById(Integer id) {
		TProject tProject = projectMapper.selectByPrimaryKey(id);
		ProjectDetailsVo vo = new ProjectDetailsVo();
		BeanUtils.copyProperties(tProject, vo);
		// 图片详情
		TProjectImagesExample example=new TProjectImagesExample();
		example.createCriteria().andProjectidEqualTo(id);
		List<TProjectImages> images = imagesMapper.selectByExample(example);
		List<String> list = vo.getDetailsImage();
		for (TProjectImages image : images) {
			if (image.getImgtype()==1) {
				list.add(image.getImgurl());
			}else {
				vo.setHeaderImage(image.getImgurl());
			}
		}
		// 个人信息
		TProjectInitiatorExample example2 =new TProjectInitiatorExample();
		example2.createCriteria().andProjectidEqualTo(id);
		List<TProjectInitiator> list2 = initiatorMapper.selectByExample(example2);
		if (!CollectionUtils.isEmpty(list2)) {
			TProjectInitiator initiator = list2.get(0);
			vo.setInitiator(initiator);
		}
		TReturnExample example3=new TReturnExample();
		//回报
		example3.createCriteria().andProjectidEqualTo(id);
		List<TReturn> list3 = returnMapper.selectByExample(example3);
		vo.setReturns(list3);
		return vo;
	}

	@Override
	public TReturn getReturn(Integer id) {
		return returnMapper.selectByPrimaryKey(id);
	}

}
