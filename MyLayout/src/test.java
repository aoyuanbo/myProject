

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import org.gephi.datalab.api.AttributeColumnsController;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.generator.plugin.RandomGraph;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;
import org.gephi.preview.api.G2DTarget;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.types.DependantOriginalColor;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;



public class test {
	
	public static void main(String[] args) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		//鍒濆鍖栦竴涓伐浣滅┖闂�
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();
        
      //Get models and controllers for this new workspace - will be useful later
          GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
          PreviewModel model = Lookup.getDefault().lookup(PreviewController.class).getModel();
          ImportController importController = Lookup.getDefault().lookup(ImportController.class);
          AttributeColumnsController attributeColumnsController=Lookup.getDefault().lookup(AttributeColumnsController.class);
//        
//        
//
        String [] columnNames1={"Id","Label","Type"};
        String [] columnNames2={"Source","Target"};
        Class [] columnType1={String.class,String.class,String.class};
        Class [] columnType2={String.class,String.class};
        File nodeFile=new File("C:\\Users\\lenovo\\Desktop\\csv\\node1.csv");
        File edgeFile=new File("C:\\Users\\lenovo\\Desktop\\csv\\Edge11.csv");
        attributeColumnsController.importCSVToNodesTable(graphModel.getUndirectedGraph(), nodeFile, ',', Charset.forName("GBK"), columnNames1, columnType1, false);
        attributeColumnsController.importCSVToEdgesTable(graphModel.getUndirectedGraph(), edgeFile, ',', Charset.forName("GBK"), columnNames2, columnType2, true);

//        Container container = Lookup.getDefault().lookup(Container.Factory.class).newContainer();
//        RandomGraph randomGraph = new RandomGraph();
//        randomGraph.setNumberOfNodes(10);
//        randomGraph.setWiringProbability(0.05);
//        randomGraph.generate(container.getLoader());
        
//        Node n0 = graphModel.factory().newNode("n0");
//        n0.setLabel("Node 0");
//        Node n1 = graphModel.factory().newNode("n1");
//        n1.setLabel("Node 1");
//        Node n2 = graphModel.factory().newNode("n2");
//        n2.setLabel("Node 2");
//
//        //Create three edges
//        Edge e1 = graphModel.factory().newEdge(n1, n2, 0, 1.0, true);
//        Edge e2 = graphModel.factory().newEdge(n0, n2, 0, 2.0, true);
//        Edge e3 = graphModel.factory().newEdge(n2, n0, 0, 2.0, true);   //This is e2's mutual edge
//
//        //Append as a Directed Graph
//        DirectedGraph directedGraph = graphModel.getDirectedGraph();
//        directedGraph.addNode(n0);
//        directedGraph.addNode(n1);
//        directedGraph.addNode(n2);
//        directedGraph.addEdge(e1);
//        directedGraph.addEdge(e2);
//        directedGraph.addEdge(e3);
        //Append container to graph structure
//        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
//        importController.process(container, new DefaultProcessor(), workspace);

        //See if graph is well imported
//        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
//        DirectedGraph graph = graphModel.getDirectedGraph();
        
        //Preview configuration
      		
              PreviewController previewController = Lookup.getDefault().lookup(PreviewController.class);
              PreviewModel previewModel = previewController.getModel();
              previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
              previewModel.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.RED));
              previewModel.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.8f));
              previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, new Font("宋体", Font.PLAIN, 10));
              previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.GREEN));
              previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
              previewModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 50);

    
        //New Processing target, get the PApplet
        G2DTarget target = (G2DTarget) previewController.getRenderTarget(RenderTarget.G2D_TARGET);
        final PreviewSketch previewSketch = new PreviewSketch(target);
        previewController.refreshPreview();

        
        //Add the applet to a JFrame and display
        JFrame frame = new JFrame("Test Preview");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(previewSketch, BorderLayout.CENTER);
        frame.setSize(500, 500);
       
        //Wait for the frame to be visible before painting, or the result drawing will be strange
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                previewSketch.resetZoom();
            }
        });
        frame.setVisible(true);
        
        MyLayout1 myLayout1=new MyLayout1(null);
        myLayout1.setGraphModel(graphModel);
        myLayout1.initAlgo();
        for (int i = 0; i < 20000 && myLayout1.canAlgo(); i++) {
            myLayout1.goAlgo();
        }
        myLayout1.endAlgo();

        System.out.println("jiesu");
        
        
//        MyLayout1 myLayout1=new MyLayout1(null);
//        myLayout1.setGraphModel(graphModel);
//        myLayout1.initAlgo();
//        for (int i = 0; i < 20000 && myLayout1.canAlgo(); i++) {
//            myLayout1.goAlgo();
//        }
//        myLayout1.endAlgo();
//
//        System.out.println("jiesu1");
//        AutoLayout autoLayout = new AutoLayout(10, TimeUnit.SECONDS);
//        autoLayout.setGraphModel(graphModel);
//        ForceAtlasLayout secondLayout = new ForceAtlasLayout(null);
//        AutoLayout.DynamicProperty adjustBySizeProperty = AutoLayout.createDynamicProperty("forceAtlas.adjustSizes.name", Boolean.TRUE, 0.1f);//True after 10% of layout time
//        AutoLayout.DynamicProperty repulsionProperty = AutoLayout.createDynamicProperty("forceAtlas.repulsionStrength.name", new Double(500.), 0f);//500 for the complete period
//        AutoLayout.DynamicProperty maxDisplacement = AutoLayout.createDynamicProperty("forceAtlas.maxDisplacement.name", new Double(5.), 0f);//500 for the complete period
//        AutoLayout.DynamicProperty gravity = AutoLayout.createDynamicProperty("forceAtlas.gravity.name", new Double(100.), 0f);//500 for the complete period
//        AutoLayout.DynamicProperty inertia = AutoLayout.createDynamicProperty("forceAtlas.inertia.name", new Double(10.), 0f);//500 for the complete period
//        AutoLayout.DynamicProperty freezeStrength = AutoLayout.createDynamicProperty("forceAtlas.freezeStrength.name", new Double(1000.), 0.1f);//500 for the complete period
//        autoLayout.addLayout(secondLayout, 1f, new AutoLayout.DynamicProperty[]{adjustBySizeProperty, repulsionProperty,maxDisplacement,gravity,inertia,freezeStrength});
//        autoLayout.execute();
//        System.out.println("jiesu");
        
//        MyLayout myLayout=new MyLayout(null);
//        myLayout.setGraphModel(graphModel);
//        myLayout.initAlgo();
//        for (int i = 0; i < 2000 && myLayout.canAlgo(); i++) {
//            myLayout.goAlgo();
//        }
//        myLayout.endAlgo();
//        ExportController ec = Lookup.getDefault().lookup(ExportController.class);
//        try {
//            ec.exportFile(new File("mygraph.pdf"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return;
//        }
	}

}
