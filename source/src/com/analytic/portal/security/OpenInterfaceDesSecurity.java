package com.analytic.portal.security;

import com.yzd.plat.common.util.PropertyManager;

public class OpenInterfaceDesSecurity extends DesSecurity {

	private static final String key=PropertyManager.getInstance("config").getProperty("SecurityKey");
	private static final String value=PropertyManager.getInstance("config").getProperty("SecurityValue");
	
	protected OpenInterfaceDesSecurity() throws Exception {
		super();
	}

	private static OpenInterfaceDesSecurity des;
	
	public static OpenInterfaceDesSecurity getInstance(){
		if(des==null){
			try {
				des=new OpenInterfaceDesSecurity();
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}
		return des;
	}
	
	protected String getkey() {
		
		return key;
	}

	protected String getIv() {
		
		return value;
	}

	public static void main(String[] args){
		String readline= "{\"CMD\":\"3001\",\"devID\":\"31141103551\",\"socketOut_P\":\"8\",\"socketOut_W\":\"607.00\",\"socketOutY_W\":\"234.56\"}";
		try {
			String encodeStr=OpenInterfaceDesSecurity.getInstance().encrypt(readline.getBytes());
			String decodeStr=new String(OpenInterfaceDesSecurity.getInstance().decrypt(encodeStr), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
