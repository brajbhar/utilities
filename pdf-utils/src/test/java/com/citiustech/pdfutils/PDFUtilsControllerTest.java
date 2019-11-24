package com.citiustech.pdfutils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.citiustech.pdfutils.controller.PDFUtilsController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PDFUtilsControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private PDFUtilsController controller;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testMergePdf() {
		File file1 = new File("File1.pdf");
		
	}
	
	@Test
	public void testPing() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/ping"))
				.andExpect(status().isOk())
				.andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		Assert.assertTrue(response.equalsIgnoreCase(PDFUtilsController.PING_SUCCESS_RESPONSE));
	}

}
