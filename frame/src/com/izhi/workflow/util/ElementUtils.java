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
import java.util.Vector;

import org.jdom.Element;

/**
 * This class contains methods for finding the elements of a JDOM tree that
 * satisfy some criteria. We can specify the tag name, local name and namespace
 * URI, character data that must be contained by the elements and a list of
 * attributes with the wanted values.
 * 
 * <P>
 * We use the getData() method of our DataUtils to extract the data of an
 * element.
 */
public class ElementUtils {

	/**
	 * Traverses a DOM tree in preorder and collects the Element instances whose
	 * tag name matches tagName (if not null), contain the strings of the data
	 * array (if not null) and have the given attributes set to the given values
	 * (if the arrays aren't null). The root element can be any Element
	 * instance; it doesn't have to be the root of a document.
	 */

	public static Element[] findElements(Element root, String tagName,
			String data[], String attrNames[], String attrValues[]) {
		return findElementsImpl(root, null, tagName, data, attrNames,
				attrValues);
	}

	/**
	 * This is the namespace-aware version of the previous method.
	 */
	public static Element[] findElements(Element root, String namespaceURI,
			String localName, String data[], String attrNames[],
			String attrValues[]) {
		return findElementsImpl(root, namespaceURI, localName, data, attrNames,
				attrValues);
	}

	/**
	 * Creates a vector and passes it to the method with the same name that
	 * follows this one. Then, it converts the vector to an array of Elements,
	 * which is returned.
	 */
	private static Element[] findElementsImpl(Element root,
			String namespaceURI, String name, String data[],
			String attrNames[], String attrValues[]) {
		Vector vector = new Vector();
		findElementsImpl(root, namespaceURI, name, data, attrNames, attrValues,
				vector);
		Element array[] = new Element[vector.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = (Element) vector.get(i);
		}
		return array;
	}

	/**
	 * Implements the finding of the elements within the JDOM tree. If the given
	 * element satisfies the finding criteria, it is added to the vector. Then,
	 * the method iterates over the children of the given node and a recursive
	 * call is made for each Element instance.
	 * 
	 * <P>
	 * If the name isn't null, it must match the tag name (if the namespace URI
	 * is null) or the local name in order to be considered for further
	 * checking. The element's character data must contain all the strings of
	 * the data array (if any). All the attributes specified by attrNames must
	 * have the values contained by attrValues. If all these conditions are met
	 * the element is added to the vector.
	 * 
	 * <P>
	 * Whether the element satisfies the criteria or not, its sub-elements are
	 * passed to recursive calls of this method so that they can be verified
	 * too.
	 */
	private static void findElementsImpl(Element root, String namespaceURI,
			String name, String data[], String attrNames[],
			String attrValues[], Vector vector) {
		boolean flag = false;
		if (name == null
				|| (namespaceURI == null && name
						.equals(root.getQualifiedName()))
				|| (namespaceURI != null
						&& namespaceURI.equals(root.getNamespaceURI()) && name
						.equals(root.getName()))) {
			flag = true;
			if (data != null) {
				String rootData[] = DataUtils.getData(root);
				for (int i = 0; i < data.length; i++) {
					flag = false;
					for (int j = 0; j < rootData.length; j++) {
						if (rootData[j].indexOf(data[i]) != -1) {
							flag = true;
							break;
						}
					}
					if (flag == false) {
						break;
					}
				}
			}

			if (flag && attrNames != null && attrValues != null) {
				for (int i = 0; i < attrNames.length; i++) {
					String value = root.getAttributeValue(attrNames[i]);
					if (value == null || !value.equals(attrValues[i])) {
						flag = false;
						break;
					}
				}
			}
		}

		if (flag) {
			vector.add(root);
		}

		Iterator iterator = root.getChildren().iterator();
		while (iterator.hasNext()) {
			Object child = iterator.next();
			findElementsImpl((Element) child, namespaceURI, name, data,
					attrNames, attrValues, vector);
		}
	}

}
