package transferToTest;

import multiThreads.Block;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wang on 17-9-20.
 */
public class Receiver implements Runnable {


    @Override
    public void run() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8899));
            SocketChannel serverChannel =  serverSocketChannel.accept();

            Block block = new Block();
            ByteBuffer buffer = ByteBuffer.allocate(block.getBlockSize());
            long length;
            long size = 0;

            long start = System.currentTimeMillis();
            while(true){
                length = serverChannel.read(buffer);
                if(length == -1)
                    break;
                size += length;
                buffer.clear();
//                buffer.clear();
            }

            //传输完成，关闭channel
            serverChannel.close();
            serverSocketChannel.close();

            long end = System.currentTimeMillis();

            System.out.println("读取数据的时间为" + (end-start)/1000 + "s, 读取的字节数为" + size);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
