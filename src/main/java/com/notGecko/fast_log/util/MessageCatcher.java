package com.notGecko.fast_log.util;

public final class MessageCatcher { // for debug in config
    private static boolean messageCatched = false;

    public static boolean isMessageCatched() {
        return messageCatched;
    }

    public static void setMessageCatched(boolean catched) {
        messageCatched = catched;
    }

    public static void reset() {
        messageCatched = false;
    }

    public static void activate() {
        setMessageCatched(true);
    }
}