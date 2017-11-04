package singleThread;

import multiThreads.Block;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by wang on 17-9-11.
 */
public class Main {
    Block block;

    public Main(){
        this.block = new Block();
    }

    public static void main(String[] args) {
        Main ma = new Main();
        String path;
        path = "/root/wang/projects/hiberfil.sys";
//                path = "/home/wang/IdeaProjects/projects/intellij.pdf";

        File file = new File(path);

        byte[] messages = new byte[ma.block.getBlockSize()];

        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(inputStream);

            long start = System.currentTimeMillis();
            long size = 0;

            int endFile;
            while (true){
//                in.skip(1048576L*2);
                endFile = in.read(ma.block.buf);
                ma.block.validBytes = endFile;

                if (endFile == -1){
                    ma.block.validBytes = 0;
                    break;
                }
                messages = ma.block.buf.clone();
                size += ma.block.validBytes;
            }

            long end = System.currentTimeMillis();

            System.out.println("读取数据的时间为" + (end-start)/1000 + "s, 读取的字节数为" + size);

            in.close();
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }




    }






}
