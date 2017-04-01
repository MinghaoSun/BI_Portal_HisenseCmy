package com.analytic.portal.common.resolver;

import java.io.Serializable;

public class MyResult implements Serializable{

	private static final long serialVersionUID = 6436423589941684173L;

	private boolean result;

	private Object content;

	public MyResult() {
	}

	public MyResult(boolean result, Object content) {
		super();
		this.result = result;
		this.content = content;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public MyResult(boolean result) {
		super();
		this.result = result;
	}
}
