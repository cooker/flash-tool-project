package org.grant.utils.constants;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * 7/3/2022 3:31 下午
 * 描述：
 *
 * @author grant
 */
public interface EnumCompare<T1, T2> extends Predicate<T1> {

    T1 getKey();
    T2 getCode();

    default String keyStr() {
        return Objects.toString(getKey());
    }

    default T1 key() {
        return getKey();
    }

    @Override
    default boolean test(T1 key) {
        return Objects.equals(key, getKey());
    }
}
