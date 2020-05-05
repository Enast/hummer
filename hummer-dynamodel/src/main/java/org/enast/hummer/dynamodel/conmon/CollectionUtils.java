package org.enast.hummer.dynamodel.conmon;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public final class CollectionUtils {

	private CollectionUtils() {
	}

	/**
	 * Null-safe check if the specified collection is empty.
	 * <p>
	 * Null returns true.
	 *
	 * @param coll the collection to check, may be null
	 * @return true if empty or null
	 * @since Commons Collections 3.2
	 */
	public static boolean isEmpty(Collection coll) {
		return (coll == null || coll.isEmpty());
	}

	public static boolean isNotEmpty(Collection coll) {
		return !isEmpty(coll);
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * 判断一个对象是否在一个对象数组中
	 *
	 * @param target     待判断对象
	 * @param collection 备选对象数组
	 * @return true-待判断对象在备选对象数组中存在；false-不存在
	 * @author zhiqianye
	 * @date 2017年6月2日上午11:19:32
	 * @since 1.0.0
	 */
	public static <T> boolean in(T target, T... collection) {
		if (target == null || collection == null || collection.length < 1)
			return false;
		for (T one : collection) {
			if (target == one || Objects.equals(target, one))
				return true;
		}
		return false;
	}

	public static boolean containsNull(Object... os) {
		if (os == null) {
			throw new IllegalArgumentException("Target collection can't be null");
		}
		for (Object o : os) {
			if (o == null)
				return true;
		}
		return false;
	}

	public static boolean containsBlank(String... strings) {
		if (strings == null) {
			throw new IllegalArgumentException("Target collection can't be null");
		}
		for (String s : strings) {
			if (StringUtils.isBlank(s))
				return true;
		}
		return false;
	}

    public static boolean isEmpty(String[] arr) {
        return arr == null || arr.length == 0 || Stream.of(arr).filter(a -> a != null && a.length() > 0).count() == 0;
    }

}
