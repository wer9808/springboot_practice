package com.example.article_crud.support.fixtures;

import java.util.List;
import java.util.UUID;

public class UserFixture {

    public static final List<UUID> ALL_USERS = List.of(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID()
    );

    public static UUID default_user_id() {
        return ALL_USERS.get(0);
    }

    public static UUID random_user_id() {
        return ALL_USERS.get((int) (Math.random() * ALL_USERS.size()));
    }

}
