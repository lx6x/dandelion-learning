package org.dandelion.commons.mybatisgenerator.plus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

/**
 * <a href="https://baomidou.com/pages/981406/#%E7%AD%96%E7%95%A5%E9%85%8D%E7%BD%AE-strategyconfig">mybatis plus generator</a>
 *
 * @author liujunfei
 * @date 2023/7/14
 */
public class MybatisPlusGenerator {

    private static final String[] TABLE = {"sys_menus"};


    public static void main(String[] args) {

        String userDir = System.getProperty("user.dir");
        System.out.println("userDir=" + userDir);

        FastAutoGenerator.create(
                        "jdbc:mysql://localhost:10013/dandelion?useSSL=false&autoReconnect=true&characterEncoding=utf8",
                        "root",
                        "root")
                // 全局配置
                .globalConfig(builder ->
                                builder.author("lx6x") // 设置作者
//                                .enableSwagger() // 开启 swagger 模式
                                        .enableSpringdoc()
                                        .outputDir(userDir + "/dandelion-mybatis-generator/src/main/java") // 指定生成目录
                                        .commentDate("yyyy/MM/dd")

                )
                .dataSourceConfig(builder -> {
                })
                // 模板配置
                .templateConfig(builder -> {
                    builder.controller("");
                })
                // 包配置
                .packageConfig(builder ->
                        builder
                                .parent("mybatisplus") // 设置父包名
                                .entity("domain")
                                .mapper("mapper")
                                .service("service")
                                .serviceImpl("service.impl")
//                                .controller("controller")
                )
                .strategyConfig(builder ->
                        builder.addInclude(TABLE)
                                .addTablePrefix() // 设置过滤的前缀
//                                .controllerBuilder().enableRestStyle() // RestController
                                .mapperBuilder().enableBaseResultMap()
                                .entityBuilder().enableLombok()
                )
                .execute();


    }
}
