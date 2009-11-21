package com.izhi.oa.service;

import java.util.List;
import java.util.Map;

import com.izhi.oa.model.OAMessage;
import com.izhi.platform.util.PageParameter;
/*
 * 短信息业务层接口
 */
public interface IOAMessageService {
	/*
	 * 保存短消息
	 * obj:要保存的短消息对象
	 */
	int saveMessage(OAMessage obj);
	/*
	 * 删除一条短消息
	 * id:消息编号
	 */
	boolean deleteMessage(int id);
	/*
	 * 删除多条短消息
	 * ids:List,为要删除的消息的编号列表
	 */
	boolean deleteMessages(List<Integer> ids) ;
	/*
	 * 获取一条短消息
	 */
	OAMessage findMessageById(int id);
	/*
	 * 短消息列表翻页
	 * userId:消息发送者或接收者
	 * direct:消息方向，指消息是发送还是接收
	 */
	List<Map<String,Object>> findPage(PageParameter pp,int userId,int direct);
	/*
	 * 短消息总数
	 * userId:消息发送者或接收者
	 * direct:消息方向，指消息是发送还是接收
	 */
	int findTotalCount(int userId,int direct);
	
	/*
	 * 获取短信息的JSON格式数据
	 * Id:短消息编号
	 */
	List<Map<String,Object>> findJsonById(int id);
	/*
	 * 更新短消息状态
	 * msgId:短消息编号
	 * state:要变更到的信息状态
	 */
	boolean changeState(int msgId,int state);
}
