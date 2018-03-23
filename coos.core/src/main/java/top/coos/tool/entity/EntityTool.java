package top.coos.tool.entity;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import top.coos.annotation.Column;
import top.coos.annotation.PrimaryKey;
import top.coos.annotation.Table;
import top.coos.exception.CoreException;
import top.coos.tool.clazz.ClassTool;
import top.coos.tool.field.FieldTool;
import top.coos.tool.string.StringHelper;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EntityTool {

	public static String getTableName(Class<?> entityClass) throws CoreException {

		if (!entityClass.isAnnotationPresent(Table.class)) {
			throw new CoreException("not find " + Table.class + " in " + entityClass + "");
		}
		Table table = entityClass.getAnnotation(Table.class);
		return table.name();
	}

	public static String getPrimaryKey(Class<?> entityClass) throws CoreException {

		List<Field> fields = FieldTool.getClassFields(entityClass);
		for (Field f : fields) {
			if (f.isAnnotationPresent(Column.class) && f.isAnnotationPresent(PrimaryKey.class)) {
				return ((Column) f.getAnnotation(Column.class)).name();
			}
		}
		return null;
	}

	public static List<Field> getClassField(Class className) throws CoreException {

		List<Field> allfields = FieldTool.getClassFields(className);

		return allfields;
	}

	private static Object formatListValue(Object value) {

		if (value != null) {
			if (value.getClass().isArray()) {
				if (value instanceof byte[] || value instanceof Byte[]) {
					byte[] list = (byte[]) value;
					return list;
				} else if (value instanceof char[] || value instanceof Character[]) {

					char[] list = (char[]) value;
					return list;
				} else if (value instanceof String[]) {

					String[] list = (String[]) value;
					return list;
				} else if (value instanceof long[] || value instanceof Long[]) {

					long[] list = (long[]) value;
					return list;
				} else if (value instanceof short[] || value instanceof Short[]) {

					short[] list = (short[]) value;
					return list;
				} else if (value instanceof double[] || value instanceof Double[]) {

					double[] list = (double[]) value;
					return list;
				} else if (value instanceof boolean[] || value instanceof Boolean[]) {

					boolean[] list = (boolean[]) value;
					return list;
				} else if (value instanceof float[] || value instanceof Float[]) {

					float[] list = (float[]) value;
					return list;
				} else if (value instanceof int[] || value instanceof Integer[]) {

					int[] list = (int[]) value;
					return list;
				} else {
				}
			}
		}
		return null;
	}

	private static Object getForMapValue(Object value, boolean forColumn) throws Exception {

		if (value != null) {
			if (ClassTool.isPrimitive(value)) {
				return value;
			} else if (value.getClass().isArray()) {
				Object v = formatListValue(value);
				if (v != null) {
					return v;
				} else {
					Object[] list = (Object[]) value;
					Object[] datas = new Object[list.length];
					for (Object one : list) {
						Object data = getForMapValue(one, forColumn);
						datas[datas.length] = (data);
					}
					return datas;
				}

			} else if (value instanceof List) {
				List<?> list = (List<?>) value;
				List<Object> datas = new ArrayList<Object>();
				for (Object one : list) {
					Object data = getForMapValue(one, forColumn);
					datas.add(data);
				}
				return datas;
			} else if (value instanceof Map) {
				return value;
			}
			return getData(value, forColumn);
		}
		return value;
	}

	public static List<Map<String, Object>> getDatas(List<?> entitys, boolean forColumn) throws CoreException {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (entitys != null) {
			for (Object entity : entitys) {
				list.add(getData(entity, forColumn));
			}
		}
		return list;
	}

	public static Map<String, Object> getData(Object entity, boolean forColumn) throws CoreException {

		if (entity == null) {
			return null;
		}
		try {
			if (entity instanceof Map) {
				return (Map) entity;
			}
			if (entity.getClass().isEnum()) {
				return null;
			}
			List<Field> fields = getClassField(entity.getClass());
			Map<String, Object> data = new HashMap<String, Object>();
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.isEnumConstant()) {
					continue;
				}
				String name = field.getName();
				if (forColumn) {
					if (!field.isAnnotationPresent(Column.class)) {
						continue;
					}
					name = ((Column) field.getAnnotation(Column.class)).name();
				}
				Object value = field.get(entity);

				Object v = getForMapValue(value, forColumn);
				data.put(name, v);
			}
			return data;
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}

	public static <T> List<T> getEntitys(Class className, List<Map<String, Object>> datas, boolean forColumn)
			throws CoreException {

		List<T> objects = new ArrayList<T>();
		if (datas == null) {
			return null;
		}
		for (Map<String, Object> data : datas) {
			objects.add((T) getEntity(className, data, forColumn));
		}
		return objects;
	}

	private static Object getForEntityValue(Class<?> fieldType, Object value, Type fieldGenericType, boolean forColumn)
			throws Exception {

		if (value != null) {
			if (ClassTool.isPrimitive(fieldType)) {
				if (fieldType.equals(String.class)) {
					return "" + value;
				}
				if (fieldType.equals(Integer.class)) {
					if (StringHelper.isEmpty("" + value)) {
						value = "0";
					}
					return Integer.valueOf("" + value);
				}
				if (fieldType.equals(Long.class)) {
					return Long.valueOf("" + value);
				}
				if (fieldType.equals(Double.class)) {
					return Double.valueOf("" + value);
				}
				return value;
			} else if (fieldType.isArray()) {
				Class<?> componentType = fieldType.getComponentType();
				Object[] datas;
				int length = 0;
				if (value instanceof JSONArray) {
					JSONArray array = JSONArray.fromObject(value);
					length = array.size();
				} else {
					Object v = formatListValue(value);
					if (v != null) {
						return v;
					}
					Object[] list = (Object[]) value;
					length = list.length;
				}
				datas = ClassTool.newArray(componentType, length);
				if (value instanceof JSONArray) {
					JSONArray array = JSONArray.fromObject(value);
					for (int i = 0; i < length; i++) {
						Object one = array.get(i);
						Object data = getForEntityValue(componentType, one, null, forColumn);
						datas[i] = (data);
					}
				} else {
					Object[] list = (Object[]) value;
					for (int i = 0; i < list.length; i++) {
						Object one = list[i];
						Object data = getForEntityValue(componentType, one, null, forColumn);
						datas[i] = (data);
					}
				}
				return datas;

			} else if (fieldType.equals(List.class)) {
				List datas = new ArrayList();
				if (fieldGenericType != null && fieldGenericType instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) fieldGenericType;
					if (pt.getActualTypeArguments().length > 0) {
						Type[] types = pt.getActualTypeArguments();
						Type type = types[0];
						Class<?> clazz = null;
						if (type instanceof ParameterizedType) {
							ParameterizedType parameterizedType = (ParameterizedType) type;
							type = parameterizedType.getRawType();
						}
						if (type instanceof Class) {
							clazz = (Class) type;
						} else {
							try {

								clazz = (Class) type;
							} catch (ClassCastException e) {
								System.out.println("error type " + type + ":" + e.getMessage());
								throw new CoreException(e);
							}
						}

						if (value instanceof JSONArray) {
							JSONArray array = JSONArray.fromObject(value);
							for (Object one : array) {
								Object data = getForEntityValue(clazz, one, null, forColumn);
								datas.add(data);
							}
						} else {
							List<?> list = (List<?>) value;
							for (Object one : list) {
								Object data = getForEntityValue(clazz, one, null, forColumn);
								datas.add(data);
							}
						}
					}
				}

				return datas;
			} else if (fieldType.equals(Map.class)) {
				return getEntity(fieldType, getData(value, forColumn), forColumn);
			} else {
				if (fieldType.getEnumConstants() != null) {
					return null;
				} else {

					Object data = getEntity(fieldType, getData(value, forColumn), forColumn);
					return data;
				}
			}
		}
		return value;
	}

	public static void fullEntity(Object object, Map<String, ?> data, boolean forColumn) throws CoreException {

		try {
			List<Field> fields = getClassField(object.getClass());

			if (object != null && object.getClass().isEnum()) {
				return;
			}
			for (Field field : fields) {
				field.setAccessible(true);
				if (java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
					continue;
				}
				if (field.isEnumConstant()) {
					continue;
				}
				String name = field.getName();
				if (forColumn) {
					if (!field.isAnnotationPresent(Column.class)) {
						continue;
					}
					name = ((Column) field.getAnnotation(Column.class)).name();
				}
				Object value = null;
				for (String keyName : data.keySet()) {

					if (keyName.equalsIgnoreCase(name)) {
						value = data.get(keyName);
						break;
					}
				}
				if (value != null) {
					if (value instanceof JSONObject) {
						JSONObject jsonObject = (JSONObject) (value);
						if (jsonObject.isNullObject()) {
							value = null;
						}
					}
					if (value instanceof JSONNull) {
						value = null;
					}
				}
				if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
					if (value == null || ("" + value).equals("0") || ("" + value).equals("false")) {
						field.set(object, false);
					} else {
						field.set(object, true);
					}
				} else {
					if (value != null) {
						Object fieldValue = getForEntityValue(field.getType(), value, field.getGenericType(), forColumn);
						if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
							field.set(object, Long.valueOf("" + fieldValue));
						} else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
							field.set(object, Integer.valueOf("" + fieldValue));
						} else if (field.getType().equals(Character.class) || field.getType().equals(char.class)) {
							field.set(object, String.valueOf("" + fieldValue).toCharArray()[0]);
						} else if (field.getType().equals(Byte.class) || field.getType().equals(byte.class)) {
							field.set(object, Byte.valueOf("" + fieldValue));
						} else if (field.getType().equals(Byte[].class) || field.getType().equals(byte[].class)) {
							if (fieldValue instanceof Object[] && ((Object[]) fieldValue).length == 0) {
								field.set(object, new byte[0]);
							} else if (((byte[]) fieldValue).length == 0) {
								field.set(object, new byte[0]);
							} else {
								field.set(object, (byte[]) fieldValue);
							}
						} else if (field.getType().equals(char[].class) || field.getType().equals(Character[].class)) {
							if (fieldValue instanceof Object[] && ((Object[]) fieldValue).length == 0) {
								field.set(object, new char[0]);
							} else if (((char[]) fieldValue).length == 0) {
								field.set(object, new char[0]);
							} else {
								field.set(object, (char[]) fieldValue);
							}
						} else if (field.getType().equals(int[].class) || field.getType().equals(Integer[].class)) {
							if (fieldValue instanceof Object[] && ((Object[]) fieldValue).length == 0) {
								field.set(object, new int[0]);
							} else if (((int[]) fieldValue).length == 0) {
								field.set(object, new int[0]);
							} else {
								field.set(object, (int[]) fieldValue);
							}
						} else if (field.getType().equals(short[].class) || field.getType().equals(Short[].class)) {
							if (fieldValue instanceof Object[] && ((Object[]) fieldValue).length == 0) {
								field.set(object, new short[0]);
							} else if (((short[]) fieldValue).length == 0) {
								field.set(object, new short[0]);
							} else {
								field.set(object, (short[]) fieldValue);
							}
						} else if (field.getType().equals(long[].class) || field.getType().equals(Long[].class)) {
							if (fieldValue instanceof Object[] && ((Object[]) fieldValue).length == 0) {
								field.set(object, new long[0]);
							} else if (((long[]) fieldValue).length == 0) {
								field.set(object, new long[0]);
							} else {
								field.set(object, (long[]) fieldValue);
							}
						} else if (field.getType().equals(float[].class) || field.getType().equals(Float[].class)) {
							if (fieldValue instanceof Object[] && ((Object[]) fieldValue).length == 0) {
								field.set(object, new float[0]);
							} else if (((float[]) fieldValue).length == 0) {
								field.set(object, new float[0]);
							} else {
								field.set(object, (float[]) fieldValue);
							}
						} else if (field.getType().equals(double[].class) || field.getType().equals(Double[].class)) {
							if (fieldValue instanceof Object[] && ((Object[]) fieldValue).length == 0) {
								field.set(object, new double[0]);
							} else if (((double[]) fieldValue).length == 0) {
								field.set(object, new double[0]);
							} else {
								field.set(object, (double[]) fieldValue);
							}
						} else if (field.getType().equals(boolean[].class) || field.getType().equals(Boolean[].class)) {
							if (fieldValue instanceof Object[] && ((Object[]) fieldValue).length == 0) {
								field.set(object, new boolean[0]);
							} else if (((boolean[]) fieldValue).length == 0) {
								field.set(object, new boolean[0]);
							} else {
								field.set(object, (boolean[]) fieldValue);
							}
						} else {
							field.set(object, fieldValue);
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CoreException(e);
		}
	}

	public static <T> T getEntity(Class className, Map<String, ?> data, boolean forColumn) throws CoreException {

		try {
			Object object = null;
			if (className.equals(Map.class)) {
				object = new HashMap();
			} else {
				object = className.newInstance();

			}

			if (data == null) {
				return null;
			}
			fullEntity(object, data, forColumn);

			return (T) object;
		} catch (CoreException e) {
			throw e;
		} catch (Exception e) {
			throw new CoreException(e);
		}
	}
}
