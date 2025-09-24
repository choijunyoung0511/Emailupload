package kopo.poly.util;

public class CmmUtil {

    public static String nvl(String str, String chg_str) {
        if (str == null || str.equals("")) {
            return chg_str;
        } else {
            return str;
        }
    }

    public static String nvl(String str) {
        return nvl(str, "");
    }

    public static String checked(String str, String com_str) {
        if (str == null) { // null 방어
            return "";
        }
        if (str.equals(com_str)) {
            return " checked";
        } else {
            return "";
        }
    }

    public static String checked(String[] str, String com_str) {
        if (str == null || str.length == 0) { // null 및 빈 배열 방어
            return "";
        }
        for (String s : str) {
            if (s != null && s.equals(com_str)) { // 내부 null 방어
                return " checked";
            }
        }
        return "";
    }


    public static String select(String str, String com_str) {
        if (str == null) { // null 방어
            return "";
        }
        if (str.equals(com_str)) {
            return " selected";
        } else {
            return "";
        }
    }
}
