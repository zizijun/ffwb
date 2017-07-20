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
    static String[] frontEnd={"HTML","CSS","javascript","jQuery","浏览器兼容性","响应式布局","web安全",
    "性能","模块定义","插件","nodejs","http标准","ECMAScript","设计模式","OO"};//还有其他待补充

    static String[] htmlAbout={"html","header","aside","nav","section","article","footer","doctype","head","title","base","link","meta",
    "style","body","h1","h2","h3","h4","h5","h6","hggroup","address","<p>","<hr>","<br>",
    "<pre>","<ol>","<ul>","<li>","<dl>","<dt>","<dd>","figure","figcaption","<div>","<a>",
    "small","strong","var","img","iframe","embed","param","video","audio","source","cavas",
    "area","table","caption","colgroup","tbody","thread","tfoot","<tr>","<td>","<th>","form","label",
    "button","select","datalist","optgroup","option","textarea","keygen","onblur","hidden","onabort",
    "oncanplay","onclick","onfocus","onerror","onmouse"};
    static String[] cssAbout={"css","style","div","text","align","size","font","color","center","margin","background",
    "repeat","position","attachment","decoration","transform","active","hover","visited","link","边距","边框",
    "padding","border","height","width","outline","overflow","float","first-child","lang","伪类","伪元素","selector",
            "pseudo-element","选择器","样式","边框","浮动"};
    static String[] jsAbout={"javascript","js","jquery","ajax","var","程序","script","onmouse","websocket","flash"};
    static String[] jquery={"jquery"};
    static String[] browserCompatibility={"兼容性","hack","火狐","firefox","opera","safari","chrome","ie5","ie6","ie7","ie8","ie9"};
    static String[] responsiveLayout={"响应式","弹性","手机"};
    static String[] security={"安全","xss","攻击","防御","防范","csrf","验证","加密","公钥","私钥","劫持","注入","钓鱼"};
    static String[] performance={"性能","缓存"};//属性能够！
    static String[] module={"模块","amd","cmd","commonjs","requirejs","seajs",""};
    static String[] plugIn={"插件","grunt","qunit","gulp","proui"};
    static String[] nodejs={"node"};
    static String[] httpAbout={"http"};
    static String[] ECMAScript={"ecmascript"};
    static String[] designPattern={"设计模式","模式","backbone","单例模式","单体模式","工厂模式","同步模式","异步模式","异步","同步",
    "设计原则","发布","订阅","策略","中介者"};
    static String[] feOO={"oo","面向对象","封装","继承","多态",""};

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
        //web前端相关的判断
        for(String s:htmlAbout){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("HTML");
                break;
            }
        }
        for(String s:cssAbout){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("CSS");
                break;
            }
        }
        for(String s:jsAbout){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("javascript");
                break;
            }
        }
        for(String s:jquery){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("jQuery");
                break;
            }
        }
        for(String s:browserCompatibility){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("浏览器兼容性");
                break;
            }
        }
        for(String s:responsiveLayout){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("响应式布局");
                break;
            }
        }
        for(String s:security){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("web安全");
                break;
            }
        }
        for(String s:performance){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("性能");
                break;
            }
        }
        for(String s:module){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("模块定义");
                break;
            }
        }
        for(String s:plugIn){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("插件");
                break;
            }
        }
        for(String s:nodejs){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("nodejs");
                break;
            }
        }
        for(String s:httpAbout){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("http标准");
                break;
            }
        }
        for(String s:ECMAScript){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("ECMAScript");
                break;
            }
        }
        for(String s:designPattern){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("设计模式");
                break;
            }
        }
        for(String s:feOO){
            if(str.toLowerCase().indexOf(s)>0) {
                res.add("OO");
                break;
            }
        }
        return res;
    }

}

