package com.itechtopus.tanks;

import com.itechtopus.tanks.interfaces.Tanker;
import com.itechtopus.tanks.security.ReflectSecurityManager;
import com.itechtopus.tanks.util.JarUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main2 {

    private static String jarFileUri = "file:///home/me/IdeaProjects/Tankist/target/Tankist-1.0-SNAPSHOT.jar";

    private static String jarFileUri2 = "file:///home/me/IdeaProjects/BattleField/target/BattleFieldAPI-1.1-SNAPSHOT.jar";

    private static String jarClassFullName = "SuperTanker";

    public static void main(String[] args) {
        System.setSecurityManager(new ReflectSecurityManager());

        Tanker loadedTanker = JarUtil.loadClassImplementing(Tanker.class, jarFileUri, jarClassFullName);
        loadedTanker.getPlayerName();

        for (String s : JarUtil.getClassNames(jarFileUri2)) {
            System.out.println("Class:" + JarUtil.getSimpleName(s));
        }
        System.out.println(JarUtil.getSimpleName("org.tomcat.apache.CodeWars"));

        List<Runnable> allRunnable = new ArrayList<>();
        for (String className : JarUtil.getClassesImplementing(Runnable.class, jarFileUri)) {
            System.out.println("implements Runnable : " + className);
            allRunnable.add(JarUtil.loadClassImplementing(Runnable.class, jarFileUri, className));
        }

        for (Runnable runnable : allRunnable) {
            new Thread(runnable).start();
        }

        try {
            for (String s : JarUtil.getImports(allRunnable.get(0).getClass())) {
                System.out.println("::" + s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
