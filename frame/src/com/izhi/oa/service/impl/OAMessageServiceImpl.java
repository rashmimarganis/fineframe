package com.izhi.oa.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.oa.dao.IOAMessageDao;
import com.izhi.oa.model.OAMessage;
import com.izhi.oa.service.IOAMessageService;
import com.izhi.platform.util.PageParameter;

@Service("oaMessageService")
public class OAMessageServiceImpl implements IOAMessageService {

	@Resource(name = "oaMessageDao")
	private IOAMessageDao messageDao;

	@Override
	public boolean changeState(int msgId, int state) {
		if(msgId>0){
			return messageDao.changeState(msgId, state);
		}
		return false;
	}

	@Override
	public boolean deleteMessage(int id) {
		if(id>0){
			return messageDao.deleteMessage(id);
		}
		return false;
	}

	@Override
	public boolean deleteMessages(List<Integer> ids) {
		if(ids!=null){
			return messageDao.deleteMessages(ids);
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		if (id > 0) {
			return messageDao.findJsonById(id);
		} else {
			return null;
		}
	}

	@Override
	public OAMessage findMessageById(int id) {
		if (id > 0) {
			return messageDao.findMessageById(id);
		} else {
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> findPage(PageParameter pp, int userId,
			int direct) {
		return messageDao.findPage(pp, userId, direct);
	}

	@Override
	public int findTotalCount(int userId, int direct) {
		return messageDao.findTotalCount(userId, direct);
	}

	@Override
	public int saveMessage(OAMessage obj) {
		return messageDao.saveMessage(obj);
	}

	public IOAMessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(IOAMessageDao messageDao) {
		this.messageDao = messageDao;
	}

}
