// All Rights Reserved. Copyright (C) Kazuo Misue (2010)

import java.awt.Dimension;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

public class Sample {

    public static void main(String[] args) {
        Graph<MyNode,MyEdge> graph = new UndirectedSparseGraph<MyNode,MyEdge>();
        MyNode n1 = new MyNode("n1");
        MyNode n2 = new MyNode("n2");
        MyNode n3 = new MyNode("n3");
        graph.addVertex(n1);
        graph.addVertex(n2);
        graph.addVertex(n3);
        graph.addEdge(new MyEdge("e1"), n1, n2);
        graph.addEdge(new MyEdge("e2"), n2, n3);
	
        Layout<MyNode,MyEdge> layout = new StaticLayout<MyNode,MyEdge>(graph);
        layout.setLocation(n1, new Point2D.Double(100, 100));
        layout.setLocation(n2, new Point2D.Double(200, 100));
        layout.setLocation(n3, new Point2D.Double(150, 200));
	
        BasicVisualizationServer<MyNode,MyEdge> panel = new BasicVisualizationServer<MyNode,MyEdge>(layout, new Dimension(300, 300));
	
        JFrame frame = new JFrame("Graph View: Manual Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
}