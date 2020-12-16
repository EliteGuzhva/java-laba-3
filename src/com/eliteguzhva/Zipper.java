package com.eliteguzhva;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.*;

public class Zipper {
    private ZipOutputStream zip;

    public boolean compress(String dirName, String zipName) {
        try {
            zipit(dirName, zipName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private void zipit(String dirName, String zipName) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(zipName);
        zip = new ZipOutputStream(fos);
        zip.setLevel(0);
        zip.setMethod(ZipOutputStream.DEFLATED);

        File dir = new File(dirName);
        if (!dir.exists()) {
            throw new IOException(dirName + " doesn't exist");
        }

        scanDirectory(dir, dir.getName());

        zip.finish();
    }

    private void scanDirectory(File dir, String parent) throws IOException {
        for(File file : dir.listFiles()) {
            if (!file.isHidden()) {
                String entryName = file.getName();
                if (!parent.equals("")) {
                    entryName = parent + "/" + entryName;
                }

                if (file.isFile() && file.exists())
                    putEntry(zip, entryName, file.getPath());
                else {
                    putDirEntry(zip, entryName);
                    if (!parent.equals(""))
                        parent += "/";
                    parent += file.getName();
                    scanDirectory(file, parent);
                }
            }
        }
    }

    private void putEntry(ZipOutputStream zip, String entryName, String pathToFile) throws IOException
    {
        ZipEntry entry = new ZipEntry(entryName);
        zip.putNextEntry(entry);

        File file = new File(pathToFile);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();

        zip.write(buffer);
    }

    private static void putDirEntry(ZipOutputStream zip, String entryName) throws IOException
    {
        ZipEntry entry = new ZipEntry(entryName + "/");
        zip.putNextEntry(entry);
    }

}

