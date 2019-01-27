package com.winterchen.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swf on 2019/1/25.
 **/
public class Test {
    public static void main(String[] args) {
        List<Person> personList=getPersonList();
        //
        int count=0;
        float countScore=0.0f;
        Person maxPerson;
    /*    for (Person person:personList){
            //ID为奇数过滤掉然后处理,
            if(person.getId()%2==1 ){
                if(maxPerson==person){
                    maxPerson=person;
                }else if(maxPerson.getScore()<person.getScore()){
                    maxPerson=person;
                }
                //countScore+=person.getScore();
            }
        }*/
       // personList.stream().map(s->s.getId()%2==1 && Person::getScore)
    }
    public static List<Person> getPersonList(){
        List<Person> personList=new ArrayList<>();
        personList.add(new Person(1,"p1",99.0f));
        personList.add(new Person(2,"p2",75.3f));
        personList.add(new Person(3,"p3",65.0f));
        personList.add(new Person(4,"p4",85.0f));
        personList.add(new Person(5,"p5",82.0f));
        personList.add(new Person(5,"p6",88.0f));
        return personList;
    }
}
class Person{
    private Integer id;
    private String name;
    private Float score;


    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(Integer id, String name, Float score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
