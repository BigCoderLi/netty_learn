package nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public class NioTest13 {
    public static void main(String[] args) throws IOException {
        String inputFile = "NioTest3_In.txt";
        String outputFile = "NioTest13_Out.txt";

        RandomAccessFile inputAccessFile = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outAccessFile = new RandomAccessFile(outputFile, "rw");

        long inputLength = new File(inputFile).length();
        FileChannel inputFileChannel = inputAccessFile.getChannel();
        FileChannel outputFileChannel = outAccessFile.getChannel();

        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);
        Charset charset = StandardCharsets.ISO_8859_1;
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(inputData);
        System.out.println(charBuffer.get(12));
        ByteBuffer outputData = encoder.encode(charBuffer);
        outputFileChannel.write(outputData);

        inputAccessFile.close();
        outAccessFile.close();
    }
}
