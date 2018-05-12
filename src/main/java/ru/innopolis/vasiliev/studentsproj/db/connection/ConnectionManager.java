package ru.innopolis.vasiliev.studentsproj.db.connection;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
}
