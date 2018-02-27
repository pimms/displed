package com.jstien.displed;

import jnr.ffi.LibraryLoader;

public class JNRTest {
    public static interface LibC {
        int puts(String s);
        int printf(String s);
    }

    private LibC lib;

    public JNRTest() {
        lib = LibraryLoader.create(LibC.class).load("c");
    }

    public void PutString(String str) {
        System.out.print("puts: ");
        lib.puts(str);
        System.out.flush();
    }

    public void Print(String str) {
        System.out.print("printf: ");
        lib.printf(str);
        System.out.flush();
    }
}
