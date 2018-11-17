package com.tvsonar.android.feimu.common;

public class  RegUtils {
    public static boolean isPhone(String phone){
        String reg = "^1[34578]\\d{9}$";
        return null != phone && phone.matches(reg);
    }


    public static boolean isEmail(String email){
        String reg = "^^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$";
        return null != email && email.matches(reg);
    }



    public static boolean isNumber(String number){
        String reg = "(-)?\\d+";
        return null != number && number.matches(reg);
    }

    public static boolean isIdCard(String idCard){
        String reg = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
        return null != idCard && idCard.matches(reg);
    }

    public static boolean isRegistCode(String idCard){
        String reg = "^\\d{6}$";
        return null != idCard && idCard.matches(reg);
    }

    public static boolean isUsefulPassword(String password){
        String reg = "^[a-zA-Z0-9_]{8,16}$";
        return null != password && password.matches(reg);
    }


}


