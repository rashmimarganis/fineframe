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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TreeMaker {
	private static Log log = LogFactory.getLog(TreeMaker.class);
	private String hrefLink;
	private String contextPath;
	private String targetFrame;
	private String checkboxName;
	Collection nodeCollection;

	public TreeMaker(String href, String contextPath, String targetFrame,
			String checkboxName, Collection nodeColl) {
		this.hrefLink = href;
		this.contextPath = contextPath;
		this.targetFrame = targetFrame;
		this.checkboxName = checkboxName;
		this.nodeCollection = nodeColl;
	}

	// ------------------------------------------------------------------------------
	public Collection makeTree() {
		return this.printTree(this.processNodes(this.nodeCollection));
	}

	// ------------------------------------------------------------------------------
	public Collection makeTreeForRole(Collection resourcesHasRight,
			String webModuleID, String roleID) {
		return this.printTree(this.processNodes(this.nodeCollection),
				resourcesHasRight, webModuleID, roleID);
	}

	// ------------------------------------------------------------------------------
	public boolean hasSon(String nodeID) {
		Iterator it = nodeCollection.iterator();
		while (it.hasNext()) {
			TreeNode node = (TreeNode) it.next();
			if (nodeID.equals(node.getParentID())) {
				return true;
			}
		}
		return false;
	}

	// ------------------------------------------------------------------------------
	public ArrayList processNodes(Collection nodeColl) {
		ArrayList resultTree = new ArrayList();
		Iterator it = nodeColl.iterator();
		int i = 0;
		while (it.hasNext()) {
			TreeNode anode = (TreeNode) it.next();
			if (anode.getParentID() == null
					|| anode.getParentID().trim().equals("")) { // has no father
				resultTree.add(new Integer(i).toString());
				resultTree.add(anode);
				if (this.hasSon(anode.getNodeID())) {
					makeTree(nodeColl, resultTree, anode, (new Integer(i))
							.toString());
				}
				i++;
			}
		}
		return resultTree;
	}

	// ------------------------------------------------------------------------------
	private void makeTree(Collection source, ArrayList target, TreeNode node,
			String head) {
		Iterator it = source.iterator();
		int i = 0;
		while (it.hasNext()) {
			TreeNode anode = (TreeNode) it.next();
			if (anode.getParentID() != null
					&& anode.getParentID().equals(node.getNodeID())) {
				String me = head + "-" + (new Integer(i)).toString();
				target.add(me);
				target.add(anode);
				makeTree(source, target, anode, me);
				i++;
			}
		}
	}

	// ------------------------------------------------------------------------------
	private void makeTree(Collection source, ArrayList target, TreeNode node,
			String head, boolean forRole) {
		Iterator it = source.iterator();
		int i = 0;
		while (it.hasNext()) {
			TreeNode anode = (TreeNode) it.next();
			if (anode.getParentID() != null
					&& anode.getParentID().equals(node.getNodeID())) {
				String me = head + "-" + (new Integer(i)).toString();
				target.add(me);
				target.add(anode);
				makeTree(source, target, anode, me, forRole);
				i++;
			}
		}
	}

	// ------------------------------------------------------------------------------
	private Collection printTree(ArrayList source) {
		ArrayList outPut = new ArrayList();
		Iterator it = source.iterator();
		while (it.hasNext()) {
			String id = (String) it.next();
			TreeNode node = (TreeNode) it.next();
			String img = "";
			if (this.hasSon(node.getNodeID())) { // -------------�ж���
				img = "<img src=\"" + contextPath
						+ "/images/plus.gif\" border=0 id=" + id
						+ " onClick=\"expandIt('" + id + "'); return false\">";
			} else {
				img = "<img src=\"" + contextPath
						+ "/images/open.gif\" border=0 id=" + id + ">";
			}

			String nbsp = "";
			int level = this.countCharInString(id, '-');
			for (int i = 0; i < level; i++) {
				nbsp += "&nbsp;&nbsp;";

			}
			String tableStr = "";
			String url = this.hrefLink + node.getNodeID();
			if (node.getParentID() == null
					|| node.getParentID().trim().equals("")) { // ----------------��������
				if (this.checkboxName == null
						|| this.checkboxName.trim().equals("")) {
					tableStr = "<table cellpadding=0 cellspacing=1 class=LayoutWebPartFrame "
							+ " id="
							+ id
							+ " style=\"display:block\">"
							+ "<tbody><tr> <td valign=\"top\" class=LayoutWebPart>"
							+ nbsp
							+ "<img src=\""
							+ contextPath
							+ "/images/spacer.gif\"  border=0 alt=\"\">"
							+ img
							+ "&nbsp;<a href="
							+ url
							+ " target="
							+ targetFrame
							+ " onclick=changeColor('"
							+ id
							+ "','"
							+ node.getNodeID()
							+ "')><font size='' class=\"ldh-12-20\" color=#000000 id="
							+ id
							+ ">"
							+ node.getNodeName()
							+ "</font></a></td>" + " </tr></tbody></table>";

				} else {
					tableStr = "<table cellpadding=0 cellspacing=1 class=LayoutWebPartFrame "
							+ " id="
							+ id
							+ " style=\"display:block\">"
							+ "<tbody><tr> <td valign=\"top\" class=LayoutWebPart>"
							+ nbsp
							+ "<img src=\""
							+ contextPath
							+ "/images/spacer.gif\"  border=0 alt=\"\">"
							+ img
							+ "<input type=checkbox name="
							+ checkboxName
							+ " value="
							+ node.getNodeID()
							+ "><a href="
							+ url
							+ " target="
							+ targetFrame
							+ " onclick=changeColor('"
							+ id
							+ "','"
							+ node.getNodeID()
							+ "')><font size='' class=\"ldh-12-20\" color=#000000 id="
							+ id
							+ ">"
							+ node.getNodeName()
							+ "</font></a></td>" + " </tr></tbody></table>";

				}
			} else {
				if (this.checkboxName == null
						|| this.checkboxName.trim().equals("")) {
					tableStr = "<table valign=\"top\" cellpadding=0 cellspacing=1 class=LayoutWebPartFrame "
							+ " id="
							+ id
							+ " style=\"display:none\">"
							+ "<tbody><tr> <td valign=\"top\" class=LayoutWebPart>"
							+ nbsp
							+ "<img src=\""
							+ contextPath
							+ "/images/spacer.gif\"  border=0 alt=\"\">"
							+ img
							+ "&nbsp;<a href="
							+ url
							+ " target="
							+ targetFrame
							+ " onclick=changeColor('"
							+ id
							+ "','"
							+ node.getNodeID()
							+ "')><font size='' class=\"ldh-12-20\" color=#000000 id="
							+ id
							+ ">"
							+ node.getNodeName()
							+ "</font></a></td>" + " </tr></tbody></table>";

				} else {
					tableStr = "<table valign=\"top\" cellpadding=0 cellspacing=1 class=LayoutWebPartFrame "
							+ " id="
							+ id
							+ " style=\"display:none\">"
							+ "<tbody><tr> <td valign=\"top\" class=LayoutWebPart>"
							+ nbsp
							+ "<img src=\""
							+ contextPath
							+ "/images/spacer.gif\"  border=0 alt=\"\">"
							+ img
							+ "<input type=checkbox name="
							+ checkboxName
							+ " value="
							+ node.getNodeID()
							+ "><a href="
							+ url
							+ " target="
							+ targetFrame
							+ " onclick=changeColor('"
							+ id
							+ "','"
							+ node.getNodeID()
							+ "')><font size='' class=\"ldh-12-20\" color=#000000 id="
							+ id
							+ ">"
							+ node.getNodeName()
							+ "</font></a></td>" + " </tr></tbody></table>";

				}

			}
			outPut.add(tableStr);
			// System.out.println(tableStr);
		}
		return outPut;
	}

	// ------------------------------------------------------------------------------
	private Collection printTree(ArrayList source,
			Collection resourcesHasRight, String webModuleID, String roleID) {
		ArrayList outPut = new ArrayList();
		Iterator it = source.iterator();

		while (it.hasNext()) {
			String xinghao = "";
			String id = (String) it.next();
			TreeNode node = (TreeNode) it.next();

			try {
				Iterator resIT = resourcesHasRight.iterator();
				while (resIT.hasNext()) {

					// Operation op = (Operation) resIT.next();
					// if (op.getResourceID().equals(node.getNodeID())
					// && op.getWebModuleID().equals(webModuleID)) {
					// xinghao = "&nbsp;&nbsp;<a target='_top' title='ɾ��֮'
					// href='"
					// + contextPath +
					// "/rolemanage/remove_resource_role.do?roleID=" +
					// roleID + "&resourceID=" + node.getNodeID() +
					// "&webModuleID=" + webModuleID + "'>**</a>";
					// break;
					// }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			String img = "";
			if (this.hasSon(node.getNodeID())) { // -------------�ж���
				img = "<img src=\"" + contextPath
						+ "/images/plus.gif\" border=0 id=" + id
						+ " onClick=\"expandIt('" + id + "'); return false\">";
			} else {
				img = "<img src=\"" + contextPath
						+ "/images/open.gif\" border=0 id=" + id + ">";
			}

			String nbsp = "";
			int level = this.countCharInString(id, '-');
			for (int i = 0; i < level; i++) {
				nbsp += "&nbsp;&nbsp;";

			}
			String tableStr = "";
			String url = this.hrefLink + node.getNodeID();
			if (node.getParentID() == null
					|| node.getParentID().trim().equals("")) { // ----------------��������
				if (this.checkboxName == null
						|| this.checkboxName.trim().equals("")) {
					tableStr = "<table cellpadding=0 cellspacing=1 class=LayoutWebPartFrame "
							+ " id="
							+ id
							+ " style=\"display:block\">"
							+ "<tbody><tr> <td valign=\"top\" class=LayoutWebPart>"
							+ nbsp
							+ "<img src=\""
							+ contextPath
							+ "/images/spacer.gif\"  border=0 alt=\"\">"
							+ img
							+ "&nbsp;<a href="
							+ url
							+ " target="
							+ targetFrame
							+ " onclick=changeColor('"
							+ id
							+ "','"
							+ node.getNodeID()
							+ "')><font size='' class=\"ldh-12-20\" color=#000000 id="
							+ id
							+ ">"
							+ node.getNodeName()
							+ xinghao
							+ "</font></a></td>" + " </tr></tbody></table>";

				} else {
					tableStr = "<table cellpadding=0 cellspacing=1 class=LayoutWebPartFrame "
							+ " id="
							+ id
							+ " style=\"display:block\">"
							+ "<tbody><tr> <td valign=\"top\" class=LayoutWebPart>"
							+ nbsp
							+ "<img src=\""
							+ contextPath
							+ "/images/spacer.gif\"  border=0 alt=\"\">"
							+ img
							+ "<input type=checkbox name=groupToDel value="
							+ node.getNodeID()
							+ "><a href="
							+ url
							+ " target="
							+ targetFrame
							+ " onclick=changeColor('"
							+ id
							+ "','"
							+ node.getNodeID()
							+ "')><font size='' class=\"ldh-12-20\" color=#000000 id="
							+ id
							+ ">"
							+ node.getNodeName()
							+ xinghao
							+ "</font></a></td>" + " </tr></tbody></table>";

				}
			} else {
				if (this.checkboxName == null
						|| this.checkboxName.trim().equals("")) {
					tableStr = "<table valign=\"top\" cellpadding=0 cellspacing=1 class=LayoutWebPartFrame "
							+ " id="
							+ id
							+ " style=\"display:none\">"
							+ "<tbody><tr> <td valign=\"top\" class=LayoutWebPart>"
							+ nbsp
							+ "<img src=\""
							+ contextPath
							+ "/images/spacer.gif\"  border=0 alt=\"\">"
							+ img
							+ "&nbsp;<a href="
							+ url
							+ " target="
							+ targetFrame
							+ " onclick=changeColor('"
							+ id
							+ "','"
							+ node.getNodeID()
							+ "')><font size='' class=\"ldh-12-20\" color=#000000 id="
							+ id
							+ ">"
							+ node.getNodeName()
							+ xinghao
							+ "</font></a></td>" + " </tr></tbody></table>";

				} else {
					tableStr = "<table valign=\"top\" cellpadding=0 cellspacing=1 class=LayoutWebPartFrame "
							+ " id="
							+ id
							+ " style=\"display:none\">"
							+ "<tbody><tr> <td valign=\"top\" class=LayoutWebPart>"
							+ nbsp
							+ "<img src=\""
							+ contextPath
							+ "/images/spacer.gif\"  border=0 alt=\"\">"
							+ img
							+ "<input type=checkbox name=groupToDel value="
							+ node.getNodeID()
							+ "><a href="
							+ url
							+ " target="
							+ targetFrame
							+ " onclick=changeColor('"
							+ id
							+ "','"
							+ node.getNodeID()
							+ "')><font size='' class=\"ldh-12-20\" color=#000000 id="
							+ id
							+ ">"
							+ node.getNodeName()
							+ xinghao
							+ "</font></a></td>" + " </tr></tbody></table>";

				}

			}
			outPut.add(node.getNodeID());
			outPut.add(tableStr);
			// System.out.println(tableStr);
		}
		return outPut;
	}

	// ------------------------------------------------------------------------------
	private int countCharInString(String str, char target) {
		int i = 0;
		for (int j = 0; j < str.length(); j++) {
			if (str.charAt(j) == target) {
				i++;
			}
		}
		return i;
	}
}
