public class ExecutorPublish implements Runnable{
    // タスクを分離したときに使う変数宣言
    private String tab="";
    
    // クラス生成(タスク分離の初期設定)
    public ExecutorPublish(int tab){
        for(int i=0; i<=tab; i++){
            this.tab += " ";
        }
    }

    // 実行処理コード
    public void run(){
        for(int i=0; i<100; i++){
            System.out.println(tab + i);
        }
    }
}