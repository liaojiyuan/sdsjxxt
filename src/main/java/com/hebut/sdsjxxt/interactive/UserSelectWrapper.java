package com.hebut.sdsjxxt.interactive;

import java.io.Serializable;

/**
* @author gaochaochao
* @date 2019年6月8日 下午4:08:17
* @description
*/
public class UserSelectWrapper implements Serializable{

	private static final long serialVersionUID = -54853413035969661L;
	
	private String xuehao;
	
	private Long banjiId;
	
	private String name;

	public String getXuehao() {
		return xuehao;
	}

	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}

	public Long getBanjiId() {
		return banjiId;
	}

	public void setBanjiId(Long banjiId) {
		this.banjiId = banjiId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
