package com.itechtopus.tanks;

import com.itechtopus.tanks.interfaces.Tanker;
import com.itechtopus.tanks.util.JarUtil;

import java.util.ArrayList;
import java.util.List;

public class Main2 {

    private static String jarFileUri = "file:///home/me/IdeaProjects/Tankist/target/Tankist-1.0-SNAPSHOT.jar";

    private static String jarFileUri2 = "file:///home/me/IdeaProjects/BattleField/target/BattleFieldAPI-1.1-SNAPSHOT.jar";

    private static String jarClassFullName = "SuperTanker";

    public static void main(String[] args) {
        Tanker loadedTanker = JarUtil.loadClassImplementing(Tanker.class, jarFileUri, jarClassFullName);
        loadedTanker.getPlayerName();

        for (String s : JarUtil.getClassNames(jarFileUri2)) {
            System.out.println("Class:" + JarUtil.getSimpleName(s));
        }
        System.out.println(JarUtil.getSimpleName("org.tomcat.apache.CodeWars"));

        List<Runnable> allRunnables = new ArrayList<>();
        for (String className : JarUtil.getClassesImplementing(Runnable.class, jarFileUri)) {
            System.out.println("implements Runnable : " + className);
            allRunnables.add(JarUtil.loadClassImplementing(Runnable.class, jarFileUri, className));
        }

        for (Runnable runnable : allRunnables) {
            new Thread(runnable).start();
        }

    }
}
