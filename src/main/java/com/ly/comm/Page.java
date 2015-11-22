package com.ly.comm;

import org.nutz.dao.pager.Pager;

public class Page extends Pager {

	/**
	 * pageCurrent=2&pageSize
	 */
	private static final long serialVersionUID = 232222222222333L;

	private int pageCurrent;

	private int pageSize = 30;


	public int getPageCurrent() {
		return super.getPageNumber();
	}

	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
		super.setPageNumber(pageCurrent);
	}

}
