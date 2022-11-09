package org.dandelion.oss.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.dandelion.commons.utils.OrikaUtils;
import org.dandelion.oss.config.OssHangZhouConfig;
import org.dandelion.oss.dto.OSSObjectSummaryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 文件管理
 *
 * @author L
 * @version 1.0
 * @date 2021/12/8 17:09
 */
@Service
public class FmService {

    private static final Logger logger = LoggerFactory.getLogger(FmService.class);

    private final OssHangZhouConfig ossConfig;

    FmService(OssHangZhouConfig ossConfig) {
        this.ossConfig = ossConfig;
    }

    /**
     * 用于通过GetBucket (ListObjects)方法列举指定存储空间下的文件，默认列举100个文件
     *
     * @param bucketName 桶
     * @param keyPrefix  列举包含指定前缀的文件
     * @author L
     */
    public List<OSSObjectSummaryDTO> getBucketList(String bucketName, String keyPrefix) {
        OSS ossClient = ossConfig.ossClient();

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        if(null!=keyPrefix){
            listObjectsRequest.setPrefix(keyPrefix);
        }


        // 列举文件。如果不设置keyPrefix，则列举存储空间下的所有文件。如果设置keyPrefix，则列举包含指定前缀的文件。
        ObjectListing objectListing = ossClient.listObjects(listObjectsRequest);

        logger.info("列举文件返回-1：{}", JSONObject.toJSONString(objectListing));
        List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        logger.info("列举文件返回-2：{}", JSONArray.toJSONString(objectSummaries));
        return OrikaUtils.conversionList(objectSummaries, OSSObjectSummaryDTO.class, null);
    }
}
