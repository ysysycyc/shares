package com.example.shares.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lijiawei
 */
public class DataConvertUtil {

    public static String convertString(Object o) {
        if (o == null) {
            return null;
        }
        return String.valueOf(o);
    }

    /**
     *
     * @param o convert value
     * @param i default value
     *
     * @return
     */
    public static Integer convertIntegerWithDefault(Object o, Integer i) {
        if (o == null) {
            return i;
        }
        String s = o.toString();
        if (StringUtils.isNumeric(s)) {
            return Integer.valueOf(s);
        }
        return i;
    }

    public static Integer convertInteger(Object o) {
        return convertIntegerWithDefault(o, null);
    }

    public static Long convertLong(Object o) {
        if (o == null) {
            return null;
        }
        String s = o.toString();
        if (StringUtils.isNumeric(s)) {
            return Long.valueOf(s);
        }
        return null;
    }

    public static Boolean convertBoolean(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Boolean) {
            return (boolean) o;
        }
        String s = o.toString();
        return Lists.newArrayList("true", "1", "yes").contains(s);
    }

    public static BigDecimal convertBigDecimal(Object o) {
        if (o == null) {
            return null;
        }
        return new BigDecimal(o.toString());
    }

    public static List<String> convertStringList(Object o) {
        if (o == null) {
            return Lists.newArrayList();
        }
        if (o instanceof Collection) {
            Collection<?> list = (Collection<?>) o;
            return list.stream().map(DataConvertUtil::convertString).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public static List<String> splitStringList(String s) {
        return splitStringList(s, ",");
    }

    public static <T> List<T> convertTList(Object o) {
        if (o == null) {
            return Lists.newArrayList();
        }
        if (o instanceof Collection) {
            Collection<?> list = (Collection<?>) o;
            return list.stream().filter(Objects::nonNull).map(l -> (T) l).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public static List<String> splitStringList(String s, String regex) {
        if (StringUtils.isBlank(s)) {
            return Lists.newArrayList();
        }
        String[] split = s.split(regex);
        if (split.length > 0) {
            return Arrays.stream(split).map(DataConvertUtil::convertString).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public static List<Integer> convertIntegerList(Object o) {
        if (o == null) {
            return Lists.newArrayList();
        }
        if (o instanceof Collection) {
            Collection<?> list = (Collection<?>) o;
            return list.stream().map(DataConvertUtil::convertInteger).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public static List<Long> convertLongList(Object o) {
        if (o == null) {
            return Lists.newArrayList();
        }
        if (o instanceof Collection) {
            Collection<?> list = (Collection<?>) o;
            return list.stream().map(DataConvertUtil::convertLong).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public static List<Long> splitLongList(String s) {
        return splitLongList(s, ",");
    }

    public static List<Long> splitLongList(String s, String regex) {
        if (StringUtils.isBlank(s)) {
            return Lists.newArrayList();
        }
        String[] split = s.split(regex);
        if (split.length > 0) {
            return Arrays.stream(split).map(DataConvertUtil::convertLong).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

}
