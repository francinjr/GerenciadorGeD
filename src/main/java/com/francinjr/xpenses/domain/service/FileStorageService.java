package com.francinjr.xpenses.domain.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.francinjr.xpenses.config.FileStorageConfig;
import com.francinjr.xpenses.domain.exception.FileStorageException;
import com.francinjr.xpenses.domain.exception.MyFileNotFoundException;

@Service
public class FileStorageService {
	
	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		Path path = Paths.get(fileStorageConfig.getUploadDir())
			.toAbsolutePath().normalize();
		
		this.fileStorageLocation = path;
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException(
				"Could not create the directory where the uploaded files will be stored!", e);
		}
	}
	
	public String storeFile(MultipartFile file) {
		// Parei aqui. O que devo fazer é a lógica para ler o arquivo e transformar o texto
		// em uma finança
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			// Filename..txt
			if (filename.contains("..")) {
				throw new FileStorageException(
					"Sorry! Filename contains invalid path sequence " + filename);
			}
			Path targetLocation = this.fileStorageLocation.resolve(filename);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return filename;
		} catch (Exception e) {
			throw new FileStorageException(
				"Could not store file " + filename + ". Please try again!", e);
		}
	}
	
	
	public Resource loadFileAsResource(String filename) {
		
		try {
			Path filePath = this.fileStorageLocation.resolve(filename).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if(resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found");
			}
		} catch(Exception e) {
			throw new MyFileNotFoundException("File not found" + filename, e);
		}
	}
	
	
	public List<String> findAllFiles() throws Exception {
		File folder = new File(this.fileStorageLocation.toString());
		
		List<File> files = findAllFilesInFolder(folder);

		List<String> filesPresentations = new ArrayList<>();
		for (File file : files) {
			filesPresentations.add(file.getName());
		}
		
		return filesPresentations;
	}
	
	
	public List<File> findAllFilesInFolder(File folder) throws Exception {
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();

			if (files != null) {
				List<File> foundedFiles = new LinkedList<>();

				for (File file : files) {
					foundedFiles.add(file);
				}
				return foundedFiles;
			} else {
				throw new Exception("The directory is empty.");
			}
		} else {
			throw new Exception("The specified path is not a valid directory.");
		}
	}
}
