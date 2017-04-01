package com.analytic.portal.test;

import com.analytic.portal.common.util.DESEncode;

public class TestDemo {
	
	public static void main(String[] args) {
		String str="mysql";
		try {
			String encrypt_str=DESEncode.Encrypt(str);
			System.out.println(encrypt_str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
