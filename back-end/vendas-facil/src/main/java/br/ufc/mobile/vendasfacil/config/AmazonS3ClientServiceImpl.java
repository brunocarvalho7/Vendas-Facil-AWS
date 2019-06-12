package br.ufc.mobile.vendasfacil.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.ufc.mobile.vendasfacil.interfaces.AmazonS3ClientService;
import br.ufc.mobile.vendasfacil.model.Produto;

@Component
public class AmazonS3ClientServiceImpl implements AmazonS3ClientService {

	private String awsS3Bucket;
    private AmazonS3 amazonS3;
    private static final Logger logger = LoggerFactory.getLogger(AmazonS3ClientServiceImpl.class);
	
    @Autowired
    public AmazonS3ClientServiceImpl(Region awsRegion, String awsS3Bucket) 
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(awsRegion.getName()).build();
        this.awsS3Bucket = awsS3Bucket;
    }

	@Override
	@Async
	public void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess, Produto produto) {
		String fileName = produto.getId() + ".jpg";
			
        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            
            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3Bucket, "produtos/" + fileName, file);

            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3.putObject(putObjectRequest);
            
            System.out.println("Fazendo upload do arquivo " + fileName);
            System.out.println("https://vendas-facil.s3.amazonaws.com/produtos/" + fileName);
            System.out.println("https://vendas-facil.s3.amazonaws.com/thumbs-produtos/" + fileName);
            
            //removing the file created in the server
            file.delete();
        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
        }
		
	}

}
