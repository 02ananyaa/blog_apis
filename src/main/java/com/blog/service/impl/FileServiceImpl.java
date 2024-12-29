package com.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
//		file name 
		String name = file.getOriginalFilename();
//		abc.png

		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

//		full path
		String filepath = path + File.separator + fileName;

//		create folder if not created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

//		making copy
		Files.copy(file.getInputStream(), Paths.get(filepath));

		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullpath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullpath);
//	db logic to return inputstream
		return is;
	}

}
