package com.izhi.oa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.oa.model.OAMessage;
import com.izhi.oa.service.IOAMessageService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.security.support.SecurityUser;

@Service
@Scope("prototype")
@Namespace("/oa/msg")
public class OAMessageAction extends BasePageAction {

	private static final long serialVersionUID = -3302519036031397428L;
	@Resource(name="oaMessageService")
	private IOAMessageService messageService;
	
	private OAMessage obj;
	
	private int id;
	
	private List<Integer> ids;
	
	private int state;

	public IOAMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IOAMessageService messageService) {
		this.messageService = messageService;
	}
	@Action("index")
	public String index(){

		return SUCCESS;
	}
	/*
	 * 显示已发送短信息
	 * 参数为：翻页参数和当前用户编号
	 */
	@Action("listFrom")
	public String listFrom(){
		if(SecurityUser.isOnline()){
			int userId=SecurityUser.getUserId();
			List<Map<String,Object>> list=messageService.findPage(this.getPageParameter(), userId, OAMessage.DIRECT_FROM);
			int totalCount=messageService.findTotalCount(userId, OAMessage.DIRECT_FROM);
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("objs", list);
			m.put("totalCount", totalCount);
			String r=JSONObject.fromObject(m).toString();
			this.getRequest().setAttribute("result", r);
		}
		return SUCCESS;
	}
	/*
	 * 显示已接收短信息，
	 * 参数为：翻页参数和当前用户编号
	 */
	@Action("listTo")
	public String listTo(){
		if(SecurityUser.isOnline()){
			int userId=SecurityUser.getUserId();
			List<Map<String,Object>> list=messageService.findPage(this.getPageParameter(), userId, OAMessage.DIRECT_TO);
			int totalCount=messageService.findTotalCount(userId, OAMessage.DIRECT_TO);
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("objs", list);
			m.put("totalCount", totalCount);
			String r=JSONObject.fromObject(m).toString();
			this.getRequest().setAttribute("result", r);
		}
		return SUCCESS;
	}
	/*
	 * 显示短信息发送页面
	 */
	@Action("add")
	public String add(){
		return SUCCESS;
	}
	/*
	 * 发送短信息，
	 * 参数为：短信息对象obj
	 */
	@Action("send")
	public String send(){
		if(SecurityUser.isOnline()){
			if(obj!=null){
				int id=messageService.saveMessage(obj);
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("success", id>0);
				map.put("id", id);
				this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
			}
		}
		return SUCCESS;
	}
	/*
	 * 删除单条短信息，
	 * 参数为短消息编号id或编号列表
	 */
	@Action("delete")
	public String delete(){
		if(SecurityUser.isOnline()){
			int userId=SecurityUser.getUserId();
			Map<String,Object> map=new HashMap<String, Object>();
			if(id>0){
				OAMessage o=messageService.findMessageById(id);
				int direct=OAMessage.DIRECT_FROM;
				if(userId!=o.getFromUser().getUserId()){
					direct=OAMessage.DIRECT_TO;
				}
				boolean success=messageService.deleteMessage(id);
				
				int totalCount=messageService.findTotalCount(userId,direct);
				map.put("success", success);
				map.put("totalCount", totalCount);
			}else if(ids!=null&&ids.size()>0){
				OAMessage o=messageService.findMessageById(ids.get(0));
				int direct=OAMessage.DIRECT_FROM;
				if(userId!=o.getFromUser().getUserId()){
					direct=OAMessage.DIRECT_TO;
				}
				boolean success=messageService.deleteMessages(ids);
				int totalCount=messageService.findTotalCount(userId,direct);
				
				map.put("success", success);
				map.put("totalCount", totalCount);
			}
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	/*
	 * 变更短信息状态
	 * 参数为：短信息编号id和短信息的新状态state
	 */
	@Action("changeState")
	public String changeState(){
		if(SecurityUser.isOnline()){
			if(id>0){
				boolean success=messageService.changeState(id, state);
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("success", success);
				this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
			}
		}
		return SUCCESS;
	}
	/*
	 * 加载显示短信息内容，未读状态修改为已读
	 * 参数为：短信息编号id
	 */
	@Action("load")
	public String load(){
		if(SecurityUser.isOnline()){
			if(id>0){
				OAMessage msg=messageService.findMessageById(id);
				if(msg.getState()==OAMessage.STATE_NOT_READED){
					messageService.changeState(id, OAMessage.STATE_READED);
				}
				List<Map<String,Object>> l=messageService.findJsonById(id);
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("data", l);
				map.put("success", true);
				this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
			}
		}
		return SUCCESS;
	}

	public OAMessage getObj() {
		return obj;
	}

	public void setObj(OAMessage obj) {
		this.obj = obj;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
