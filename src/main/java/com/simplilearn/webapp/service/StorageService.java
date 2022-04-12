package com.simplilearn.webapp.service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.simplilearn.webapp.exception.StorageException;

@Service
public class StorageService {

	@Value("${upload.filepath}")
	private String filepath;
	
	public void uploadFile(MultipartFile file) {
		// verify if file is empty
		if(file.isEmpty()) {
			throw new StorageException("Falied to upload a file :: file is empty. ");
		}
		
		System.out.println("File Extension :: "+getFileExtension(file));
		// upload/copy file at server specified file path
		try {
			String filename = file.getOriginalFilename();
			InputStream sourceFile = file.getInputStream();
			Files.copy(sourceFile, Paths.get(filepath+filename), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new StorageException("Falied to upload a file. ");
		}		
	}
	
	public String getFilePath(String filename) {
		return filepath+filename;
	}
	
	private String getFileExtension(MultipartFile file) {
	    String name = file.getOriginalFilename();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return "";
	    }
	    return name.substring(lastIndexOf);
	}
	
}
