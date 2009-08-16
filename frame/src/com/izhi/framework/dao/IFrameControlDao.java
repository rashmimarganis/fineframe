package com.izhi.framework.dao;

import java.util.List;

import com.izhi.framework.model.FrameControl;
import com.izhi.platform.util.PageParameter;

public interface IFrameControlDao {
	int saveControl(FrameControl obj);
	boolean updateControl(FrameControl obj);
	boolean deleteControl(int id);
	boolean deleteControls(List<Integer> ids) ;
	FrameControl findControlById(int id);
	FrameControl findControlByName(String name);
	List<FrameControl> findPage(PageParameter pp);
	int findTotalCount();
}
