package com.firstApp.tamaroas.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.firstApp.tamaroas.model.MyUploadForm;

@Controller
public class MyFileUploadController {

	@RequestMapping("/upload")
	public String home() {
		
		return "indexUpload";
	}
	
	@RequestMapping(value ="/uploadOneFile", method =RequestMethod.GET)
	public String uploadOneFile(Model model) {
		MyUploadForm myUploadForm = new MyUploadForm();
		
		return "uploadOneFile";
	}
	
	@RequestMapping(value="/uploadOneFile", method = RequestMethod.POST)
	public String uploadOneFilePost(HttpServletRequest request, Model model, @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
		
		return this.doUpload(request, model, myUploadForm);
		
	}

	private String doUpload(HttpServletRequest request, Model model, MyUploadForm myUploadForm) {
		
		String description = myUploadForm.getDescription();
		System.out.println("Description "+description);
		
		//String uploadRootPath = request.getServletContext().getRealPath("upload");
		
		String uploadRootPath = "./upload";
		System.out.println("uploadRootPath: "+uploadRootPath);
		
		File uploadRootDir = new File(uploadRootPath);
		
		if(!uploadRootDir.exists()) 
		{
			uploadRootDir.mkdirs();
		}
		
		MultipartFile[] fileDatas = myUploadForm.getFileDatas();
		
		List<File> uploadFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();
		
		
		for(MultipartFile fileData: fileDatas) 
		{
			String name = fileData.getOriginalFilename();
			System.out.println("Client file name = "+name);
			
			if(name!=null && name.length()>0) 
			{
				try 
				{
					File serverFile = new File(uploadRootDir.getAbsolutePath()+File.separator+name);
					
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					
					uploadFiles.add(serverFile);
					
					System.out.println("Write file: "+serverFile);
				}catch(Exception e) 
				
				{
					System.out.println("Error write file "+name);
					failedFiles.add(name);
				}
			}
			
		}
		
		model.addAttribute("description", description);
		model.addAttribute("uploadedFiles", uploadFiles);
		model.addAttribute("failedFiles", failedFiles);
		
		return "uploadResult";
	}
	
}
