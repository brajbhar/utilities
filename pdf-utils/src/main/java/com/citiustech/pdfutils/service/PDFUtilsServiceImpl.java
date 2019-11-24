package com.citiustech.pdfutils.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;

@Service
public class PDFUtilsServiceImpl implements PDFUtilsService {

	@Override
	public File merge(List<File> files) throws DocumentException, IOException {
		Document document = new Document();
		File mergedFile = new File("merged.pdf");
		FileOutputStream outputStream = new FileOutputStream(mergedFile);
		PdfCopy copy = new PdfSmartCopy(document, outputStream);
		document.open();
		for(File inFile : files) {
			PdfReader reader = new PdfReader(inFile.getAbsolutePath());
			copy.addDocument(reader);
			reader.close();
		}
		
		document.close();
		return mergedFile;
	}

}
