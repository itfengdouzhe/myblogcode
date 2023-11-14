//package com.javatiaocao.myblog.controller;
//
//import lombok.Data;
//
//import java.util.List;
//
//@Data
//class Person{
//        String name;
//    int age;
//
//    public Person(String name, int age) {
//        this.name = name;
//        this.age = age;
//    }
//}
//
//class RemoveDuplicates{
//    static List<Person> of(List<Person>ss){
//        //Ideally, we would like to just write
//        //""return ss.stream().distinct().toList();""
//        //But Person.equals uses name.equals internally, and
//        //we need to use name.equalsIgnoreCase instead!
//        //Hint: model solution is 6 lines and overrides 3 methods
//        return ss.stream().map(s->new Person( ){
////
//        }).distinct().map(s->s.get()).toList();
//    }
//}
//public class Exercise {
//    public static void main(String[]a) {
//        List<Person> data1 = List.of(new Person("Bob",15), new Person("Bob",20));
//        List<Person> data2 = List.of(new Person("Bob",15), new Person("bob",15));
//        List<Person> data3 = List.of(new Person("Bob",15), new Person("bob",20));
//        List<Person> data4 = List.of(new Person("Bob",15), new Person("Bob",15));
//
//        String s1=RemoveDuplicates.of(data1).toString();
//        String s2=RemoveDuplicates.of(data2).toString();
//        String s3=RemoveDuplicates.of(data3).toString();
//        String s4=RemoveDuplicates.of(data4).toString();
//
//        assert s1.equals("[Person[name=Bob, age=15], Person[name=Bob, age=20]]"):"assert1 "+s1;
//        assert s2.equals("[Person[name=Bob, age=15]]"):"assert2 "+s2;
//        assert s3.equals("[Person[name=Bob, age=15], Person[name=bob, age=20]]"):"assert3 "+s3;
//        assert s4.equals("[Person[name=Bob, age=15]]"):"assert4 "+s4;
//    }
//}