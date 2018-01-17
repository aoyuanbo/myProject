import org.gephi.graph.api.Node;

public class MyLayoutUtils {
	
	public static float distance(Node n1, Node n2){
		return (float) Math.hypot(n1.x() - n2.x(), n1.y() - n2.y());
	}
	
	//分散点
	public static void dispersion(Node n1,Node n2,double c3){
		MyLayoutData n1l=n1.getLayoutData();
		MyLayoutData n2l=n2.getLayoutData();
		
	    double xDist = n1.x() - n2.x();
	    double yDist = n1.y() - n2.y();
	    double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);
	    
		float repF=-(float) (c3/(Math.sqrt(dist)));
		
	    n1l.dx += xDist / dist * repF;
        n1l.dy += yDist / dist * repF;

        n2l.dx -= xDist / dist * repF;
        n2l.dy -= yDist / dist * repF;
	}
	
	public static void close(Node n1, Node n2, double c1, double c2){
		MyLayoutData n1l=n1.getLayoutData();
		MyLayoutData n2l=n2.getLayoutData();
		
	    double xDist = n1.x() - n2.x();
	    double yDist = n1.y() - n2.y();
	    double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);
	    
	    float attrF=(float) (c1*Math.log10(dist/c2));
        
	    n1l.dx += xDist / dist * attrF;
        n1l.dy += yDist / dist * attrF;

        n2l.dx -= xDist / dist * attrF;
        n2l.dy -= yDist / dist * attrF;
	    
	}
	
	//同一类别，吸引
	public static void uniTypeAttractor(Node n1, Node n2, double c){
		MyLayoutData n1l=n1.getLayoutData();
		MyLayoutData n2l=n2.getLayoutData();
		
		
	    double xDist = n1.x() - n2.x();
	    double yDist = n1.y() - n2.y();
	    double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);

	    double f = attraction(c, dist);
	    
        n1l.dx += xDist / dist * f;
        n1l.dy += yDist / dist * f;

        n2l.dx -= xDist / dist * f;
        n2l.dy -= yDist / dist * f;
		   
	}
	
	//有边连接，吸引
	public static void edgeAttractor(Node n1, Node n2, double c){
		MyLayoutData n1l=n1.getLayoutData();
		MyLayoutData n2l=n2.getLayoutData();
	
	    double xDist = n1.x() - n2.x();
	    double yDist = n1.y() - n2.y();
	    double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);
	    System.out.println("dist"+dist);

	    double f = attraction(c, dist);
	    
        n1l.dx += xDist / dist * f;
        n1l.dy += yDist / dist * f;

        n2l.dx -= xDist / dist * f;
        n2l.dy -= yDist / dist * f;
		
	}
	
	//不同类别，斥力
	public static void diffTypeRepulsion(Node n1, Node n2, double c){
		MyLayoutData n1l=n1.getLayoutData();
		MyLayoutData n2l=n2.getLayoutData();
		
	
	    double xDist = n1.x() - n2.x();
	    double yDist = n1.y() - n2.y();
	    double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);
	    
	    double f = repulsion(c, dist);
	    
        n1l.dx += xDist / dist * f;
        n1l.dy += yDist / dist * f;

        n2l.dx -= xDist / dist * f;
        n2l.dy -= yDist / dist * f;
		
	}
	
	
	
    protected static double attraction(double c, double dist) {
        return 0.01 * -c * dist;
    }

    protected static double repulsion(double c, double dist) {
        return 0.001 * c / dist;
    }
    
    public static void getPos(Node n){
    	System.out.println("点n的x为："+n.x()+",y为："+n.y());
    }
}
