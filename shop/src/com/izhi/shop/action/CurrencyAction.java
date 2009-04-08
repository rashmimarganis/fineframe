package com.izhi.shop.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Currency;
import com.izhi.shop.service.ICurrencyService;
@Service
@Scope(value="prototype")
@Namespace("/currency")
public class CurrencyAction extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="currencyService")
	private ICurrencyService currencyService;
	private Currency obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)currencyService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("currencyId");
		pp.setDir("desc");
		List<Currency> l=currencyService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new Currency();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=currencyService.findCurrencyById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=currencyService.deleteCurrency(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=currencyService.deleteCurrencys(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getCurrencyId()==0){
			int i=currencyService.saveCurrency(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=currencyService.updateCurrency(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public ICurrencyService getCurrencyService() {
		return currencyService;
	}
	public void setCurrencyService(ICurrencyService currencyService) {
		this.currencyService = currencyService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Currency getObj() {
		return obj;
	}
	public void setObj(Currency obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
