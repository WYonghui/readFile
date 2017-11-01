package MultiTransfertoTest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by wang on 17-10-20.
 */
public class Receiver implements Runnable{

    private SocketChannel socketChannel;

    public Receiver(SocketChannel s) {
        this.socketChannel = s;
    }

    @Override
    public void run() {

        ByteBuffer buffer = ByteBuffer.allocate(MultiTest.length);
        long length;
        long size = 0;
        try {
            while(true){
                length = socketChannel.read(buffer);
                if(length == -1)
                    break;
                size += length;
                buffer.clear();
            }

            //传输完成，关闭channel
            socketChannel.close();

            System.out.println("size = " + size);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
