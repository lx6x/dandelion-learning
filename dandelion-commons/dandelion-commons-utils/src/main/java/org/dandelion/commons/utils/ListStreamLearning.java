package org.dandelion.commons.utils;

import org.dandelion.commons.model.entity.utils.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO list stream 使用
 *
 * @author L
 * @version 1.0
 * @date 2022/2/22 11:18
 */
public class ListStreamLearning {

    public static void main(String[] args) {

        List<Person> list;

        Person p1 = new Person("a", 11, "aaaaa");
        Person p2 = new Person("b", 12, "bbbbb");
        Person p3 = new Person("c", 13, "ccccc");
        Person p4 = new Person("d", 14, "ddddd");
        Person p5 = new Person("e", 15, "eeeee");

        list = Arrays.asList(p1, p2, p3, p4, p5);


//        filterTest(list);
        mapTest(list);
//        flatMapTest(list);
//        reduceTest();
//        collectTest(list);
    }

    private static void println(List<Person> list) {
        for(Person p:list) {
            System.out.println(p.getName()+"-"+p.getAge()+"-"+p.getProvince());
        }
    }

    // filter age > 13 and name = d
    private static void filterTest(List<Person> list) {
        List<Person> temp = new ArrayList<Person>();
        for(Person p:list) {
            if (p.getAge() > 13 && "d".equalsIgnoreCase(p.getName())) {
                temp.add(p);
            }
        }
        println(temp);

        List<Person> collect = list
                .stream()
                .filter(p->(p.getAge() > 13 && "d".equalsIgnoreCase(p.getName())))
                .collect(Collectors.toList());
        println(collect);

        List<Person> collect1 = list
                .stream()
                .filter(p->{
                    if (p.getAge() > 13 && "d".equalsIgnoreCase(p.getName())) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        println(collect1);
    }

    // map
    private static void mapTest(List<Person> list) {
        List<String> temp = new ArrayList<>();
        for(Person p:list) {
            temp.add(p.getName());
        }
        System.out.println("temp="+temp.toString());

        List<String> collect = list
                .stream()
                .map(p->p.getName())
                .collect(Collectors.toList());
        System.out.println("collect="+collect);

        List<String> collect1 = list
                .stream()
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("collect1="+collect1);

        List<String> collect2 = list
                .stream()
                .map(p->{
                    return p.getName();
                }).collect(Collectors.toList());
        System.out.println("collect2="+collect2);
    }

    // flatMap
    private static void flatMapTest(List<Person> list) {
        List<String> collect = list
                .stream()
                .flatMap(person -> Arrays.stream(person.getName().split(" ")))
                .collect(Collectors.toList());
        System.out.println("collect="+collect);

        List<Stream<String>> collect1 = list
                .stream()
                .map(person -> Arrays.stream(person.getName().split(" ")))
                .collect(Collectors.toList());
        System.out.println("collect1="+collect1);

        List<String> collect2 = list
                .stream()
                .map(person -> person.getName().split(" "))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        System.out.println("collect2="+collect2);

        List<String> collect3 = list
                .stream()
                .map(person -> person.getName().split(" "))
                .flatMap(str -> Arrays.asList(str).stream())
                .collect(Collectors.toList());
        System.out.println("collect3="+collect3);
    }

    // reduce
    public static void reduceTest(){
        Integer reduce = Stream.of(1, 2, 3, 4)
                .reduce(10, (count, item) ->{
                    System.out.println("count:"+count);
                    System.out.println("item:"+item);
                    return count + item;
                } );
        System.out.println(reduce);

        Integer reduce1 = Stream.of(1, 2, 3, 4)
                .reduce(1, (x, y) -> x + y);
        System.out.println(reduce1);

        String reduce2 = Stream.of("1", "2", "3")
                .reduce("1", (x, y) -> (x + "," + y));
        System.out.println(reduce2);
    }

    /**
     * toList
     */
    public static void collectTest(List<Person> list){
        List<String> collect = list.stream()
                .map(Person::getName)
                .collect(Collectors.toList());

        Set<String> collect1 = list.stream()
                .map(Person::getName)
                .collect(Collectors.toSet());

        Map<String, Integer> collect2 = list.stream()
                .collect(Collectors.toMap(Person::getName, Person::getAge));
        Map<String, String> collect3 = list.stream()
                .collect(Collectors.toMap(p->p.getName(), value->{
                    return value+"1";
                }));
        for (Map.Entry<String, String> entry : collect3.entrySet()) {
            System.out.println("key=" + entry.getKey() + ",value=" + entry.getValue());
        }

        //TreeSet<Person> collect4 = list.stream()
        //        .collect(Collectors.toCollection(TreeSet::new));
        //System.out.println(collect4);

        Map<Boolean, List<Person>> collect5 = list.stream()
                .collect(Collectors.groupingBy(p->"d".equalsIgnoreCase(p.getName())));
        System.out.println(collect5);

        String collect6 = list.stream()
                .map(p->p.getName())
                .collect(Collectors.joining(",", "{", "}"));
        System.out.println(collect6);

        List<String> collect7 = Stream.of("1", "2", "3").collect(
                Collectors.reducing(new ArrayList<String>(), x -> Arrays.asList(x), (y, z) -> {
                    y.addAll(z);
                    return y;
                }));
        System.out.println(collect7);
    }
}
