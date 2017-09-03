package com.itechtopus.tanks.uploader.uri_jar_uploader;

import com.itechtopus.tanks.interfaces.Tanker;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Yerlan Akzhanov
 * @link GitHub : github.com/Paintballworld
 * @link personal : www.itechtopus.com
 * Paintballworld@rambler.ru
 * date: 03.09.17
 * GameEngine
 */
public class Test1 {

    private static String jarFileUri = "file:///home/me/IdeaProjects/Tankist/target/Tankist-1.0-SNAPSHOT.jar";
    private static String jarClassFullName = "SuperTanker";

    public static void main(String[] args) throws Exception {


        // Getting the jar URL which contains target class
        URL[] classLoaderUrls = new URL[]{new URL(jarFileUri)};

        // Create a new URLClassLoader
        URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

        // Load the target class
        Class<?> beanClass = urlClassLoader.loadClass(jarClassFullName);

        Tanker tanker = (Tanker) beanClass.newInstance();

        tanker.run();

        /*// Create a new instance from the loaded class
        Constructor<?> constructor = beanClass.getConstructor();
        Object beanObj = constructor.newInstance();
        // Getting a method from the loaded class and invoke it
        Method method = beanClass.getMethod("sayHello");
        method.invoke(beanObj);*/


    }

}
