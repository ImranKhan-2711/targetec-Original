package com.tec.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tec.dao.ResourceMasterDao;
import com.tec.model.ResourceMaster;
import com.tec.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@Resource(name = "resourceMasterDao")
	private ResourceMasterDao resourceMasterDao;

	public Long updateStudentProfile(String fileName, String extension) {
		String resourcePath = "std_profile/" + fileName;
		ResourceMaster resourceMaster = new ResourceMaster(null, "IMAGE", extension, resourcePath, "account_detail",
				"profile_image", "ACTIVE");
		return resourceMasterDao.insert(resourceMaster);
	}

	public ResourceMaster getResourceMaster(Long id) {
		return resourceMasterDao.getResourceMaster(id);
	}
}
