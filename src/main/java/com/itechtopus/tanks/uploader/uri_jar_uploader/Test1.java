package com.itechtopus.tanks.uploader.uri_jar_uploader;

import com.itechtopus.tanks.interfaces.Tanker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    Thread toTerminate = new Thread(tanker);

    toTerminate.start();

    String line;

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    while (toTerminate.getState() != Thread.State.TERMINATED) {
      line = reader.readLine();
      System.out.println("-->" + line);
      if (line.equalsIgnoreCase("stop")) {
        System.out.println("Attempt to use stop");
        toTerminate.stop();
      }
      if (line.equalsIgnoreCase("interrupt")) {
        System.out.println("Attempt to interrupt");
        toTerminate.interrupt();
      }
      if (line.equalsIgnoreCase("destroy")) {
        System.out.println("Attempt to use destroy");
        toTerminate.destroy();
      }
      System.out.println(toTerminate.getState());
    }



        /*// Create a new instance from the loaded class
        Constructor<?> constructor = beanClass.getConstructor();
        Object beanObj = constructor.newInstance();
        // Getting a method from the loaded class and invoke it
        Method method = beanClass.getMethod("sayHello");
        method.invoke(beanObj);*/


  }

}
