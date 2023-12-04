/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.uniit.ricevute;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import it.csi.siac.siacbatch.batch.CmdLineOptions;
import it.csi.siac.siacbatch.batch.oil.uniit.BaseOilUniItBatch;
import it.csi.siac.siacbatch.util.LogHandler;

@Component
public class OilUniItRicevuteBatch extends BaseOilUniItBatch<OilUniItRicevuteBatchConfig>
{
	@Resource(name = "oilUniItRicevuteBatchConfig")
	public void setConfig(OilUniItRicevuteBatchConfig config) {
		super.config = config;
	}

	@Override
	protected void execute() throws Exception
	{
		CloseableHttpClient httpClient = getHttpClient();
		
		CloseableHttpResponse response = null;
		try {

			//System.setProperty("javax.net.ssl.keyStoreType", "jks");
			//System.setProperty("javax.net.ssl.trustStoreType", "jks");
			//System.setProperty("javax.net.ssl.keyStore", "D:/uniit/csi.keystore");
			//System.setProperty("javax.net.ssl.trustStore", "D:/uniit/client.truststore");
			//System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
			//System.setProperty("javax.net.ssl.trustStorePassword", "siacsiac");
			//System.setProperty("jdk.tls.disabledAlgorithms", "DHE");
//			System.setProperty("javax.net.debug", "all");

			
			String codiceEnte = getOptionValue(CmdLineOptions.CODICE_ENTE);
			String data = getOptionValue(OilUniItRicevuteCmdLineOptions.DATA);
			String downloadPath = getOptionValue(OilUniItRicevuteCmdLineOptions.PATH); 
			String[] tipi = getOptionValues(OilUniItRicevuteCmdLineOptions.TIPO);
			
			String url = String.format(config.getUrlPattern() + "&tipo=" + StringUtils.join(tipi, "&tipo="), codiceEnte, data, data);
			
			HttpGet httpGet = new HttpGet(url);
			
			httpGet.addHeader(HttpHeaders.ACCEPT, "application/zip");

			//LogHandler.logInfo("Executing request " + httpGet.toString());
			LogHandler.logInfo(String.format("Richiesta per codice ente %s con data %s...", codiceEnte, data));

			response = httpClient.execute(httpGet);
			
			StatusLine statusLine = response.getStatusLine();
			int status = statusLine.getStatusCode();
			
        	LogHandler.logInfo(String.format("HTTP status: %d %s", status, statusLine.getReasonPhrase()));
			
	        if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				
				if (entity != null) {
					String dataOraGenerazione = getHeader(response, "Date");
					
					if (dataOraGenerazione == null) {
						throw new IllegalStateException("Data/Ora generazione file non presente nella response HTTP");
					}
					
					String fileName = String.format("%s_%s.zip", convertDate(dataOraGenerazione), codiceEnte); 
	
					FileUtils.copyInputStreamToFile(entity.getContent(), new File(downloadPath + fileName));
					
					LogHandler.logInfo(String.format("File %s per codice ente %s con data %s, scaricato in %s", fileName, codiceEnte, data, downloadPath));
				} else {
					LogHandler.logInfo(String.format("Nessun file scaricato per l'ente %s con data %s", codiceEnte, data));
				}
	        } else {
	        	setExitCode(1);
	        }
		} finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
	}

	private String convertDate(String dataOraGenerazione) throws Exception
	{
		Date d = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US).parse(dataOraGenerazione);
		
		return new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss", Locale.ITALY).format(d);
	}

	private String getHeader(HttpResponse response, String headerName)
	{
		Header[] headers = response.getHeaders(headerName);
		
		if (headers != null && headers.length > 0)
			return headers[0].getValue();
		
		return null;
	}
}
