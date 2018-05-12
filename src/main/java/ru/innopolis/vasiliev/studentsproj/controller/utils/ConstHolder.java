package ru.innopolis.vasiliev.studentsproj.controller.utils;

public class ConstHolder {
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_LGN = "login";
    public static final String PARAM_PW = "password";
    public static final String PARAM_LOGOUT = "logout";
    public static final String PARAM_USERTYPE = "userType";

    public static final String ATR_CONTENT = "content";
    public static final String ATR_TEACHER = "teacher";
    public static final String ATR_GRADE = "grade";

    public static final String URL_DASHBOARD = "/dashboard";
    public static final String URL_AUTH_ERROR = "/auth?errorMsg=authErr";
    public static final String URL_UNDEF_ERROR = "/auth?errorMsg=UnError";

    public static final String JSP_AUTH = "/auth.jsp";
    public static final String JSP_DASHBOARD = "/dash.jsp";

    static final String UTF8ENCODING = "UTF-8";

    private ConstHolder() {
        throw new IllegalStateException("Utility class");
    }
}
