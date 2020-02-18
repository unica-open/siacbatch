/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.uniit.caricoflussi;

import java.io.File;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.CmdLineOptions;
import it.csi.siac.siacbatch.batch.oil.uniit.BaseOilUniItBatch;
import it.csi.siac.siacbatch.util.LogHandler;

@Component
public class OilUniItCaricoFlussiBatch extends BaseOilUniItBatch<OilUniItCaricoFlussiBatchConfig>
{
	@Resource(name = "oilUniItCaricoFlussiBatchConfig")
	public void setConfig(OilUniItCaricoFlussiBatchConfig config) {
		super.config = config;
	}
	
	@Override
	protected void execute() throws Exception
	{
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse response = null;

		try {
			
			String codiceEnte = getOptionValue(CmdLineOptions.CODICE_ENTE);
			String filePath = getOptionValue(OilUniItCaricoFlussiCmdLineOptions.PATH);
			
			String url = String.format(config.getUrl(), codiceEnte);
			

			HttpPost httpPost = new HttpPost(url);
			
		    MultipartEntityBuilder builder = addFilesMultipart(filePath);
		    
		    HttpEntity multipart = builder.build();
		 
		    httpPost.setEntity(multipart);
		 
		    response = httpClient.execute(httpPost);
		    
		    LogHandler.logError("response: " + response.toString());

		} finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
	}

	private MultipartEntityBuilder addFilesMultipart(String filePath)
	{
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();

		Collection<File> fileList = FileUtils.listFiles(new File(filePath), new String[]{"xml"}, false);
		
		for (File file : fileList) {
			LogHandler.logInfo("File: " + file.getAbsolutePath());
			builder.addBinaryBody("file", file, ContentType.APPLICATION_XML, "file");
		}
		
		return builder;
	}
}
