package com.tec.dao;

import com.tec.model.ResourceMaster;

public interface ResourceMasterDao {

	public Long insert(ResourceMaster resourceMaster);

	public ResourceMaster getResourceMaster(Long id);

}
