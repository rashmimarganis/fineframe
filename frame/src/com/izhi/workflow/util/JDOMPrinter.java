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

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.EntityRef;
import org.jdom.JDOMException;
import org.jdom.ProcessingInstruction;
import org.jdom.input.SAXBuilder;

/**
 * This class parses an XML file and prints the JDOM tree. It can work as a
 * standalone application, but it is also used by other examples of this
 * chapter: CreationSample, DataSample and ElementSample.
 */
public class JDOMPrinter {

	/**
	 * Prints a given number of double-spaces followed by a string to the
	 * standard output stream.
	 */
	public static void println(String s, int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print("  ");
		}
		System.out.println(s);
	}

	/**
	 * Prints a string to the standard output stream.
	 */
	public static void println(String s) {
		println(s, 0);
	}

	/**
	 * Prints the JDOM tree whose root is given as parameter.
	 */
	public static void print(Object node) {
		printImpl(node, 0);
	}

	/**
	 * This is the implementation of the above print() method. It first prints
	 * several node properties: type, name and value. Empty text nodes are
	 * ignored. Then it prints the attributes and the children of the given node
	 * using recursive calls.
	 */
	private static void printImpl(Object node, int indent) {
		if (node == null) {
			return;
		}
		if (node instanceof Document) {
			Document doc = (Document) node;
			println("Document", indent);
			printImpl(doc.getContent(), indent + 1);
		} else if (node instanceof DocType) {
			DocType dtd = (DocType) node;
			println("DocType - " + dtd.getElementName() + " - "
					+ dtd.getPublicID() + " - " + dtd.getSystemID(), indent);
		} else if (node instanceof Element) {
			Element elem = (Element) node;
			println("Element - " + elem.getName(), indent);
			printImpl(elem.getAttributes(), indent + 1);
			printImpl(elem.getContent(), indent + 1);
		} else if (node instanceof Attribute) {
			Attribute attr = (Attribute) node;
			println("Attribute - " + attr.getName() + " - " + attr.getValue(),
					indent);
		} else if (node instanceof String) {
			String str = ((String) node).trim();
			if (str.length() > 0) {
				println("String - " + str, indent);
			}
		} else if (node instanceof CDATA) {
			CDATA cdata = (CDATA) node;
			String str = cdata.getText().trim();
			if (str.length() > 0) {
				println("CDATA - " + str, indent);
			}
		} else if (node instanceof Comment) {
			Comment comm = (Comment) node;
			String str = comm.getText().trim();
			if (str.length() > 0) {
				println("Comment - " + str, indent);
			}
		} else if (node instanceof ProcessingInstruction) {
			ProcessingInstruction proc = (ProcessingInstruction) node;
			println("ProcessingInstruction - " + proc.getTarget() + " - "
					+ proc.getData(), indent);
		} // b6 else if (node instanceof Entity) {
		else if (node instanceof EntityRef) {
			// b6 Entity entity = (Entity) node;
			EntityRef entity = (EntityRef) node;
			println("Entity - " + entity.getName(), indent);
			// printImpl(entity.getMixedContent(), indent + 1);
			printImpl(entity, indent + 1);
		} else if (node instanceof List) {
			List list = (List) node;
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				printImpl(iterator.next(), indent);
			}
		}
	}

	/**
	 * Parses an XML document and returns the resulted JDOM tree.
	 */
	public static Document parse(String path, boolean validation)
			throws IOException, JDOMException {

		// Create the JDOM SAXBuilder
		SAXBuilder saxBuilder = new SAXBuilder(validation);

		// Return the JDOM tree
		return saxBuilder.build(new File(path));
	}

	/**
	 * Parses the document whose path is given in the command line and prints
	 * the obtained JDOM tree. If the command line is empty, it uses nodes.xml
	 */
	public static void main(String args[]) {
		try {
			String path = args.length > 0 ? args[0] : "nodes.xml";
			print(parse(path, false));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
