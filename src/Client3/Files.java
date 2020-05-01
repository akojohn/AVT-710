package Client3;

import java.io.*;

public class Files
{
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private FileInputStream in = new FileInputStream("objects.out");
    private FileOutputStream out = new FileOutputStream("objects.out");

    Files() throws IOException
    {
        outputStream = new ObjectOutputStream(out);
        inputStream = new ObjectInputStream(in);
    }

    public void write(Object o) throws IOException {
        outputStream.writeObject(o);
    }

    public Object read() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    public void closeOut() throws IOException {
        outputStream.close();
    }

    public void closeIn() throws IOException {
        inputStream.close();
    }

    public int eof() throws IOException {
        return in.read();
    }
}
