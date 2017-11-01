package multiThreads;

/**
 * Created by wang on 17-9-7.
 */
public class Block {
    private boolean empty;
    public byte[] buf;
    public int validBytes;
    private int blockSize;

    public Block(){
        this.empty = true;
//        this.blockSize = 1024*1024*128;
        this.blockSize = 1024*10;
        buf = new byte[blockSize];   //每个缓冲块大小为128MB
    }

    synchronized public boolean isEmpty() {
        return empty;
    }

    synchronized public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getBlockSize() {
        return blockSize;
    }

    //    public byte[] getBuf() {
//        return buf;
//    }
//
//    public void setBuf(byte[] buf) {
//        this.buf = buf;
//    }
}
