package com.ffwb.utils;

import java.util.*;

/**
 * Created by lenovo on 2017/7/18.
 */
public class TagBuilt {
    //java相关
    static String[] javaTags={"JVM相关","java的运行","数据类型","对象和实例","访问控制","流程控制","OO","static静态","基础知识点",
        "集合框架","异常框架","IO","线程相关","网络","时间日期处理","XML解析/JSON解析","Maven的使用",
        "泛型","关键字","JNI","开源框架","J2EE"};

    static String[] JVM={"内存","jvm","gc","垃圾","回收","收集","finalize","调优","thread dump","class loader","类加载器","执行过程"};
    static String[] runtime={"运行","编译","class文件","命令行","classpath","类路径"};
    static String[] dataType={"基本类型","数据类型","对象类型","装箱","拆箱","integer","hashcode","equals","string类"};
    static String[] object={"对象","实例","instance","构造","创建"};
    static String[] accessControl={"public","private","defalut","protected","修饰"};
    static String[] flowControl={"流程","if","switch","loop","for","while"};
    static String[] OO={"面向对象","oo","封装","继承","多态","重载","接口回调","抽象","抽象类","接口","覆盖"};
    static String[] staticAbout={"static","静态","静态属性","静态方法","静态类","静态代码块"};
    static String[] basics={"final","finally"};//有待完整
    static String[] collections={"集合","collection","map","list","set","collections"};
    static String[] exceptions={"异常","throwable","exception","runtimeexception","error"};
    static String[] IO={"读写","input","output","reader","writer","inputstream","outputstream",
            "inputstreamreader","bufferedreader","outputstreamreader","bufferedwriter","printwriter","file","流"};//IO exception collection
    static String[] threadAbout={"线程","并发","callable","runnable","synchronized","reentrantlock","future",
            "异步","concurrent","lock","锁"};
    static String[] network={"网络","network","tcp","udp"};
    static String[] timeAbout={"时间","日期","simpledateformat","dateformat","calendar"};//update会匹配上吗？？会的
    static String[] parseAbout={"json","xml","dom","sax","jackson","fastjson","gson","解析"};
    static String[] mavenAbout={"maven","pom.xml"};
    static String[] genericity={"泛型","object","<t>"};
    static String[] keyword={"super","this"};
    static String[] JNI={"jni"};
    static String[] openSourceFramework={"spring","swing","struts","hibernate","mvc","jquery","orm","mybatis"};
    static String[] J2EE={"j2ee","servlet","jsp","web","ejb","jdbc","jndi","rmi","request","response","remotemethodinvocation","webservice","jms","jta","jts","mail","javamail"};

    //web前端相关font-end
    static String[] frontEnd={"HTML","CSS","javascript","jQuery","浏览器兼容性","相应式布局","web安全",
    "性能优化","模块定义","插件","node","http标准","ECMAScript","设计模式","OO"};//还有其他待补充

    static String[] htmlAbout={};
    static String[] cssAbout={};
    static String[] jsAbout={};
    static String[] jquery={"jquery",""};
    static String[] browserCompatibility={};
    static String[] relativeLayout={};
    static String[] security={};
    static String[] performance={};
    static String[] module={};
    static String[] plugIn={};
    static String[] nodejs={};
    static String[] httpAbout={};
    static String[] ECMAScript={};
    static String[] designPattern={};
    static String[] feOO={};

    //获取所有tag
    public static String[] getTagsByCategory(String category){
        if(category.equals("java"))
            return javaTags;
        else if(category.equals("front-end"))
            return frontEnd;
        else return null;
    }

    public static Set<String> isWhat(String str){

        Set<String> res=new HashSet<>();
        for(String s:JVM){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("JVM");
                break;
            }
        }
        for(String s:runtime){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("java的运行");
                break;
            }
        }
        for(String s:dataType){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("数据类型");
                break;
            }
        }
        for(String s:object){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("对象和实例");
                break;
            }
        }
        for(String s:accessControl){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("访问控制");
                break;
            }
        }
        for(String s:flowControl){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("流程控制");
                break;
            }
        }
        for(String s:OO){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("OO");
                break;
            }
        }
        for(String s:staticAbout){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("static静态");
                break;
            }
        }
        for(String s:basics){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("基础知识点");
                break;
            }
        }
        for(String s:collections){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("集合框架");
                break;
            }
        }
        for(String s:exceptions){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("异常框架");
                break;
            }
        }
        for(String s:IO){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("IO");
                break;
            }
        }
        for(String s:threadAbout){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("线程相关");
                break;
            }
        }
        for(String s:network){
            if(str.toLowerCase().indexOf(s)>0){
                res.add("网络");
                break;
            }
        }
        for(String s:timeAbout){//date update validate
            if(str.toLowerCase().indexOf(s)>0){
                res.add("时间日期处理");
                break;
            }
        }
        for(String s:parseAbout){
            if(str.toLowerCase().indexOf(s)>0){
                res.add("XML解析/JSON解析");
                break;
            }
        }
        for(String s:mavenAbout){
            if(str.toLowerCase().indexOf(s)>0){
                res.add("Maven的使用");
                break;
            }
        }
        for(String s:genericity){
            if(str.toLowerCase().indexOf(s)>0){
                res.add("泛型");
                break;
            }
        }
        for(String s:keyword){
            if(str.toLowerCase().indexOf(s)>0){
                res.add("关键字");
                break;
            }
        }
        for(String s:JNI){
            if(str.toLowerCase().indexOf(s)>0){
                res.add("JNI");
                break;
            }
        }
        for(String s:openSourceFramework){
            if(str.toLowerCase().indexOf(s)>0){
                res.add("开源框架");
                break;
            }
        }
        for(String s:J2EE){
            if(str.toLowerCase().indexOf(s)>0){
                res.add("J2EE");
                break;
            }
        }
        return res;
    }

}

