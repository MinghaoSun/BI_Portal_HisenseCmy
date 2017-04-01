package com.analytic.portal.common.filter;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * 
 * 得到response中内容
 * @author kezhuang.li 
 * @creation 2015年9月28日
 */
public class SystemLogWrapper extends HttpServletResponseWrapper {
	private CharArrayWriter caw = null;
	public SystemLogWrapper(HttpServletResponse response) {
		super(response);
		caw = new CharArrayWriter();
	}
	public String toString(){
		return caw.toString();
	}
	public PrintWriter getWriter() {
		return new PrintWriter(caw);
	}
	public ServletOutputStream getOutputStream() {
		return new SampleServletOutputStream(caw);
	}

	class SampleServletOutputStream extends ServletOutputStream{
		private CharArrayWriter buffer = null;
		
		public SampleServletOutputStream(CharArrayWriter charArrayWriter){
		  super();
		  buffer = charArrayWriter;
		}
		public void write(int c){
		  buffer.write(c);
		}
	}
}
