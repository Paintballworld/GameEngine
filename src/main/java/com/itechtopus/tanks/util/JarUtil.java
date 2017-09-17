package com.itechtopus.tanks.util;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Yerlan Akzhanov
 * @link GitHub : github.com/Paintballworld
 * @link personal : www.itechtopus.com
 * Paintballworld@rambler.ru
 * date: 03.09.17
 * GameEngine
 */
public class JarUtil {

  public static <T> T loadClassImplementing(Class<T> interFaceClass, String jarFileLocation, String classFullName) {
    try {
      Class<?> beanClass = getClass(jarFileLocation, classFullName);
      if (!Arrays.asList(beanClass.getInterfaces()).contains(interFaceClass))
        throw new JarInstantiationException("Class <" +
          classFullName + "> doesn't implement " + interFaceClass + " interface");

      // because in previous line I made checking if one of the loaded class' interfaces matches given one
      // so we could instantiate a class by it
      //noinspection unchecked
      return (T) beanClass.newInstance();
    } catch (MalformedURLException e) {
      e.printStackTrace();
      throw new JarInstantiationException("Invalid URL <" + jarFileLocation + "> :" + e.getMessage());
    } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
      e.printStackTrace();
      throw new JarInstantiationException("Error has been occurred during getting instance of a class from jar:" + e.getMessage());
    }
  }

  private static Class<?> getClass(String jarFileLocation, String classFullName) throws MalformedURLException, ClassNotFoundException {
    // Getting the jar URL which contains target class
    URL[] classLoaderUrls = new URL[]{new URL(jarFileLocation)};

    // Create a new URLClassLoader
    URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

    // Load the target class
    return urlClassLoader.loadClass(classFullName);
  }

  public static <T> List<String> getClassesImplementing(Class<T> interfaceClass, String jarFileLocation) {
    List<String> result = new ArrayList<>();
    try {
      for (String className : getClassNames(jarFileLocation)) {
        Class<?> aClass = getClass(jarFileLocation, className);
        if (Arrays.asList(aClass.getInterfaces()).contains(interfaceClass))
          result.add(className);
      }
    } catch (MalformedURLException | ClassNotFoundException e) {
      e.printStackTrace();
      throw new JarInstantiationException("Error has been occurred during getting a class implementing <"
        + interfaceClass + "> :" + e.getMessage());
    }
    return result;
  }

  public static List<String> getClassNames(String jarFileLocation) {
    List<String> classNames = new ArrayList<>();
    try (
      ZipInputStream zip = new ZipInputStream(new FileInputStream(jarFileLocation.replaceFirst("file://", "")))
    ) {
      for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
        if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
          // This ZipEntry represents a class. Now, what class does it represent?
          String className = entry.getName().replace('/', '.'); // including ".class"
          classNames.add(className.substring(0, className.length() - ".class".length()));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new JarInstantiationException("Error has been occurred during extracting jar file at <" +
        jarFileLocation + "> :" + e.getMessage());
    }
    return classNames;
  }

  public static String getSimpleName(String fullName) {
    if (!fullName.contains(".")) return fullName;
    if (fullName.lastIndexOf('.') == fullName.length() - 1)
      throw new JarInstantiationException("Class name has no name after last '.' symbol");
    return fullName.substring(fullName.lastIndexOf('.') + 1);
  }

  public static List<String> getImports(Class<?> aClass) throws FileNotFoundException {
    JavaDocBuilder builder = new JavaDocBuilder();
    builder.addSource(new InputStreamReader(aClass.getClassLoader().getResourceAsStream(aClass.getName())));
    JavaSource src = builder.getSources()[0];
    return Arrays.asList(src.getImports());
  }

}
