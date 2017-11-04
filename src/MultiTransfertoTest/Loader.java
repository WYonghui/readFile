package MultiTransfertoTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wang on 17-10-20.
 */
public class Loader implements Runnable{

    private File file;
    private SocketChannel dst;

    public Loader(File file, SocketChannel dst)
    {
//        this.file = file;
        this.file = new File(file.getAbsolutePath());
        this.dst = dst;
    }

    @Override
    public void run() {

        String threadName = Thread.currentThread().getName();
        Character c = threadName.charAt(threadName.length()-1);
        int threadNum = Integer.parseInt(c.toString());  //线程编号

        try{
            FileInputStream inputStream = new FileInputStream(file);
            FileChannel fileChannel = inputStream.getChannel();

            //连接服务器
//            SocketChannel socketChannel = SocketChannel.open();
//            socketChannel.connect(new InetSocketAddress("localhost", 8899));

            long count = MultiTest.length;
            long position = 0 + threadNum*count;
            System.out.println("threadNum: " + threadNum);
//            long size = 0;

            while (true){

                //此处认为可以一次传输完
                long n = fileChannel.transferTo(position, count, dst);

                if (n <= 0){
                    break;
                }

//                size += n;
                position += count * MultiTest.loaderNum;
//                position += count * 2;

            }

//            socketChannel.close();
            inputStream.close();

//            System.out.println("sendSize = " + size);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
//        finally {
//
//            try {
//                //连接服务器
//                SocketChannel socketChannel = SocketChannel.open();
//                socketChannel.connect(new InetSocketAddress("localhost", 8899));
//
//                socketChannel.close();
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//
//        }

    }

}
