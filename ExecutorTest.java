import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTest {

	public static final int CORE = 4;		// CPUのコア数

	public static void main(String[] args) {
		// 指定したCORE数でExecutorServiceを生成する
		ExecutorService ex = Executors.newFixedThreadPool(CORE);
		System.err.println("[タスクの発行を開始]");
		for(int i=0; i<10; i++){
			// タスクを生成し実行させる (ExecutorPublish.java クラス)
			ex.execute(new ExecutorPublish(i*3));
		}
		System.err.println("[タスクの発行が完了]");
		ex.shutdown();		// タスクの発行を停止できる
		System.err.println("[タスクの発行を停止]");
		while(!ex.isTerminated());		// 全タスクが完了するまで待つ
		System.err.println("[タスクの実行が終了]");
	}
}
