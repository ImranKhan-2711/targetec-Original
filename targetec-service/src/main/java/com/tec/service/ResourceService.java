package com.tec.service;

import com.tec.model.ResourceMaster;

public interface ResourceService {

	public Long updateStudentProfile(String fileName, String extension);

	public ResourceMaster getResourceMaster(Long id);

}
