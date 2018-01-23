package lab.dao.jdbc;

public interface DaoConstants {
    String CREATE_COUNTRY_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS Country (id IDENTITY, name VARCHAR (255), code_name VARCHAR (255))";

    String DROP_COUNTRY_TABLE_SQL = "DROP TABLE country";

}
