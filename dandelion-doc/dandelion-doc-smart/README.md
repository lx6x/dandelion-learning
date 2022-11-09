# dandelion-doc-smart -- api 接口文档

## 模块结构

```
    .../dandelion-doc-smart
        ../dandelion-doc-smart-single-module               maven单模块中使用插件
        ../dandelion-doc-smart-multi-module                maven多模块中使用插件
```

## 模块服务端口

```
    .../dandelion-doc-smart                   
```

### dandelion-doc-smart-single-module

**单模块 maven 使用**

* 使用插件
    * pom中配置

  ```
   <plugin>
       <groupId>com.github.shalousun</groupId>
       <artifactId>smart-doc-maven-plugin</artifactId>
       <version>[最新版本]</version>
       <configuration>
           <!--指定生成文档的使用的配置文件,配置文件放在自己的项目中-->
           <configFile>./src/main/resources/smart-doc.json</configFile>
           <!--指定项目名称-->
           <projectName>测试</projectName>
           <!--smart-doc实现自动分析依赖树加载第三方依赖的源码，如果一些框架依赖库加载不到导致报错，这时请使用excludes排除掉-->
           <excludes>
               <!--格式为：groupId:artifactId;参考如下-->
               <exclude>com.alibaba:fastjson</exclude>
           </excludes>
           <!--自1.0.8版本开始，插件提供includes支持,配置了includes后插件会按照用户配置加载而不是自动加载，因此使用时需要注意-->
           <!--smart-doc能自动分析依赖树加载所有依赖源码，原则上会影响文档构建效率，因此你可以使用includes来让插件加载你配置的组件-->
           <includes>
               <!--格式为：groupId:artifactId;参考如下-->
               <include>com.alibaba:fastjson</include>
           </includes>
       </configuration>
       <executions>
           <execution>
               <!--如果不需要在执行编译时启动smart-doc，则将phase注释掉-->
               <phase>compile</phase>
               <goals>
                   <!--smart-doc提供了html、openapi、markdown等goal，可按需配置-->
                   <goal>html</goal>
               </goals>
           </execution>
       </executions>
   </plugin> 
  ```

    * 添加 smart-doc.json 生成文档的配置

  ```
  {
   "outPath": "D://md2" //指定文档的输出路径，此项为必填项
  }
  ```

注：这里只列出简单样例，具体参考官方文档 - `https://gitee.com/smart-doc-team/smart-doc/wikis`

### dandelion-doc-smart-multi-module

**maven 多模块使用插件**

* 完全父子级项目
* 在父项目中【parent】 `pom.xml` 添加同上 `plugin`
* 在索要生成文档的子项目中 `pom.xml` 添加对应 `plugin`
* 在子项目中添加对应 `smart-doc.json` 文件
* 执行命令 如： `mvn -X smart-doc:html -Dfile.encoding=UTF-8 -pl :smart-multi-01 -am`
* 注：测试项目 【 smart-multi-01 - smart-multi-02】

## 版本

```

1.0.0 初始版本

```
