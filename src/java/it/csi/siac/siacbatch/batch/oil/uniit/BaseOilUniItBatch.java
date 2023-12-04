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

		
		
		
		
		
		
//		
//		for (Provider provider: Security.getProviders()) {
//			  System.out.println(provider.getName());
//			  for (String key: provider.stringPropertyNames())
//			    System.out.println("\t" + key + "\t" + provider.getProperty(key));
//			}
		
//		SSLSocketFactory socketFactory = SSLContext.getDefault().getSocketFactory();
//		for (String cipherSuite : socketFactory.getSupportedCipherSuites()) {
//		    System.out.println(cipherSuite);
//		}
		
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, config.getCertPassword().toCharArray()).build();
		
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1.2" },
				// null,
				new String[] {
//						"TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384",
//						"TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
//						"TLS_RSA_WITH_AES_256_CBC_SHA256",
//						"TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384",
//						"TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384",
//						"TLS_DHE_RSA_WITH_AES_256_CBC_SHA256",
//						"TLS_DHE_DSS_WITH_AES_256_CBC_SHA256",
//						"TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA",
//						"TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
//						"TLS_RSA_WITH_AES_256_CBC_SHA",
//						"TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA",
//						"TLS_ECDH_RSA_WITH_AES_256_CBC_SHA",
//						"TLS_DHE_RSA_WITH_AES_256_CBC_SHA",
//						"TLS_DHE_DSS_WITH_AES_256_CBC_SHA",
//						"TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256",
//						"TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
//						"TLS_RSA_WITH_AES_128_CBC_SHA256",
//						"TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256",
//						"TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256",
//						"TLS_DHE_RSA_WITH_AES_128_CBC_SHA256",
//						"TLS_DHE_DSS_WITH_AES_128_CBC_SHA256",
//						"TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
//						"TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
//						"TLS_RSA_WITH_AES_128_CBC_SHA",
//						"TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA",
//						"TLS_ECDH_RSA_WITH_AES_128_CBC_SHA",
//						"TLS_DHE_RSA_WITH_AES_128_CBC_SHA",
//						"TLS_DHE_DSS_WITH_AES_128_CBC_SHA",
//						"TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
//						"TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
						"TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
//						"TLS_RSA_WITH_AES_256_GCM_SHA384",
//						"TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384",
//						"TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384",
//						"TLS_DHE_RSA_WITH_AES_256_GCM_SHA384",
//						"TLS_DHE_DSS_WITH_AES_256_GCM_SHA384",
//						"TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
//						"TLS_RSA_WITH_AES_128_GCM_SHA256",
//						"TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256",
//						"TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256",
//						"TLS_DHE_RSA_WITH_AES_128_GCM_SHA256",
//						"TLS_DHE_DSS_WITH_AES_128_GCM_SHA256",
//						"TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA",
//						"TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA",
//						"SSL_RSA_WITH_3DES_EDE_CBC_SHA",
//						"TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA",
//						"TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA",
//						"SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA",
//						"SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA",
//						"TLS_EMPTY_RENEGOTIATION_INFO_SCSV",
//						"TLS_DH_anon_WITH_AES_256_GCM_SHA384",
//						"TLS_DH_anon_WITH_AES_128_GCM_SHA256",
//						"TLS_DH_anon_WITH_AES_256_CBC_SHA256",
//						"TLS_ECDH_anon_WITH_AES_256_CBC_SHA",
//						"TLS_DH_anon_WITH_AES_256_CBC_SHA",
//						"TLS_DH_anon_WITH_AES_128_CBC_SHA256",
//						"TLS_ECDH_anon_WITH_AES_128_CBC_SHA",
//						"TLS_DH_anon_WITH_AES_128_CBC_SHA",
//						"TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA",
//						"SSL_DH_anon_WITH_3DES_EDE_CBC_SHA",
//						"SSL_RSA_WITH_DES_CBC_SHA",
//						"SSL_DHE_RSA_WITH_DES_CBC_SHA",
//						"SSL_DHE_DSS_WITH_DES_CBC_SHA",
//						"SSL_DH_anon_WITH_DES_CBC_SHA",
//						"SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
//						"SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
//						"SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA",
//						"SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA",
//						"TLS_RSA_WITH_NULL_SHA256",
//						"TLS_ECDHE_ECDSA_WITH_NULL_SHA",
//						"TLS_ECDHE_RSA_WITH_NULL_SHA",
//						"SSL_RSA_WITH_NULL_SHA",
//						"TLS_ECDH_ECDSA_WITH_NULL_SHA",
//						"TLS_ECDH_RSA_WITH_NULL_SHA",
//						"TLS_ECDH_anon_WITH_NULL_SHA",
//						"SSL_RSA_WITH_NULL_MD5",
//						"TLS_KRB5_WITH_3DES_EDE_CBC_SHA",
//						"TLS_KRB5_WITH_3DES_EDE_CBC_MD5",
//						"TLS_KRB5_WITH_DES_CBC_SHA",
//						"TLS_KRB5_WITH_DES_CBC_MD5",
//						"TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA",
//						"TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5"
						},
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







/*   

for i in $(openssl ciphers 'ALL:eNULL' | sed -e 's/:/ /g') ; do echo $i ; openssl s_client -cipher "$i" -connect ordinativo-pre.argentea.it:443  ; done | less



*/
