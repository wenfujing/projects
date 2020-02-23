package com.tool;

public class StringTool {
	 /**
	  *<b>��ȡָ���ֽڳ��ȵ��ַ��������ܷ��ذ������</b>
	  * ���磺
	  * �����ҳ�������ʾ17�����֣���ô length ��Ϊ 34
	  * StringTool.getSubString(str, 34);
	  * 
	  * @param str
	  * @param length
	  * @return
	  */
	 public static String getSubString(String str, int length) {
	  int count = 0;
	  int offset = 0;
	  char[] c = str.toCharArray();
	  for (int i = 0; i < c.length; i++) {
	   if (c[i] > 256) {
	    offset = 2;
	    count += 2;
	   } else {
	    offset = 1;
	    count++;
	   }
	   if (count == length) {
	    return str.substring(0, i + 1);
	   }
	   if ((count == length + 1 && offset == 2)) {
	    return str.substring(0, i);
	   }
	  }
	  return "";
	 }
	}
