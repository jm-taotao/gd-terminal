package com.terminal.manage.tool;

import com.terminal.manage.model.User;

/**
 * @author TAO
 * @date 2022/7/16 / 18:40
 */
public class ThreadLocalAdminUser {

    private static final ThreadLocal<User> currUser = ThreadLocal.withInitial(User::new);

    public static User get() {
        return currUser.get();
    }

    public static void set(User value) {
        currUser.set(value);
    }

    public static void remove() {
        currUser.remove();
    }
}
