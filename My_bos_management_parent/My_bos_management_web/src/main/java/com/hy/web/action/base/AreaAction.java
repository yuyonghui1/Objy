package com.hy.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.hy.domain.base.Area;
import com.hy.service.Customer;
import com.hy.service.CustomerService;
import com.hy.service.base.AreaService;
import com.hy.utils.PinYin4jUtils;
import com.hy.web.action.common.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class AreaAction extends BaseAction<Area>{
	
//	private Area model=new Area();
//	
//	@Override
//	public Area getModel() {
//		return model;
//	}
	
	@Autowired
	private AreaService areaService;
	
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	@Action("area_importXls")
	public void importXls() throws  Exception{
		
		HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(upload));
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		List<Area> list = new ArrayList<>();
		Area area;
		
		for (Row row : sheet) {
			
			if(row.getRowNum()==0){
				continue;
			}
			
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			
			area = new Area(id, province, city, district, postcode, null, null);
			
			province = province.substring(0, province.length()-1);
			city = city.substring(0, city.length()-1);
			district = district.substring(0, district.length()-1);
			
			String all=province+city+district;
			String[] strs = PinYin4jUtils.getHeadByString(all);
			String shortcode = StringUtils.join(strs);
			area.setShortcode(shortcode);
			
			String citycode = PinYin4jUtils.hanziToPinyin(city);
			area.setCitycode(citycode);
			
			list.add(area);			
		}
		
		areaService.saveArea(list);
		
	}
	
//	private int rows;
//	private int page;
//	public void setRows(int rows) {
//		this.rows = rows;
//	}
//	public void setPage(int page) {
//		this.page = page;
//	}
	@Action("area_findPage")
	public void findPage(){
		
		Pageable pageAble=new PageRequest(page, rows);
		
		Page<Area> aPage = areaService.findPage(pageAble);
		
		java2json(aPage, new String[]{"subareas"});
		
//		long total = aPage.getTotalElements();
//		List<Area> list = aPage.getContent();
//		
//		HashMap<String, Object> map = new HashMap<>();
//		
//		map.put("rows",list);
//		map.put("total",total);
//		
//		JsonConfig config = new JsonConfig();
//		config.setExcludes(new String[]{"subareas"});
//		String json = JSONObject.fromObject(map,config).toString();
//		
//		try {
//			HttpServletResponse response = ServletActionContext.getResponse();
//			
//			response.setContentType("text/json;charset=utf-8");
//			
//			response.getWriter().write(json);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	@Action("area_findAll")
	public void findAll(){
		
		List<Area> list = areaService.findAll();
		
		java2json(list, new String[]{"subareas"});
	}
}
