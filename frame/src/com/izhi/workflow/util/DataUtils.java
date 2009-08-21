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

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.Element;
import org.jdom.EntityRef;

/**
 * This class contains a method for extracting the data contained by a JDOM
 * node. If the node is an Element or Entity instance, the data might be split
 * in adjacent String and CDATA nodes. Comments may also act as splitters.
 * Logically, however, only sub-elements and processing instructions split the
 * data. An application might not want to be bothered by a CDATA section, an
 * entity reference or a comment separating the text maintained in two String
 * objects.
 */
public class DataUtils {

	/**
	 * Extracts the character data contained by a node. If the node is a String
	 * or a CDATA section, this will return the node's data. Otherwise, it
	 * iterates over its children and collects the character data in a string
	 * array. Each component of this array is the concatenation of the data
	 * contained by adjacent String and CDATA nodes after the entity references
	 * were replaced with their values. Therefore, the array has multiple
	 * components only if sub-elements or processing instructions separate the
	 * character data. Note that the data contained by sub-elements is not
	 * included. If the node contains no data, a string array containing an
	 * empty string is returned.
	 */
	public static String[] getData(Object node) {
		Vector vector = new Vector();
		StringBuffer buf = new StringBuffer();
		getDataImpl(node, buf, vector);
		if (buf.length() > 0) {
			vector.add(buf.toString());
		}
		String array[] = new String[vector.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = (String) vector.get(i);
		}
		if (array.length == 0) {
			array = new String[] { "" };
		}
		return array;
	}

	/**
	 * Implements the data extraction mechanism. The getData() method calls
	 * getDataImpl() passing as parameters an empty string buffer and an empty
	 * vector. This method iterates over the children of the given node and
	 * appends the data of the String and CDATA nodes to the given string
	 * buffer. The method is called recursively for each Entity so that their
	 * data can be collected. Comments are ignored. When this method finds a
	 * child node that acts as data separator (sub-element or processing
	 * instruction), the content of the string buffer (if any) is added to the
	 * vector and the buffer is emptied. The getData() method will convert the
	 * vector to a string array.
	 */
	private static void getDataImpl(Object node, StringBuffer buf, Vector vector) {
		if (node instanceof String) {
			buf.append((String) node);
			return;
		}
		if (node instanceof CDATA) {
			buf.append(((CDATA) node).getText());
			return;
		}

		List content = null;
		if (node instanceof Element || node instanceof EntityRef) {
			content = ((Element) node).getContent();
			// b6 } else if (node instanceof Entity) {
			// b6 content = ((Entity) node).getMixedContent();
		}
		if (content == null) {
			return;
		}
		Iterator iterator = content.iterator();
		while (iterator.hasNext()) {
			Object child = iterator.next();
			if (child instanceof String) {
				buf.append((String) child);
			} else if (child instanceof CDATA) {
				buf.append(((CDATA) child).getText());
				// } else if (child instanceof Entity) {
			} else if (child instanceof EntityRef) {
				getDataImpl(child, buf, vector);
			} else if (!(child instanceof Comment)) {
				if (buf.length() > 0) {
					vector.add(buf.toString());
					buf.setLength(0);
				}
			}
		}
	}

}
