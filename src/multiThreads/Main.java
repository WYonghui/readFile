package multiThreads;


import java.io.File;

/**
 * Created by wang on 17-9-7.
 */
public class Main {

    Block[] blocks;
    int blockNum;
    boolean endFlag;

    public Main(){
        blockNum = 10;                //10个缓存块
        blocks = new Block[blockNum];
        for (int i = 0; i < blockNum; i++) {
            blocks[i] = new Block();
        }

        endFlag = false;

    }

    public static void main(String[] args) {
        Main ma = new Main();
        String path;
        if (args.length > 0)
        {
//            System.out.println(args.length);
            path = args[0];
        }
        else
//            path = "/root/wang/projects/hiberfil.sys";
                path = "/home/wang/IdeaProjects/projects/intellij.pdf";
        File file = new File(path);

        ProducerThread producer = new ProducerThread(ma, file);
        Thread thread = new Thread(producer);
        thread.start();

//        System.out.println(ma.blocks[0].getBlockSize());
        byte[] messaage = new byte[ma.blocks[0].getBlockSize()];
        long size = 0;

        long start = System.currentTimeMillis();
        for (int i = 0; true; i = (i+1)%ma.blockNum) {
            if (ma.endFlag && ma.blocks[i].isEmpty()){
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







}
