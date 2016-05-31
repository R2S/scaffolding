package biz.r2s.core.util;

import java.lang.reflect.Field;

public class ObjectUtil {
	public static void copyFieldByField(Object src, Object dest) {
	    copyFields(src, dest, src.getClass());
	}

	private static void copyFields(Object src, Object dest, Class<?> klass) {
	    Field[] fields = klass.getDeclaredFields();
	    for (Field f : fields) {
	        f.setAccessible(true);
	        copyFieldValue(src, dest, f);
	    }

	    klass = klass.getSuperclass();
	    if (klass != null) {
	        copyFields(src, dest, klass);
	    }
	}

	private static void copyFieldValue(Object src, Object dest, Field f) {
	    try {
	        Object value = f.get(src);
	        f.set(dest, value);
	    } catch (ReflectiveOperationException e) {
	        throw new RuntimeException(e);
	    }
	}
}
