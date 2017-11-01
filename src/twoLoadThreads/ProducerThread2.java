package twoLoadThreads;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by wang on 17-9-13.
 */
public class ProducerThread2 implements Runnable{

    private Main ma;
    private File file;
    private int loadEnable;  //加载磁盘准许线程编号。等于0时，准许线程0加载磁盘，等于1时准许线程1加载磁盘
                             //目的：实现顺序读取磁盘
    private boolean fileBegin;

    public ProducerThread2(Main ma, File file){
        this.ma = ma;
        this.file = file;

        this.loadEnable = 0;
//        this.offset = 0;
        this.fileBegin = true;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        Character c = name.charAt(name.length()-1);
        int threadNum = Integer.parseInt(c.toString()); //线程编号决定起始块号

        int i = threadNum;
        int endFile;

        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(inputStream);
//            long localOffset;
            long skipLength;
            int enableThread;
            while (true){
                if (ma.blocks[i].isEmpty()){// && loadEnable==threadNum
                    enableThread = ma.getLoadEnable();
                    if (enableThread == threadNum){

                        if (ma.isFileBegin()){
                            skipLength = 0;
                            ma.setFileBegin(false);
                        }else{
                            skipLength = ma.blocks[0].getBlockSize();
                        }
                        ma.setLoadEnable((enableThread+1) % 2);
//                        loadEnable = (loadEnable + 1) % 2;

                        in.skip(skipLength);
                        endFile = in.read(ma.blocks[i].buf);
                        ma.blocks[i].validBytes = endFile;
                        if (endFile == -1){
                            ma.endFlag ++;
                            ma.blocks[i].validBytes = 0;
                            break;
                        }
                        ma.blocks[i].setEmpty(false);
                        i = (i+2)%ma.blockNum;
                    }
                }
            }

            in.close();
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }


    }

}
