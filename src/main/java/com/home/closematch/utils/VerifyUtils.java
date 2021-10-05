package com.home.closematch.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.closematch.exception.ServiceErrorException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class VerifyUtils {
    public static int VERIFY_CODE_LENGTH = 6;
    public static Map<String, String> verifyCodeMap = new HashMap<>();
    private static final Random random = new Random(22);

    public static void sendFormatJson(HttpServletResponse response, Object obj) throws IOException {
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(obj);
        out.write(s);
        out.flush();
        out.close();
    }

    public static String generateVerifyCode () {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < VerifyUtils.VERIFY_CODE_LENGTH; i++)
            result.append(String.valueOf(random.nextInt(10)));
        return result.toString();
    }

    public static boolean inspectCode(String email, String code){
        String trimEmail = email.trim();
        String s = VerifyUtils.verifyCodeMap.get(trimEmail);
        return code.equals(s);
    }
}
