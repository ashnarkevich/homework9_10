package com.gmail.petrikov05.app.service.constant;

public enum CreateActionEnum {
    CREATE_TABLE_USER("CREATE TABLE IF NOT EXISTS user(\n" +
            "id INT(11) PRIMARY KEY AUTO_INCREMENT,\n" +
            "username VARCHAR(40) NOT NULL,\n" +
            "password VARCHAR(40) NOT NULL,\n" +
            "is_active TINYINT(1) NOT NULL DEFAULT true,\n" +
            "age INT(11) NOT NULL);"),
    CREATE_TABLE_USER_INFORMATION("CREATE TABLE IF NOT EXISTS user_information(\n" +
            "user_id INT(11) PRIMARY KEY,\n" +
            "address VARCHAR(100) NOT NULL,\n" +
            "telephone VARCHAR(40) NOT NULL,\n" +
            "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE);");

    private final String query;

    CreateActionEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
