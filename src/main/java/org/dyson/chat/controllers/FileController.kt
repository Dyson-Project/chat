package org.dyson.chat.controllers

import io.minio.MinioAsyncClient
import io.minio.UploadObjectArgs
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream


@RestController
@RequestMapping("/api/v1/files")
class FileController(val fileClient: MinioAsyncClient) {
    val log = LoggerFactory.getLogger(FileController::class.java)

    @Value("\${minio.bucket.name}")
    var defaultBucketName: String? = null

    @Value("\${minio.default.folder}")
    var defaultBaseFolder: String? = null

    @PostMapping
    fun postFile(@RequestParam("file") mpFile: MultipartFile): ResponseEntity<Void> {
        // Normalize file name
        val fileName: String = StringUtils.cleanPath(mpFile.getOriginalFilename())

        val tmpFile = File("/tmp/" + fileName)
        val fileOutputStream = FileOutputStream(tmpFile)
        fileOutputStream.write(mpFile.bytes)

        fileClient.uploadObject(
            UploadObjectArgs.builder()
                .bucket(defaultBucketName)
                .`object`(defaultBaseFolder + fileName)
                .filename(tmpFile.absolutePath)
                .build()
        ).get()
        return ResponseEntity.noContent().build();
    }
}
