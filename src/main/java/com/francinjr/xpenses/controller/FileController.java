package com.francinjr.xpenses.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.francinjr.xpenses.domain.service.FileStorageService;
import com.francinjr.xpenses.dto.UploadFileResponseDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/file/v1")
@Tag(name = "File Endpoint")
public class FileController {
	private Logger logger = Logger.getLogger(FileController.class.getName());
	
	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/uploadFile")
	public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) {
		logger.info("Storing file to disk");
		
		var filename = fileStorageService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/api/file/v1/downloadFile/")
			.path(filename)
			.toUriString();

		return new UploadFileResponseDTO(
			filename, fileDownloadUri, file.getContentType(), file.getSize()
		);
	}


	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponseDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		logger.info("Storing files to disk");
		
		return Arrays.asList(files)
			.stream()
			.map(file -> uploadFile(file))
			.collect(Collectors.toList());
	}
}
