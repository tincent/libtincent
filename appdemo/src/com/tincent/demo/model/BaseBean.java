/**
 * 
 */
package com.tincent.demo.model;

import java.io.Serializable;

/**
 * 数据bean基类
 * 
 * @author hui.wang
 * @date 2015.3.25
 * 
 */
public class BaseBean implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	// 数据状态
	public String code;
	// 数据描述
	public String msg;
}
