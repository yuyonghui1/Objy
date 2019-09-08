package com.hy.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.hy.domain.base.SubArea;
import com.hy.service.base.SubAreaService;
import com.hy.utils.FileUtils;
import com.hy.web.action.common.BaseAction;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results(@Result(name="list",location="/pages/base/sub_area.jsp"))
public class SubAreaAction extends BaseAction<SubArea>{
	
	@Autowired
	private SubAreaService subAreaService;
	
	@Action("subarea_save")
	public String save(){
		
		subAreaService.save(model);
		
		return "list";
	}
	
	@Action("subArea_findPage")
	public void findPage(){
		
		Pageable pageable = new PageRequest(page, rows);
		
		Page<SubArea> page=subAreaService.findPage(pageable);
		
		java2json(page, new String[]{"subareas","couriers"});
	}
	
	@Action("subarea_findAll")
	public void findAll(){
		
		List<SubArea> list=subAreaService.findByFixedAreaIsNull();
		
		java2json(list, new String[]{"subareas","couriers"});
	}
	
	@Action("subArea_exportSubArea")
	public void exportSubArea() throws Exception{
		
		List<SubArea> list=subAreaService.findAll();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet("sheet1");
		
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("编号");
		row.createCell(1).setCellValue("关键字");
		row.createCell(2).setCellValue("辅助关键字");
		row.createCell(3).setCellValue("区域");
		row.createCell(4).setCellValue("定区");
		
		SubArea subArea;
		for(int i=0;i<list.size();i++){
			subArea = list.get(i);
			row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(subArea.getId());
			row.createCell(1).setCellValue(subArea.getKeyWords());
			row.createCell(2).setCellValue(subArea.getAssistKeyWords());
			row.createCell(3).setCellValue(subArea.getArea().getName());
			row.createCell(4).setCellValue(subArea.getFixedArea()==null?"":subArea.getFixedArea().getFixedAreaName());
		}
		
		String fileName="分区.xls";
		
		String agent=ServletActionContext.getRequest().getHeader("User-Agent");
		
		fileName=FileUtils.encodeDownloadFilename(fileName, agent);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Content-Type", "application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment;fileName="+fileName);
		
		workbook.write(response.getOutputStream());
		workbook.close();
		
	}
	
	@Action("subArea_showSubareaChart")
	public void showSubareaChart() throws Exception {

		List<Object[]> list=subAreaService.findChart();
		
		java2json(list, new String[]{});
	}
	
}
