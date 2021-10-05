package com.home.closematch.utils;

public class SeekerDeliverUtils {
    private SeekerDeliverUtils() {}
    public static final Integer STATUS_CHECK_WAIT = 0;
    public static final Integer STATUS_CHECK_REFUSE = 1;
    public static final Integer STATUS_CHECK_PASS = 2;

    public static String formatCheckCommentToSeeker(String positionName, Integer checkStatus){
        String checkString = checkStatus.equals(STATUS_CHECK_PASS) ? "通过" : "拒绝";
        String postString = checkStatus.equals(STATUS_CHECK_PASS) ? "请准备好面试" : "请继续投递您的简历, 加油!!!";
        return "您申请的 [" + positionName + "] 审批状态为: " + checkString + ", " + postString;
    }

    public static String formatCheckCommentToHr(String seekerName, String positionName, Integer checkStatus){
        String checkString = checkStatus.equals(STATUS_CHECK_PASS) ? "通过" : "拒绝";
        return "您已经" + checkString + "了 [" + seekerName + "] 对 [" + positionName + "] 职位的职位申请";
    }
}
