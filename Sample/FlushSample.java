import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;


public class FlushSample {

    public static void main(String[] args) throws IOException, InterruptedException {
	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("output.txt"))));

	bw.write("a");
	bw.write("b");
	bw.flush();
	TimeUnit.SECONDS.sleep(20);
	bw.write("c");
	bw.write("d");
	bw.close();
    }
}
