package com.izhi.shop.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Brand;
import com.izhi.shop.model.ProductCategory;
import com.izhi.shop.model.Product;
import com.izhi.shop.model.ProductType;
import com.izhi.shop.service.IBrandService;
import com.izhi.shop.service.ICategoryService;
import com.izhi.shop.service.IProductService;
import com.izhi.shop.service.IProductTypeService;
@Service
@Scope(value="prototype")
@Namespace("/product")
public class ProductAction extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="productService")
	private IProductService productService;
	@Resource(name="productTypeService")
	private IProductTypeService typeService;
	@Resource(name="categoryService")
	private ICategoryService categoryService;
	@Resource(name="brandService")
	private IBrandService brandService;
	
	private Product obj;
	private List<Integer> ids;
	private File image;
	private String imageContentType;
	private String imageFileName;
	private int id;
	private List<ProductType> types;
	private List<ProductCategory> categories;
	private List<Brand> brands;
	
	public List<ProductType> getTypes() {
		return types;
	}
	public void setTypes(List<ProductType> types) {
		this.types = types;
	}
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)productService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("productId");
		pp.setDir("desc");
		List<Product> l=productService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new Product();
		types=typeService.findAll();
		categories=categoryService.findTopAll();
		brands=brandService.findAll();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		types=typeService.findAll();
		categories=categoryService.findTopAll();
		obj=productService.findProductById(id);
		brands=brandService.findAll();
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=productService.deleteProduct(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}

	@Action("deletes")
	public String deletes(){
		boolean i=productService.deleteProducts(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("online")
	public String online(){
		boolean i=productService.onlineProducts(ids, true);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("offline")
	public String offline(){
		boolean i=productService.onlineProducts(ids, false);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		if(image!=null){
			obj.setImagePath(this.uploadFile(image, imageContentType));
		}
		if(obj.getProductId()==0){
			obj.setUpTime(new Date());
			obj.setShop(SecurityUser.getShop());
			int i=productService.saveProduct(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=productService.updateProduct(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Product getObj() {
		return obj;
	}
	public void setObj(Product obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getImageContentType() {
		return imageContentType;
	}
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public IProductTypeService getTypeService() {
		return typeService;
	}
	public void setTypeService(IProductTypeService typeService) {
		this.typeService = typeService;
	}
	public ICategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public List<ProductCategory> getCategories() {
		return categories;
	}
	public void setCategories(List<ProductCategory> categories) {
		this.categories = categories;
	}
	public IBrandService getBrandService() {
		return brandService;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public List<Brand> getBrands() {
		return brands;
	}
	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}
	
}
