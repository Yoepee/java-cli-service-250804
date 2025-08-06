package com.object;

public class Main {
    public static void main(String[] args) {
        AppContext.renew();
        new App().run();
    }
}
