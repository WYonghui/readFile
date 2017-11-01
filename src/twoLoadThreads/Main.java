package twoLoadThreads;

import multiThreads.Block;

import java.io.File;

/**
 * Created by wang on 17-9-11.
 */
public class Main {

    Block[] blocks;
    int blockNum;
    int endFlag;

    private int loadEnable;
    private boolean fileBegin;

    public Main(){
        blockNum = 10;                //10个缓存块
        blocks = new Block[blockNum];
        for (int i = 0; i < blockNum; i++) {
            blocks[i] = new Block();
        }

        endFlag = 0;

        this.loadEnable = 0;
        this.fileBegin = true;
    }

    public static void main(String[] args) {
        Main ma = new Main();
        String path;

//        path = "/root/wang/projects/hiberfil.sys";
        path = "/home/wang/IdeaProjects/projects/intellij.pdf";

        File file = new File(path);

        long start = System.currentTimeMillis();

        ProducerThread producer1 = new ProducerThread(ma, file);
//        ProducerThread2 producer2 = new ProducerThread2(ma, file);
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(producer1);
            threads[i].start();
        }
//        Thread thread1 = new Thread(producer1);
//        Thread thread2 = new Thread(producer1);

//        thread1.start();
//        thread2.start();

        byte[] messaage = new byte[ma.blocks[0].getBlockSize()];
        long size = 0;

        for (int i = 0; true; i = (i+1)%ma.blockNum) {
            if (ma.endFlag==10 && ma.blocks[i].isEmpty()){
                break;
            }

            if (ma.blocks[i].isEmpty()){
                i--;
                continue;
            }
            messaage = ma.blocks[i].buf.clone();
            size += ma.blocks[i].validBytes;
            ma.blocks[i].setEmpty(true);
        }
        long end = System.currentTimeMillis();

        System.out.println("读取数据的时间为" + (end-start)/1000 + "s, 读取的字节数为" + size);

    }

    synchronized public int getLoadEnable() {
        return loadEnable;
    }

    synchronized public void setLoadEnable(int loadEnable) {
        this.loadEnable = loadEnable;
    }

    public boolean isFileBegin() {
        return fileBegin;
    }

    public void setFileBegin(boolean fileBegin) {
        this.fileBegin = fileBegin;
    }
}
