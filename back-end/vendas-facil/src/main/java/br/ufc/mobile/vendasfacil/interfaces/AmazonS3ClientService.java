package br.ufc.mobile.vendasfacil.interfaces;

import org.springframework.web.multipart.MultipartFile;

import br.ufc.mobile.vendasfacil.model.Produto;

public interface AmazonS3ClientService {

	void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess, Produto produto);
	
}
