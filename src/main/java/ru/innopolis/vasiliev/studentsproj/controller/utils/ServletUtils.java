package ru.innopolis.vasiliev.studentsproj.controller.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.controller.AuthServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ServletUtils {
    private static final Logger logger = LogManager.getLogger(AuthServlet.class);
    public static void redirectTo(String url, HttpServletResponse resp) {
        try {
            resp.sendRedirect(url);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void setEncoding(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding(ConstHolder.UTF8ENCODING);
            resp.setCharacterEncoding(ConstHolder.UTF8ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }

    }
    private ServletUtils() {
        throw new IllegalStateException("Utility class");
    }
}
