package biz.r2s.core.util;

import java.lang.reflect.Field;

public class ObjectUtil {
	public static void copyFieldByField(Object src, Object dest) {
		copyFields(src, dest, src.getClass());
	}

	public static void copyFields(Object src, Object dest, Class<?> klass) {
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

	public static void copyFieldValue(Object src, Object dest, Field f) {
		try {
			Object value = f.get(src);
			f.set(dest, value);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getValue(String campo, Object object) {
		Object value = null;
		try {
			Field field = getField(campo, object.getClass());
			if (field != null) {
				field.setAccessible(true);
				value = field.get(object);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static Field getField(String campo, Class clazz) {
		Field fieldReturn = null;
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getName().equals(campo)) {
				fieldReturn = field;
			}
		}
		return fieldReturn;
	}
}
