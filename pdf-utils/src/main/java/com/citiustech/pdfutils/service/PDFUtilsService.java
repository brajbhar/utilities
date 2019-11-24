/**
 * 
 */
package com.citiustech.pdfutils.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.DocumentException;

/**
 * @author bablur
 *
 */
public interface PDFUtilsService {
	
	public File merge(List<File> files) throws DocumentException, IOException;

}
