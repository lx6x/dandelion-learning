package org.dandelion.commons.utils.sftp;

import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022-12-29
 */
public class Sftp {

    public static void main(String[] args) {
        /*SftpProperties properties=new SftpProperties();
        properties.setHost("172.16.5.103");
        properties.setPort(22);
        properties.setPassword("lenovo");
        properties.setUsername("testsftp");

        SftpProperties.PoolConfig poolConfig=new SftpProperties.PoolConfig();
        poolConfig.setMaxIdle(10000);
        poolConfig.setMaxTotal(50);
        poolConfig.setMinIdle(2);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnCreate(false);
        poolConfig.setTestOnReturn(false);
        poolConfig.setTestWhileIdle(false);
        poolConfig.setBlockWhenExhausted(false);
        poolConfig.setMaxWaitMillis(10000);
        poolConfig.setTimeBetweenEvictionRunsMillis(1000);
        properties.setPool(poolConfig);

        SftpClient client;
        try {
            client = new SftpClient(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<String> files = client.findFiles("/crm/sync/flow");
        System.out.println(files);*/

    }

}