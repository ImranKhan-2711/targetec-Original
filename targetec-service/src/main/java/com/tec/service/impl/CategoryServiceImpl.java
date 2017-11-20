package com.tec.service.impl;

import org.springframework.stereotype.Service;

import com.tec.service.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	// @Resource(name="accountDao")
	// AccountDao accountDao;

	public void listOrgParentCategory(String username) {
		// String
		// orgUnitId=accountDao.findByUserName(username).getOrgUnit().getOrgUnitId();
		// return categoryDao.listOrgParentCategory(orgUnitId);
	}

}
