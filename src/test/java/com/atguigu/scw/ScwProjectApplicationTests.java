package com.atguigu.scw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ScwProjectApplicationTests {

	@Test
	public void contextLoads() throws FileNotFoundException {
	/*	
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String prefix="http://";
		String endpoint = "oss-cn-shanghai.aliyuncs.com";
		// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
		// https://ram.console.aliyun.com 创建。
		String accessKeyId = "LTAI4FbnrsYnAxm9zj77SMW3";
		String accessKeySecret = "svi2VrWNq2g7Ucii1MysgQdXrhpZJ9";

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(prefix+endpoint, accessKeyId, accessKeySecret);
		String bucketName="scw-xc";
		String belongFile="imgs/";
		String objectName="javeEE.jpg";
		// 上传文件流。
		InputStream inputStream = new FileInputStream(new File("C:\\Users\\CHU-XU\\Desktop\\JavaEE.jpg"));
		ossClient.putObject(bucketName,belongFile+objectName, inputStream);

		//https://scw-xc.oss-cn-shanghai.aliyuncs.com/javeEE.jpg
		String path=prefix+endpoint+"."+endpoint+"/"+belongFile+objectName;
		log.debug("图片地址是：{}",path);
		// 关闭OSSClient。
		ossClient.shutdown();*/
	}

}
