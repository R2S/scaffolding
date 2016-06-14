package biz.r2s.core.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {
    private ClassUtils() {
    }

    private static void checkDirectory(File directory, String pckgname, List<Class<?>> classes, String extension) throws ClassNotFoundException {
        File tmpDirectory;

        if (directory.exists() && directory.isDirectory()) {
            final String[] files = directory.list();

            for (final String file : files) {
                if (file.endsWith(extension)) {
                    try {
                        classes.add(Class.forName(pckgname + '.' + file.split("\\.")[0]));
                    } catch (final NoClassDefFoundError e) {
                        e.fillInStackTrace();
                    }
                } else {
                    tmpDirectory = new File(directory, file);
                    if (tmpDirectory.isDirectory()) {
                        checkDirectory(tmpDirectory, pckgname + "." + file, classes, extension);
                    }
                }
            }
        }
    }

    private static void checkJarFile(JarURLConnection connection, String pckgname, List<Class<?>> classes, String extension) throws ClassNotFoundException, IOException {
        final JarFile jarFile = connection.getJarFile();
        final Enumeration<JarEntry> entries = jarFile.entries();
        String name;

        for (JarEntry jarEntry = null; entries.hasMoreElements() && ((jarEntry = entries.nextElement()) != null);) {
            name = jarEntry.getName();

            if (name.contains(extension)) {
                name = name.substring(0, name.length() - 6).replace('/', '.');

                if (name.contains(pckgname)) {
                    classes.add(Class.forName(name));
                }
            }
        }
    }
    public static List<Class<?>> getClassesForPackage(String pckgname) throws ClassNotFoundException {
    	return getClassesForPackage(pckgname, ".class");
    }
    public static List<Class<?>> getClassesForPackage(String pckgname, String extension) throws ClassNotFoundException {
        final List<Class<?>> classes = new ArrayList<Class<?>>();

        try {
            final ClassLoader cld = Thread.currentThread().getContextClassLoader();

            if (cld == null) {
                lancaExcecaoPersonalizada("Can't get class loader.", new ClassNotFoundException("Can't get class loader."));
            }

            final Enumeration<URL> resources = cld.getResources(pckgname.replace('.', '/'));
            URLConnection connection;

            for (URL url = null; resources.hasMoreElements() && ((url = resources.nextElement()) != null);) {
                connection = url.openConnection();

                if (connection instanceof JarURLConnection) {
                    checkJarFile((JarURLConnection) connection, pckgname, classes, extension);
                } else {
                    checkDirectory(new File(URLDecoder.decode(url.getPath(), "UTF-8")), pckgname, classes, extension);
                }
            }
        } catch (final NullPointerException ex) {
            lancaExcecaoPersonalizada(pckgname + " does not appear to be a valid package (Null pointer exception)", ex);
        } catch (final UnsupportedEncodingException ex) {
            lancaExcecaoPersonalizada(pckgname + " does not appear to be a valid package (Unsupported encoding)", ex);
        } catch (final IOException ioex) {
            lancaExcecaoPersonalizada("IOException was thrown when trying to get all resources for " + pckgname, ioex);
        }

        return classes;
    }

    private static void lancaExcecaoPersonalizada(String mensagem, final Exception ex) throws ClassNotFoundException {
        throw new ClassNotFoundException(mensagem, ex);
    }

    public static Method buscarMetodoPorAnnotation(Class anootationClass, Class classe){
        Method metodoRetorno = null;
        Method[] metodos = classe.getMethods();

        for(Method metodo:metodos){
            if(metodo.getAnnotation(anootationClass) != null){
                metodoRetorno = metodo;
            }
        }
        return metodoRetorno;
    }

    public static boolean ehSubClasse(Class classe, Class interfacee){
        return !ehInterface(classe)&&!ehClasseAbstrata(classe)&&interfacee.isAssignableFrom(classe);
    }

    public static boolean ehInterface(Class classe){
        return classe.isInterface();
    }

    public static boolean ehClasseAbstrata(Class classe){
    	return Modifier.isAbstract( classe.getModifiers() );
    }
}
