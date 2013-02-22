public class CreateMail {
    String subject;
    String msgText;
    String to;
    String from;
    String host;
    boolean debug;
    
    /*
     * ライブラリが必要
     * 1. javamail-1.4.5 (mail.jar)
     * 2. jaf-1.1.1 (activation.jar)
     */
    
    public static void main(String[] args) {
	CreateMail cm = new CreateMail();
	cm.subject = "Title";
	cm.msgText = "This is a message body.\nHere's the second line.";
	cm.to = "sample@lanevok.com";
	cm.from = "sample@lanevok.com";
	cm.host = "smtp.lolipop.jp";
	cm.debug = true;
	msgsendsample.requestMail(cm);
    }
}
