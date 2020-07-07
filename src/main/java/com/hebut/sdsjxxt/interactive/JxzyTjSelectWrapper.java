package com.hebut.sdsjxxt.interactive;

import java.io.Serializable;

/**
* @author gaochaochao
* @date 2019年6月3日 上午8:53:10
* @description
*/
public class JxzyTjSelectWrapper  implements Serializable {

	private static final long serialVersionUID = -8646961591106063454L;
	
	private String xuehao;
	
	private Long banjiIds;
	
	private Long jxzyId;

	public String getXuehao() {
		return xuehao;
	}

	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}

	public Long getBanjiIds() {
		return banjiIds;
	}

	public void setBanjiIds(Long banjiIds) {
		this.banjiIds = banjiIds;
	}

	public Long getJxzyId() {
		return jxzyId;
	}

	public void setJxzyId(Long jxzyId) {
		this.jxzyId = jxzyId;
	}
	
}
