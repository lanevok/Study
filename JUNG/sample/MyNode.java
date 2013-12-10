// All Rights Reserved. Copyright (C) Kazuo Misue (2010)

public class MyNode {
    String label;
    
    public MyNode(String label) {
        this.label = label;
    }
    
    @Override
	public String toString() {
        return label;
    }
}