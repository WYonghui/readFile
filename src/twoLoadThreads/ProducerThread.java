package twoLoadThreads;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by wang on 17-9-11.
 */
public class ProducerThread implements Runnable {

    private Main ma;
    private File file;
    private int loadEnable;  //加载磁盘准许线程编号。等于0时，准许线程0加载磁盘，等于1时准许线程1加载磁盘
                            //目的：实现顺序读取磁盘
//    private long offset;
    private boolean fileBegin;

    public ProducerThread(Main ma, File file){
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

            while (true){
                if (ma.blocks[i].isEmpty()){// && loadEnable==threadNum
                    if (loadEnable == threadNum){

    //                    localOffset = ma.blocks[0].getBlockSize();
    //                    offset += ma.blocks[0].getBlockSize();
                        if (fileBegin){
                            skipLength = 0;
                            fileBegin = false;
                        }else{
                            skipLength = ma.blocks[0].getBlockSize()*9;
                        }
                        loadEnable = (loadEnable + 1) % 10;

    //                    in.skip(localOffset);

                        in.skip(skipLength);
                        endFile = in.read(ma.blocks[i].buf);
                        ma.blocks[i].validBytes = endFile;
                        if (endFile == -1){
                            ma.endFlag ++;
                            ma.blocks[i].validBytes = 0;
                            break;
                        }
                        ma.blocks[i].setEmpty(false);
                        i = (i+10)%ma.blockNum;
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
