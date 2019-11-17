package com.atguigu.scw.project.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class OSSTemplate {
	String prefix;
	String endpoint;
	String accessKeyId;
	String accessKeySecret;
	String bucketName;
	String belongFile;
	
	public String uploadFile(MultipartFile file)  {

		// Endpoint以杭州为例，其它Region请按实际情况填写。

		// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
		// https://ram.console.aliyun.com 创建。

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(prefix + endpoint, accessKeyId, accessKeySecret);

		// 上传文件流。
		
		InputStream inputStream=null;
		String path=null;
		try {
			inputStream = file.getInputStream();
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String objectName = System.currentTimeMillis()+"_"+uuid+"_"+file.getOriginalFilename();
			ossClient.putObject(bucketName, belongFile + objectName, inputStream);
			// https://scw-xc.oss-cn-shanghai.aliyuncs.com/javeEE.jpg
			path = prefix + endpoint + "." + endpoint + "/" + belongFile + objectName;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			// 关闭OSSClient。
			ossClient.shutdown();
		}
		log.debug("地址是：{}",path); 
		return path;
	}
}
