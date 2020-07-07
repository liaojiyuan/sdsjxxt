package com.hebut.sdsjxxt.interactive;

import java.io.Serializable;

/**
* @author gaochaochao
* @date 2019年6月4日 下午8:30:32
* @description
*/
public class JxnrTjSelectWrapper implements Serializable{

	private static final long serialVersionUID = 8302169171041322071L;
	
    private String xuehao;
	
	private Long banjiIds;
	
	private Integer stage;
	
	private Long jxnrId;

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

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public Long getJxnrId() {
		return jxnrId;
	}

	public void setJxnrId(Long jxnrId) {
		this.jxnrId = jxnrId;
	}
	

}
