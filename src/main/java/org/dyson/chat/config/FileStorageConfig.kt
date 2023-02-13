package org.dyson.chat.config

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioAsyncClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class FileStorageConfig {
    @Value("\${minio.username}")
    var accessKey: String? = null

    @Value("\${minio.secretkey}")
    var secretKey: String? = null

    @Value("\${minio.url}")
    var minioUrl: String? = null

    @Value("\${minio.bucket.name}")
    var defaultBucketName: String? = null

    @Bean
    open fun fileStorageClient(): MinioAsyncClient {
        val client = MinioAsyncClient.builder()
            .endpoint(minioUrl)
            .credentials(accessKey, secretKey).build()
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(defaultBucketName).build()).get()) {
            client.makeBucket(MakeBucketArgs.builder().bucket(defaultBucketName).build())
        }
        return client;
    }

}
