package com.example.demo.config.utils;

import java.util.List;

import com.google.common.collect.Lists;

public class StringUtil extends cn.hutool.core.util.StrUtil{
	private static final char D = ',';
	
	/**
	 * 将1,2,3,a,b,c 转换为List<String>
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> convertList(String str) {
		List<String> list = Lists.newArrayList();
		if (isNotBlank(str)) {
			String[] s = str.split(String.valueOf(D));
			for (String string : s) {
				list.add(string);
			}
		}
		return list;
	}
}
