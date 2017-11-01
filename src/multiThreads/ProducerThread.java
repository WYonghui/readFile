package multiThreads;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by wang on 17-9-7.
 */
public class ProducerThread implements Runnable{
//    static int ids = 0;
    private Main ma;
//    private int id;
    private File file;

    public ProducerThread(Main ma, File file){
        this.ma = ma;
//        this.id = ids++;
        this.file = file;
    }

    @Override
    public void run() {
        int i = 0;
//        int offset = 0;
//        int blockSize = 1024*1024*256;
        int endFile;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedInputStream bufferedIn = new BufferedInputStream(inputStream);
            while (true){
                if (ma.blocks[i].isEmpty()){
//                    bufferedIn.skip()
                    endFile = bufferedIn.read(ma.blocks[i].buf);
                    ma.blocks[i].validBytes = endFile;
                    if (endFile == -1){
                        ma.endFlag = true;
                        ma.blocks[i].validBytes = 0;
                        break;
                    }
                    ma.blocks[i].setEmpty(false);
                    i = (i+1)%ma.blockNum;
                }
            }

            bufferedIn.close();
            inputStream.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }


//        while (true){
//            for (int i = id; i < ma.blockNum; i += ids) {  //遍历缓存数组，寻找空缓存块
//                if (ma.blocks[i].isEmpty()){
//
//                }
//
//
//            }
//        }

    }
}
