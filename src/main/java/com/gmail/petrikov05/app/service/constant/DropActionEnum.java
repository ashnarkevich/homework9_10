package com.gmail.petrikov05.app.service.constant;

public enum DropActionEnum {
    DROP_TABLE_USER_INFORMATION("DROP TABLE IF EXISTS user_information;"),
    DROP_TABLE_USER("DROP TABLE IF EXISTS user;");

    private final String query;

    DropActionEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
