package operation;

public class Add {

	private int first;
	private int second;
	private int ans;

	public Add(){
		this.first = 0;
		this.second = 0;
		this.ans = 0;
	}

	public void setFirst(String number){
		this.first = Integer.parseInt(number);
	}

	public void setSecond(String number){
		this.second = Integer.parseInt(number);
	}

	public String getResult(){
		ans = first+second;
		return String.valueOf(ans);
	}
}
