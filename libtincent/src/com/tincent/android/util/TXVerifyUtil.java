/**
 * All Rights Reserved by tincent.com
 */
package com.tincent.android.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.TextView;

/**
 * 听讯科技：正测表达验证工具类
 * 
 * @author hui.wang
 * @date 2015.3.16
 * 
 */
public class TXVerifyUtil {
	static boolean flag = false;
	static String regex = "";

	/**
	 * 验证非空
	 * 
	 * @return
	 */
	public static boolean isEmpty(TextView w, String displayStr) {
		if (TXStringUtils.empty(w.getText().toString().trim())) {
			w.setError(displayStr + "不能为空！");
			w.setFocusable(true);
			w.requestFocus();
			return true;
		}
		return false;
	}
	
	/**
	 * 验证手机号码
	 * 
	 * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
	 * 联通号码段:130、131、132、136、185、186、145 电信号码段:133、153、180、189
	 * 
	 * @param cellphone
	 * @return
	 */
	public static boolean checkCellphone(TextView w) {
		if (!TXRegexUtil.isMobileNumber(w.getText().toString().trim())) {
			w.setError("手机号码为11位数字！");
			w.setFocusable(true);
			return false;
		}
		return true;
	}

	
	/**
	 * 检测字符串是否匹配给定的正则表达式
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean check(String str, String regex) {
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证非空
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkNotEmputy(String notEmputy) {
		regex = "^\\s*$";
		return check(notEmputy, regex) ? false : true;
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		String regex = "^\\w+[-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$ ";
		return check(email, regex);
	}

	/**
	 * 验证手机号码
	 * 
	 * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
	 * 联通号码段:130、131、132、136、185、186、145 电信号码段:133、153、180、189
	 * 
	 * @param cellphone
	 * @return
	 */
	public static boolean checkCellphone(String cellphone) {
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
		return check(cellphone, regex);
	}

	/**
	 * 验证固话号码
	 * 
	 * @param telephone
	 * @return
	 */
	public static boolean checkTelephone(String telephone) {
		String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
		return check(telephone, regex);
	}

	/**
	 * 验证传真号码
	 * 
	 * @param fax
	 * @return
	 */
	public static boolean checkFax(String fax) {
		String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
		return check(fax, regex);
	}

	/**
	 * 验证QQ号码
	 * 
	 * @param QQ
	 * @return
	 */
	public static boolean checkQQ(String QQ) {
		String regex = "^[1-9][0-9]{4,} $";
		return check(QQ, regex);
	}

	/**
	 * 验证是否数字
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNumber(String num) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(num).matches();
	}
	
	/**
	 * 检测字符串中是否包含用户名和密码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {

		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
}
