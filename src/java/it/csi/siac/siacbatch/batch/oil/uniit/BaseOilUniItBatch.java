/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siacbatch.batch.oil.uniit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import it.csi.siac.siacbatch.batch.BaseBatch;


public abstract class BaseOilUniItBatch<CONF extends BaseOilUniItBatchConfig> extends BaseBatch
{
	protected CONF config;
	
	// https://stackoverflow.com/questions/15201251/how-i-can-tell-alias-of-the-wanted-key-entry-to-sslsocket-before-connecting
	// https://alesaudate.wordpress.com/2010/08/09/how-to-dynamically-select-a-certificate-alias-when-invoking-web-services/
	
	protected CloseableHttpClient getHttpClient() throws KeyStoreException, FileNotFoundException, IOException,
			NoSuchAlgorithmException, CertificateException, KeyManagementException, UnrecoverableKeyException
	{
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

		FileInputStream instream = new FileInputStream(new File(config.getKeyStorePath()));

		try
		{
			keyStore.load(instream, config.getKeyStorePassword().toCharArray());
		}
		finally
		{
			instream.close();
		}

		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, config.getCertPassword().toCharArray()).build();
		
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
				// null,
				new String[] { "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA" },
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		httpClientBuilder.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 
		httpClientBuilder.setSSLSocketFactory(sslsf);
		
		String proxyHost = System.getProperty("https.proxyHost"); 
		String proxyPort = System.getProperty("https.proxyPort");
	
		if (StringUtils.isNotEmpty(proxyHost) && StringUtils.isNotEmpty(proxyPort)) {
			HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort), "http");
			httpClientBuilder.setProxy(proxy);
		}
		
		return httpClientBuilder.build();
	}
	
}
