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
package com.izhi.workflow.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.model.FlowMetaFile;
/**
 * <p>Title: PowerStone</p>
 */

public interface IFlowMetaFileDAO {
  public List findAllFlowMetaFiles();
  
  public List<Map<String,Object>> findPage(PageParameter pp,Long typeId);
  public List<Map<String,Object>> findByType(Long typeId);
  public int findTotalCount(Long typeId);
  public FlowMetaFile findFlowMetaFile(Long flowFileID);
  public void saveFlowMetaFile(FlowMetaFile flowMetaFile);

  public void deleteFlowMetaFile(Long flowFileID);

  public void updatePreviewImage(FlowMetaFile fmFile);
}
