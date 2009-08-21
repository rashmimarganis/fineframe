/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the LGPL license, Version 2.1 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author daquanda(liyingquan@gmail.com)
 * @author kevin(diamond_china@msn.com)
 */
package com.izhi.workflow.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeUtil {

	public static String getID(String prefix) {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String random = new Float(Math.random()).toString().substring(2);

		// ��ֹ���ֿ�ѧ����
		if (random.indexOf("E") > 0) {
			random = random.substring(0, random.indexOf("E"));
		}
		String back = prefix
				+ format.format(new Timestamp(System.currentTimeMillis()))
				+ random;
		return back;
	}

	public static String getTimeStamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		return format.format(new Timestamp(System.currentTimeMillis()));
	}

}
