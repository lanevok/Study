// All Rights Reserved. Copyright (C) Kazuo Misue (2010)

public class MyEdge {
    String label;
    
    public MyEdge(String label) {
        this.label = label;
    }
    
    @Override
	public String toString() {
        return label;
    }
}