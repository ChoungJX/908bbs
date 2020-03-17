package com.demo.index.util;

import org.springframework.util.DigestUtils;



public class MD5Util {
	//盐，用于混交md5
	private static final String slat = "908bbs666666666";
	/**
	 * 生成md5
	 * @param seckillId
	 * @return
	 */
	public static String getMD5(String str) {
		String base = str +"/"+slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

}
