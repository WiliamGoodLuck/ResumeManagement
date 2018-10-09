package com.code.resumemanagement.utils;

import com.code.resumemanagement.domain.Personnel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseFileUtil {
    private static Pattern pattern = null;
    private static Matcher matcher = null;
    private static String str = "";

    public static Personnel parsePersonnel(String content, String fileName) {
        Personnel personnel = new Personnel();
        content = content.replaceAll(" ", "");
        //获取姓名
        personnel = getUserName(personnel, fileName);
        //性别
        personnel = getGender(personnel, content);
        //年龄
        personnel = getAge(personnel, content);
        //学历
        personnel = getEducation(personnel, content);
        //工作年限
        personnel = getServiceYear(personnel, content);
        //工作分类
        personnel = getWorkType(personnel, fileName);
        //手机
        personnel = getPhoneNumber(personnel, content);
        //邮箱
        personnel = getEmail(personnel, content);
        return personnel;
    }

    //获取姓名
    private static Personnel getUserName(Personnel personnel, String fileName) {
        //姓名
        int i = fileName.indexOf("-");
        if (i > 0) {
            personnel.setUserName(fileName.substring(0, i));
        } else {
            personnel.setUserName(fileName.substring(0, fileName.length() >= 3 ? 3 : fileName.length()));
        }


        return personnel;
    }

    //获取性别
    private static Personnel getGender(Personnel personnel, String content) {
        int i = 0;
        int j = 0;
        pattern = Pattern.compile("男");
        matcher = pattern.matcher(content);
        while (matcher.find()) {
            i = i + 1;
        }
        pattern = Pattern.compile("女");
        matcher = pattern.matcher(content);
        while (matcher.find()) {
            j = j + 1;
        }
        if (i > j) {
            if (j != 0) {
                personnel.setGender("2");
            } else {
                personnel.setGender("1");
            }
        } else if (i < j) {
            if (i != 0) {
                personnel.setGender("1");
            } else {
                personnel.setGender("2");
            }
        }
        return personnel;
    }

    //获取年龄
    private static Personnel getAge(Personnel personnel, String content) {
        pattern = Pattern.compile("(19)\\d{2}$*");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            Integer age = Integer.parseInt(DateUtil.getCurrentYear()) - Integer.parseInt(matcher.group().toString());
            if (age != null) {
                personnel.setAge(age);
            }
        } else {
            pattern = Pattern.compile("年龄" + ".{0,3}");
            matcher = pattern.matcher(content);
            while (matcher.find()) {
                str = matcher.group().toString().trim();
                if (str.indexOf("：") != -1) {
                    str = str.substring(str.indexOf("：") + 1, str.length());
                }
                if (str.indexOf(":") != -1) {
                    str = str.substring(str.indexOf(":") + 1, str.length());
                }
                int age;
                try {
                    age = Integer.parseInt(str.trim());
                    personnel.setAge(age);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return personnel;
    }

    //获取学历
    private static Personnel getEducation(Personnel personnel, String content) {
        pattern = Pattern.compile("博士|硕士|研究生|本科|一本|二本|专科|大专");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            String str = matcher.group().toString().trim();
            personnel.setEducation(str);
        } else {
            personnel.setEducation("其他");
        }
        return personnel;
    }

    //获取工作年限
    private static Personnel getServiceYear(Personnel personnel, String content) {
        pattern = Pattern.compile(".{0,5}" + "工作经验" + ".{0,5}");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            String str = matcher.group().toString().trim();
            System.out.println(str);
            int year = str.indexOf("年");
            int servie = str.indexOf("工作经验");
            //若格式为"X年工作经验"
            if (year != -1 && year < servie) {
                pattern = Pattern.compile("[0-9]*$");
                matcher = pattern.matcher(str.substring(0, year));
                if (matcher.find()) {
                    str = matcher.group().toString().trim();
                    personnel.setServiceYear(Integer.parseInt(str));
                }

            } else if (year > servie) {
                //若格式为“工作经验：X年”
                pattern = Pattern.compile("[0-9]*$");
                matcher = pattern.matcher(str.substring(servie, year));
                if (matcher.find()) {
                    str = matcher.group().toString().trim();
                    personnel.setServiceYear(Integer.parseInt(str));
                }
            } else {
                //没有找到年
                personnel.setServiceYear(0);
            }
        } else {
            personnel.setServiceYear(0);
        }

        return personnel;
    }

    //获取工作分类
    private static Personnel getWorkType(Personnel personnel, String fileName) {
        int i = fileName.indexOf("-");
        int j = fileName.lastIndexOf(".");
        if (i > 0 && j > i) {
            personnel.setWorkType(fileName.substring(i + 1, j));
        } else {
            personnel.setWorkType("未知");
        }
        return personnel;
    }

    //获取手机号
    private static Personnel getPhoneNumber(Personnel personnel, String content) {

        // pattern = Pattern.compile("(1|86-?1)((3|7|5|8)\\d{9}$*|(3|7|5|8)\\d{1}-?\\d{4}-?\\d{4})");
        pattern = Pattern.compile("(1|86-?1)((3|7|5|8)\\d{1}-?\\d{4}-?\\d{4}$*)");
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            str = matcher.group().toString().trim().replaceAll("-", "");

            personnel.setPhoneNumber(str);

        }
        return personnel;
    }

    //获取邮箱
    private static Personnel getEmail(Personnel personnel, String content) {

        pattern = Pattern.compile("\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}");
        matcher = pattern.matcher(content);
        while (matcher.find()) {
            str = matcher.group().toString().trim();

            personnel.setEmail(str);

        }
        return personnel;
    }

}
