package com.xyz.microservices.user.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration(proxyBeanMethods=false)
public class RestTemplateConfig {
	
	private static final Logger log = LoggerFactory.getLogger(RestTemplateConfig.class);
	
	@Value("${rest-template.https.enabled}")
	private Boolean httpsEnabled;

    @Primary
    @Bean
    RestTemplate restTemplate() {
    	return getRestTemplate();
    }
	
	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		
		if(httpsEnabled.equals(Boolean.TRUE)) {
	    	TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
					return true;
				}
			};
			SSLContext sslContext = null;
			try {
				sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
				log.error(e.getMessage(),e);
			} 
	
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setReadTimeout(0);
			requestFactory.setConnectTimeout(0);
			requestFactory.setHttpClient(httpClient);
			restTemplate = new RestTemplate(requestFactory);
		}
		return  restTemplate;
	}

}
