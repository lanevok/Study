import java.io.IOException;

public class iniRead {

    public static void main(String[] args) {
	iniRead.Format();
    }
    
    private static void Format(){
	try {
	    java.util.Properties prop = new java.util.Properties();
	    prop.load(new java.io.FileInputStream("settings.ini"));
	    int CPU = Integer.valueOf(prop.getProperty("CPU"));
	    System.out.println(CPU);
	} catch (IOException e) {e.printStackTrace();}
    }
}
