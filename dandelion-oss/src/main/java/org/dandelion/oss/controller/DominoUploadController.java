package org.dandelion.oss.controller;

import org.dandelion.oss.dto.OSSObjectSummaryDTO;
import org.dandelion.oss.service.FmService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author L
 * @version 1.0
 * @date 2021/12/8 14:55
 */
@RestController
public class DominoUploadController {

    @Value("${aliyun.oss.bucket.name}")
    private String bucketName;

    private final FmService fmService;

    DominoUploadController(FmService fmService) {
        this.fmService = fmService;
    }

    /**
     * 获取桶下文件信息
     *
     * @param keyPrefix 列举包含指定前缀的文件
     * @return List<OSSObjectSummaryDTO>
     * @author L
     */
    @GetMapping("/getBucketList")
    private List<OSSObjectSummaryDTO> getBucketList(@RequestParam(required = false) String keyPrefix) {
        System.out.println("========== 请求了");
        return fmService.getBucketList(bucketName, keyPrefix);
    }
}
