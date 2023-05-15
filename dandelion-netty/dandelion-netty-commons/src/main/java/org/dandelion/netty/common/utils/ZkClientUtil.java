package org.dandelion.netty.common.utils;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO zk client util
 *
 * @author L
 * @version 1.0
 * @date 2022/5/13 17:04
 */
public class ZkClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZkClientUtil.class);

    @Resource
    private ZkClient zkClient;

    public ZkClientUtil() {

    }

    /**
     * 所有注册节点
     */
    private List<String> allNode=new ArrayList<>();

    /**
     * 创建父级节点
     *
     * @param root 节点名称
     * @author L
     */
    public void createRootNode(String root) {
        boolean exists = zkClient.exists(root);
        if (exists) {
            return;
        }
        //创建 root
        zkClient.createPersistent(root);
    }

    /**
     * 写入指定节点 临时目录
     *
     * @param path 节点名称
     * @author L
     */
    public void createEphemeralNode(String path) {
        zkClient.createEphemeral(path);
    }

    /**
     * 获取所有注册节点
     *
     * @author L
     */
    public List<String> getAllNode() {
        logger.info("查询所有节点成功，节点数：" + allNode.size());
        return allNode;
    }

    /**
     * 更新server list
     *
     * @param allNode 所有节点
     * @author L
     */
    public void setAllNode(List<String> allNode) {
        logger.info("server节点更新，节点数：" + allNode.size());
        this.allNode = allNode;
    }


}
