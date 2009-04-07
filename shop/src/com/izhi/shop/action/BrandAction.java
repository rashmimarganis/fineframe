package com.izhi.shop.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Brand;
import com.izhi.shop.service.IBrandService;
@Service
@Scope(value="prototype")
@Namespace("/brand")
public class BrandAction extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="brandService")
	private IBrandService brandService;
	private Brand obj;
	private List<Integer> ids;
	private File logo;
	private String logoContentType;
	private String logoFileName;
	
	private int id;
	private int p=1;
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)brandService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("brandId");
		pp.setDir("desc");
		List<Brand> l=brandService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new Brand();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=brandService.findBrandById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=brandService.deleteBrand(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=brandService.deleteBrands(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		if(logo!=null){
			String logoPath=this.uploadFile(logo,this.getLogoContentType());
			obj.setBrandLogo(logoPath);
		}
		if(obj.getBrandId()==0){
			int i=brandService.saveBrand(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=brandService.updateBrand(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IBrandService getBrandService() {
		return brandService;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Brand getObj() {
		return obj;
	}
	public void setObj(Brand obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}
	public File getLogo() {
		return logo;
	}
	public void setLogo(File logo) {
		this.logo = logo;
	}
	public String getLogoContentType() {
		return logoContentType;
	}
	public void setLogoContentType(String logoContentType) {
		this.logoContentType = logoContentType;
	}
	public String getLogoFileName() {
		return logoFileName;
	}
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	
}
