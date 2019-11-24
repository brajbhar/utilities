/**
 * 
 */
package com.citiustech.pdfutils.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.citiustech.pdfutils.model.Merge;
import com.citiustech.pdfutils.service.PDFUtilsService;

/**
 * @author bablur
 *
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PDFUtilsController {
	
	public static final String PING_SUCCESS_RESPONSE = "success";

	@Autowired
	PDFUtilsService pdfUtilsService;
	
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadFileBySize(@RequestParam("sizeInMB") int sizeInMB, 
														@RequestParam(value = "frame", required = false) int frame
			) throws IOException  {
		InputStreamResource resource = null;
		RandomAccessFile randomFile = null;
		int length = sizeInMB * 1024 * 1024;
		try {
		
		randomFile = new RandomAccessFile("file.txt", "rw");
		randomFile.setLength(length);
		resource = new InputStreamResource(Channels.newInputStream(randomFile.getChannel()));
		    
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally {
			
		}
		return ResponseEntity.ok()
	            .contentLength(length)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
	
	@RequestMapping(value = "mergePdf", method = RequestMethod.POST)
	public ResponseEntity<byte[]> mergePDF(@RequestParam("files") MultipartFile[] multipartFiles) throws Exception, IOException {
		List<File> files = convertFiles(multipartFiles);
		File mergedFile = pdfUtilsService.merge(files);
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("charset", "utf-8");
	    responseHeaders.setContentType(MediaType.APPLICATION_PDF);
	    responseHeaders.set("Content-disposition", "attachment; filename=merged.pdf");
		return new ResponseEntity<byte[]>(Files.readAllBytes(mergedFile.toPath()), responseHeaders, HttpStatus.OK);
	}

	private static List<File> convertFiles(MultipartFile[] multipartFiles) {
		List<File> files = Arrays.stream(multipartFiles)
					.map(x -> {
						try {
							return convert(x);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					})
					.collect(Collectors.toList());
		return files;
	}
	
	
	
	public static File convert(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile();
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}


	private List<File> getPdfFiles() {
		List<File> pdfFiles = new ArrayList<>();
		for(int i=1;i<=2;i++) {
			pdfFiles.add(new File("File"+i+".pdf"));
		}
		return pdfFiles;
	}
	
	
	
	@RequestMapping(value = "merge", method = RequestMethod.GET)
	public Merge getMerge() {
		Merge merge = new Merge();
		merge.setName("MergedFileName.pdf");
		return merge;
	}
	
	@RequestMapping(value = "ping", method = RequestMethod.GET)
	public String ping() {
		return PING_SUCCESS_RESPONSE;
	}
	
	@RequestMapping(value = "getPdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getPdf() throws Exception, IOException {
		File mergedFile = new File("merged.pdf");
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("charset", "utf-8");
	    responseHeaders.setContentType(MediaType.APPLICATION_PDF);
	    responseHeaders.set("Content-disposition", "attachment; filename=merged.pdf");
		return new ResponseEntity<byte[]>(Files.readAllBytes(mergedFile.toPath()), responseHeaders, HttpStatus.OK);
	}
}
