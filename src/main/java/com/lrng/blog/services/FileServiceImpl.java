package com.lrng.blog.services;

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

@Service
public class FileServiceImpl implements IFileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		String name = file.getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		String filePath = path + File.separator + fileName;

		// Create a folder if doesnt exists
		File file2 = new File(path);
		if (!file2.exists())
			file2.mkdir();

		Files.copy(file.getInputStream(), Paths.get(filePath));

		return filePath;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

		String fullPath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(fullPath);

		return inputStream;
	}

}
