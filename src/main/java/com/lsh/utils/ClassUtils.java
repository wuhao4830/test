package com.lsh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by wuhao on 2018/6/4.
 */
public class ClassUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    public static List<Class> getAllClassByInterface(Class clazz) {
        List<Class> list = new ArrayList<Class>();
        // 判断是否是一个接口
        if (clazz.isInterface()) {
            try {
                List<Class> allClass = getAllClass(clazz.getPackage().getName());
                /**
                 * 循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
                 */
                for (Class allClas : allClass) {
                    /**
                     * 判断是不是同一个接口
                     */
                    // isAssignableFrom:判定此 Class 对象所表示的类或接口与指定的 Class
                    // 参数所表示的类或接口是否相同，或是否是其超类或超接口
                    if (clazz.isAssignableFrom(allClas)) {
                        if (!clazz.equals(allClas)) {
                            // 自身并不加进去
                            list.add(allClas);
                        }
                    }
                }
            } catch (Exception e) {
               logger.error(e.getMessage(),e);
            }
        } else {
            // 如果不是接口，则获取它的所有子类
            try {
                List<Class> allClass = getAllClass(clazz.getPackage().getName());
                /**
                 * 循环判断路径下的所有类是否继承了指定类 并且排除父类自己
                 */
                for (Class allClas : allClass) {
                    if (allClas.isAssignableFrom(clazz)) {
                        if (!clazz.equals(allClas)) {
                            // 自身并不加进去
                            list.add(allClas);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("出现异常");
            }
        }
        return list;
    }
    /**
     * 从一个指定路径下查找所有的类
     *
     */
    private static List<Class> getAllClass(String packagename) {
        List<Class> list = new ArrayList<Class>();
        // 返回对当前正在执行的线程对象的引用。
        // 返回该线程的上下文 ClassLoader。
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packagename.replace('.', '/');
        try {
            List<File> fileList = new ArrayList<File>();
            /**
             * 这里面的路径使用的是相对路径 如果大家在测试的时候获取不到，请理清目前工程所在的路径 使用相对路径更加稳定！
             * 另外，路径中切不可包含空格、特殊字符等！
             */
            // getResources:查找所有给定名称的资源
            // 获取jar包中的实现类:Enumeration<URL> enumeration =
            // classLoader.getResources(path);
            Enumeration<URL> enumeration = classLoader.getResources(path);
            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                // 获取此 URL 的文件名
                fileList.add(new File(url.getFile()));
            }
            for (File aFileList : fileList) {
                list.addAll(findClass(aFileList, packagename));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * 如果file是文件夹，则递归调用findClass方法，或者文件夹下的类 如果file本身是类文件，则加入list中进行保存，并返回
     *
     * @param file
     * @param packagename
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static List<Class> findClass(File file, String packagename) {
        List<Class> list = new ArrayList<Class>();
        if (!file.exists()) {
            return list;
        }
        // 返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
        File[] files = file.listFiles();
        if(files!=null && files.length!=0) {
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    // assert !file2.getName().contains(".");// 添加断言用于判断
                    if (!file2.getName().contains(".")) {
                        List<Class> arrayList = findClass(file2, packagename + "." + file2.getName());
                        list.addAll(arrayList);
                    }
                } else if (file2.getName().endsWith(".class")) {
                    try {
                        // 保存的类文件不需要后缀.class,带$符号的过滤掉
                        if(file2.getName().contains("$")){
                            continue;
                        }
                        list.add(Class.forName(packagename + '.' + file2.getName().substring(0, file2.getName().length() - 6)));
                    } catch (ClassNotFoundException e) {
                        logger.error(e.getMessage(),e);
                    }
                }
            }
        }
        return list;
    }

}
