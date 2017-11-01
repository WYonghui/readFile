package transferToTest;

import multiThreads.Block;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wang on 17-9-20.
 */
public class Main {

//    private boolean endFlag;

//    synchronized public boolean isEndFlag() {
//        return endFlag;
//    }
//
//    synchronized public void setEndFlag(boolean endFlag) {
//        this.endFlag = endFlag;
//    }

    public static void main(String[] args) {

        String path;
        if (args.length > 0)
            path = args[0];
        else
            path = "/root/wang/projects/hiberfil.sys";
//            path = "/home/wang/IdeaProjects/projects/intellij.pdf";

        File file = new File(path);

        try {
            FileInputStream inputStream = new FileInputStream(file);
            FileChannel inChannel = inputStream.getChannel();

            Receiver receiver = new Receiver();
            Thread thread = new Thread(receiver);
            thread.start();

            //创建socketchannel用于接收数据
            SocketChannel outChannel = SocketChannel.open();
            boolean connected = outChannel.connect(new InetSocketAddress("localhost", 8899));

            Block block = new Block();
            long start = System.currentTimeMillis();
            long length;
            long size = 0;
            long position = 0;
            while(true){

                length = inChannel.transferTo(position, block.getBlockSize(), outChannel);

                if (length <= 0){
                    outChannel.close();
                    inChannel.close();
                    break;
                }

                size += length;
                position = size;
            }

            long end = System.currentTimeMillis();

            System.out.println("传输数据的时间为" + (end-start)/1000 + "s, 传输的字节数为" + size);
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
