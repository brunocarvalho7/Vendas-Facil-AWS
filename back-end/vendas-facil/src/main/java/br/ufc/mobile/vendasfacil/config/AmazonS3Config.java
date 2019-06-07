package br.ufc.mobile.vendasfacil.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

@Configuration
public class AmazonS3Config {
	
	@Value("${aws.s3.region}")
	private String awsRegion;
	
	@Value("${aws.s3.bucket}")
	private String awsS3Bucket;
	
	@Bean(name = "awsRegion")
	public Region getAWSPollyRegion() {
        return Region.getRegion(Regions.fromName(awsRegion));
    }
	
	@Bean(name = "awsS3Bucket")
    public String getAWSS3Bucket() {
        return awsS3Bucket;
    }
	
}
