package com.example.demo.config.common;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.example.demo.config.utils.DateUtils;


public class CodeGen {
	static Lock lock=new ReentrantLock();
    //同步获取编号
	public  static String getCode(String max,String prefix) {
		try {
			lock.lock();//加锁
			String code=DateUtils.formatDateTime(new Date());
			String year=code.substring(2, 4);
			String month=code.substring(5, 7);
			String day=code.substring(8, 10);
			String hour=code.substring(11, 13);
			if("001".equals(max)) {
				return prefix+year+month+day+hour+max;
			}else {
				String time=max.substring(2, 10);
				//如果两个时间一样则加1
				if(time.equals(year+month+day+hour)) {
					max=max.substring(max.length()-2,max.length());
					Integer num=new Integer(max)+1;
					if(num<10) {
						return prefix+year+month+day+hour+"00"+num;
					}else if(num<100) {
						return prefix+year+month+day+hour+"0"+num;
					}else {
						return prefix+year+month+day+hour+num;
					}
				}else {//不一样则直接加上001
					return prefix+year+month+day+hour+"001";
				}
			}
			
		} finally {
			lock.unlock();//释放所，避免使用synchronized关键字，提高效率
		}
		
	};
	public static void main(String[] args) {
		System.out.println(getCode("001","CK"));
	}
}
