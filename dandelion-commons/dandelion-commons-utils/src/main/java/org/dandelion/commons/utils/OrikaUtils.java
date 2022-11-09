package org.dandelion.commons.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 对象复制
 *
 * @author L
 * @version 1.0
 * @date 2021/9/27 13:48
 */
public class OrikaUtils {

    /**
     *
     */
    private static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    /**
     * copy测试
     *
     * @author L
     */
    public static void mapperFactory() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Test01.class, Test02.class)
            // 名称不相同需要指定
            .field("age", "ages")
            .byDefault()
            .register();

        Test01 test01 = new Test01();
        test01.setId(0);
        test01.setName("张三");
        test01.setAge(2);

        // 映射
        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        Test02 test02 = mapperFacade.map(test01, Test02.class);
        System.out.println(test02.toString());

    }

    /**
     * 实体对象转换 注：字段相同
     *
     * @param sourceEnt 源
     * @param tClass    目标类
     * @param <S>       源泛型
     * @param <T>       目标泛型
     * @return 目标类
     * @author L
     */
    public static <S, T> T conversion(S sourceEnt, Class<T> tClass) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory.getMapperFacade().map(sourceEnt, tClass);
    }

    /**
     * 实体对象转换 注：字段不同
     *
     * @param sClass 源
     * @param tClass 目标
     * @param <S>    目标泛型
     * @param <T>    目标泛型
     * @return 目标类
     * @author L
     */
    public static <S, T> T conversionInconsistent(S sClass, Class<T> tClass, Map<String, String> map) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        register(sClass.getClass(), tClass, map, mapperFactory);
        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        return mapperFacade.map(sClass, tClass);

    }

    /**
     * 字段注册
     *
     * @param sClass        源
     * @param tClass        目标
     * @param map           注册字段
     * @param mapperFactory .
     * @param <S>           源
     * @param <T>           目标
     * @author L
     */
    private static <S, T> void register(Class<S> sClass, Class<T> tClass, Map<String, String> map, MapperFactory mapperFactory) {
        if (null == map || map.isEmpty()) {
            return;
        }
        ClassMapBuilder<?, T> tClassMapBuilder = mapperFactory.classMap(sClass, tClass);
        map.forEach(tClassMapBuilder::field);
        tClassMapBuilder.byDefault().register();
    }

    /**
     * 集合转换
     *
     * @param sList  源
     * @param tClass 目标
     * @param <S>    源泛型
     * @param <T>    目标泛型
     * @return 目标类
     * @author L
     */
    public static <S, T> List<T> conversionList(List<S> sList, Class<T> tClass, Map<String, String> map) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        if (null != sList && sList.size() > 0) {
            register(sList.get(0).getClass(), tClass, map, mapperFactory);
        }
        return mapperFactory.getMapperFacade().mapAsList(sList, tClass);
    }


    public static void main(String[] args) {
//        mapperFactory();

        Test01 test01 = new Test01();
        test01.setId(0);
        test01.setName("张三");
        test01.setAge(2);
        test01.setPhone("111111");

        System.out.println("------------- 分割线 测试 对象转换 -------------");
        System.out.println(conversion(test01, Test02.class).toString());
        Map<String, String> map1 = new HashMap<>();
        map1.put("age", "ages");
        Test02 test02 = conversionInconsistent(test01, Test02.class, map1);
        System.out.println(test02.getPhone());
        System.out.println("------------- 分割线 测试 集合转换-------------");
        List<Test01> test01s = new ArrayList<>();
        test01s.add(test01);
        System.out.println(conversionList(test01s, Test02.class, null));
        System.out.println(conversionList(test01s, Test02.class, map1));

//        BeanUtil.copyProperties();

    }


}

class Test01 {
    int id;
    String name;
    /**
     * 测试使用 同上名称不同
     */
    int age;
    String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


class Test02 extends Test03 {
    int id;
    String name;
    /**
     * 测试使用 同上名称不同
     */
    int ages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAges() {
        return ages;
    }

    public void setAges(int ages) {
        this.ages = ages;
    }
}

class Test03 {
    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
