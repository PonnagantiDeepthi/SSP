package ug.daes.onboarding;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ug.daes.DAESService;
import ug.daes.PKICoreServiceException;
import ug.daes.Result;

//import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
@EnableAsync
@SpringBootApplication
public class NewOnBoardingApplication {

	final static private Logger logger = LoggerFactory.getLogger(NewOnBoardingApplication.class);
	
	/** The Constant CLASS. */
	final static String CLASS = "NewOnBoardingApplication";
	
	public static void main(String[] args) {
		SpringApplication.run(NewOnBoardingApplication.class, args);
	}
			
	
	 
	
	@Bean
	public void signatueServiceInitilize() {
		try {
			Result result = DAESService.initPKINativeUtils();
			if(result.getStatus() == 0) {
				logger.info(CLASS + " >> signatueServiceInitilize() >>"+ new String(result.getStatusMessage()));
				System.out.println(new String(result.getStatusMessage()));
			}else {
				logger.info(CLASS + " >> signatueServiceInitilize() >>"+ new String(result.getResponse()));
				System.out.println(new String(result.getResponse()));
			}
		} catch (PKICoreServiceException e) {
			e.printStackTrace();
			logger.error(CLASS + " >> signatueServiceInitilize() >> PKICoreServiceException >> "+  e.getMessage());
		}
	}
	
	 @Bean
	    public RestTemplate restTemplate()
	            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
	        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

	        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
	                .loadTrustMaterial(null, acceptingTrustStrategy)
	                .build();

	        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

	        CloseableHttpClient httpClient = HttpClients.custom()
	                .setSSLSocketFactory(csf)
	                .build();

	        HttpComponentsClientHttpRequestFactory requestFactory =
	                new HttpComponentsClientHttpRequestFactory();
	        
	        requestFactory.setConnectionRequestTimeout(300000);
			requestFactory.setConnectTimeout(300000);
			requestFactory.setReadTimeout(300000);
			requestFactory.setHttpClient(httpClient);

	        
	        RestTemplate restTemplate = new RestTemplate(requestFactory);
	        return restTemplate;
	    }
	 
	 @Bean
	 public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	     ObjectMapper mapper = new ObjectMapper();
	     mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	     MappingJackson2HttpMessageConverter converter = 
	         new MappingJackson2HttpMessageConverter(mapper);
	     return converter;
	 }

}
