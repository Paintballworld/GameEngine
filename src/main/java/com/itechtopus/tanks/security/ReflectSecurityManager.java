package com.itechtopus.tanks.security;

/**
 * @author Yerlan Akzhanov
 * @link GitHub : github.com/Paintballworld
 * @link personal : www.itechtopus.com
 * Paintballworld@rambler.ru
 * date: 04.09.17
 * GameEngine
 */
public class ReflectSecurityManager extends SecurityManager {
    @Override
    public void checkPackageAccess(String pkg) {
        System.out.println("ReflectSecurityManager pkg = " + pkg);
        super.checkPackageAccess(pkg);
    }

    @Override
    public void checkCreateClassLoader() {
//        ignore
//        super.checkCreateClassLoader();
    }

    @Override
    public void checkRead(String file) {
//        super.checkRead(file);
    }
}
