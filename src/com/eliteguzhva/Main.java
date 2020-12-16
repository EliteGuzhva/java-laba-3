package com.eliteguzhva;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please enter arguments: [dirName] [zipName]");
            System.exit(0);
        }

        if (args[0].endsWith(".zip")) {
            System.out.println("First argument should be a directory");
            System.exit(0);
        }

        if (!args[1].endsWith(".zip")) {
            System.out.println("Second argument should be a zip archive");
            System.exit(0);
        }

        String dirName = args[0];
        String zipName = args[1];

        Zipper zipper = new Zipper();
        boolean didCompress = zipper.compress(dirName, zipName);
        if (!didCompress) {
            System.out.println("Couldn't compress given directory");
        } else {
            System.out.println("Successfully compressed!");
        }
    }
}
