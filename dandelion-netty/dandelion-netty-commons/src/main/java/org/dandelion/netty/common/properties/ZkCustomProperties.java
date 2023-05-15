package org.dandelion.netty.common.properties;

/**
 * TODO zk config message
 *
 * @author LJF
 * @version 1.0
 * @date 2022/05/14 17:21
 */
//@PropertySource(value = "classpath:/application.properties")
public class ZkCustomProperties {

    /**
     * init root
     */
    private String root;

    /**
     * ip + port
     */
    private String adder;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getAdder() {
        return adder;
    }

    public void setAdder(String adder) {
        this.adder = adder;
    }
}
