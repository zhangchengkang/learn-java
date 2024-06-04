package com.ksf.reflect;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

/**
 * method.invoke实际上委派给MethodAccessor来处理。
 * MethodAccessor是一个接口，它有两个已有的具体实现：一个通过本地方法来实现反射调用，另一个则使用了委派模式。
 * 每个Method实例的第一次反射调用都会生成一个委派实现，它所委派的具体实现便是一个本地实现
 * 本地实现非常容易理解。当进入了Java虚拟机内部之后，我们便拥有了Method实例所指向方法的具体地址。这时候，反射调用无非就是将传入的参数准备好，然后调用进入目标方法。
 * <p>
 * 动态实现和本地实现相比，其运行效率要快上20倍 [2] 。这是因为动态实现无需经过Java到C++再到Java的切换，但由于生成字节码十分耗时，仅调用一次的话，反而是本地实现要快上3到4倍 [3]。
 * <p>
 * 考虑到许多反射调用仅会执行一次，Java虚拟机设置了一个阈值15（可以通过-Dsun.reflect.inflationThreshold=来调整）
 * 当某个反射调用的调用次数在15之下时，采用本地实现；
 * 当达到15时，便开始动态生成字节码，并将委派实现的委派对象切换至动态实现，这个过程我们称之为Inflation。
 * <p>
 * 反射耗时的原因
 * 1.Class.getMethod则会遍历该类的公有方法。如果没有匹配到，它还将遍历父类的公有方法
 * 2.getMethod会返回查找得到结果的一份拷贝,容易造成 GC,记得缓存Class.forName和Class.getMethod的结果,以减少不必要的堆空间消耗
 * 3.反射调用没有被内联,逃逸分析不再起效
 * 4.变长参数方法导致的Object数组
 * 5.基本类型的自动装箱、拆箱
 */
public class ImplTest {

    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }

    /**
     * 通过打印反射调用到目标方法时的栈轨迹
     * Method.invoke
     * ->DelegatingMethodAccessorImpl.invoke
     * ->NativeMethodAccessorImpl.invoke
     * ->target.invoke
     */
    public static void main1(String[] args) throws Exception {
        Class<?> klass = Class.forName("com.ksf.reflect.ImplTest");
        Method method = klass.getMethod("target", int.class);
        method.invoke(null, 0);
    }

    /**
     * 第 15 次的时候调用的类发生了变化
     * at sun.reflect.GeneratedMethodAccessor1.invoke(Unknown Source)
     *
     * @param args
     * @throws Exception
     */
    public static void main2(String[] args) throws Exception {
        long l = System.currentTimeMillis();
        Date date = new Date(l);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        System.out.println(simpleDateFormat.format(date));
    }

    public static void main(String[] args) {

        int a = 0;
        if (false){
            a = 1;
        }else if (false){
            a=2;
        }

        System.out.println(a);
    }
}
