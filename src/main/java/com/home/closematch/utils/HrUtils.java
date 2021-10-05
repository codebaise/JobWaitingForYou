package com.home.closematch.utils;

import com.home.closematch.entity.Humanresoucres;

import java.lang.reflect.Field;
import java.util.List;

public class HrUtils {

    public void clearHrPrivateInfo(List<String> needList, List<Humanresoucres> humanresoucresList) {
        Class<Humanresoucres> clazz = Humanresoucres.class;
        Field[] declaredFields = clazz.getDeclaredFields();

    }
}
