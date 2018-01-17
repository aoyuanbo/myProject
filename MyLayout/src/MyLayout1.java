/*
 Copyright 2008-2010 Gephi
 Authors : Mathieu Jacomy
 Website : http://www.gephi.org

 This file is part of Gephi.

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 2011 Gephi Consortium. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 3 only ("GPL") or the Common
 Development and Distribution License("CDDL") (collectively, the
 "License"). You may not use this file except in compliance with the
 License. You can obtain a copy of the License at
 http://gephi.org/about/legal/license-notice/
 or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
 specific language governing permissions and limitations under the
 License.  When distributing the software, include this License Header
 Notice in each file and include the License files at
 /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
 License Header, with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 If you wish your version of this file to be governed by only the CDDL
 or only the GPL Version 3, indicate your decision by adding
 "[Contributor] elects to include this software in this distribution
 under the [CDDL or GPL Version 3] license." If you do not indicate a
 single choice of license, a recipient has the option to distribute
 your version of this file under either the CDDL, the GPL Version 3 or
 to extend the choice of license to its licensees as provided above.
 However, if you add GPL Version 3 code and therefore, elected the GPL
 Version 3 license, then the option applies only if the new code is
 made subject to such option by the copyright holder.

 Contributor(s):

 Portions Copyrighted 2011 Gephi Consortium.
 */


import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;
import org.gephi.layout.plugin.AbstractLayout;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutProperty;
import org.openide.util.NbBundle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.Exceptions;

/**
 *
 * @author Mathieu Jacomy
 */
public class MyLayout1 extends AbstractLayout implements Layout {

    private static final float SPEED_DIVISOR = 800;
    private static final float AREA_MULTIPLICATOR = 10000;
    //Graph
    protected Graph graph;
    //Properties
    private float area=10000;
    private double gravity=100;
    private double speed=1;

    public MyLayout1(LayoutBuilder layoutBuilder) {
        super(layoutBuilder);
    }

    @Override
    public void resetPropertiesValues() {
        speed = 1;
        area = 10000;
        gravity = 1;
    }

    @Override
    public void initAlgo() {
    }

    @Override
    public void goAlgo() {
        this.graph = graphModel.getGraphVisible();
        graph.readLock();
        try {
            Node[] nodes = graph.getNodes().toArray();
            Edge[] edges = graph.getEdges().toArray();

            for (Node n : nodes) {
                if (n.getLayoutData() == null || !(n.getLayoutData() instanceof MyLayoutData)) {
                    n.setLayoutData(new MyLayoutData());
                }
                MyLayoutData layoutData = n.getLayoutData();
                layoutData.dx = 0;
                layoutData.dy = 0;
            }

            float maxDisplace = (float) (Math.sqrt(AREA_MULTIPLICATOR * area) / 10f);                    // D��placement limite : on peut le calibrer...�ƶ����ƣ�����У׼
            float k = (float) Math.sqrt((AREA_MULTIPLICATOR * area) / (1f + nodes.length));        // La variable k, l'id��e principale du layout.

            for (Node N1 : nodes) {
                for (Node N2 : nodes) {    // On fait toutes les paires de noeuds
                    if (N1 != N2) {
                        float xDist = N1.x() - N2.x();    // distance en x entre les deux noeuds
                        float yDist = N1.y() - N2.y();
                        float dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);    // distance tout court

                        if (dist > 0) {
                            float repulsiveF = k * k / dist;            // Force de r��pulsion
                            MyLayoutData layoutData = N1.getLayoutData();
                            layoutData.dx += xDist / dist * repulsiveF*0.01;        // on l'applique...
                            layoutData.dy += yDist / dist * repulsiveF*0.01;
                        }
                    }
                }
            }
            for (Edge E : edges) {
                // Idem, pour tous les noeuds on applique la force d'attraction

                Node Nf = E.getSource();
                Node Nt = E.getTarget();

                float xDist = Nf.x() - Nt.x();
                float yDist = Nf.y() - Nt.y();
                float dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);

                float attractiveF = dist * dist / k;

                if (dist > 0) {
                    MyLayoutData sourceLayoutData = Nf.getLayoutData();
                    MyLayoutData targetLayoutData = Nt.getLayoutData();
                    sourceLayoutData.dx -= xDist / dist * attractiveF;
                    sourceLayoutData.dy -= yDist / dist * attractiveF;
                    targetLayoutData.dx += xDist / dist * attractiveF;
                    targetLayoutData.dy += yDist / dist * attractiveF;
                }
            }
            for(Node n1 : nodes){
            	for(Node n2 : nodes){
            		if(n1!=n2){
            			if(n1.getAttribute("Type").equals(n2.getAttribute("Type"))){
                          float xDist = n1.x() - n2.x();
                          float yDist = n1.y() - n2.y();
                          float dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);
          
                          float attractiveF = dist * dist / k;
          
                          if (dist > 0) {
                              MyLayoutData sourceLayoutData = n1.getLayoutData();
                              MyLayoutData targetLayoutData = n2.getLayoutData();
                              sourceLayoutData.dx -= xDist / dist * attractiveF;
                              sourceLayoutData.dy -= yDist / dist * attractiveF;
                              targetLayoutData.dx += xDist / dist * attractiveF;
                              targetLayoutData.dy += yDist / dist * attractiveF;
                          }         				
            			}
            		}
            	}           	
            }
//            // gravity
//            for (Node n : nodes) {
//                MyLayoutData layoutData = n.getLayoutData();
//                float d = (float) Math.sqrt(n.x() * n.x() + n.y() * n.y());
//                float gf = 0.01f * k * (float) gravity * d;
//                layoutData.dx -= gf * n.x() / d;
//                layoutData.dy -= gf * n.y() / d;
//            }
            // speed
            for (Node n : nodes) {
                MyLayoutData layoutData = n.getLayoutData();
                layoutData.dx *= speed / SPEED_DIVISOR;
                layoutData.dy *= speed / SPEED_DIVISOR;
            }
            for (Node n : nodes) {
                // Maintenant on applique le d��placement calcul�� sur les noeuds.
                // nb : le d��placement �� chaque passe "instantann��" correspond �� la force : c'est une sorte d'acc��l��ration.
                MyLayoutData layoutData = n.getLayoutData();
                float xDist = layoutData.dx;
                float yDist = layoutData.dy;
                float dist = (float) Math.sqrt(layoutData.dx * layoutData.dx + layoutData.dy * layoutData.dy);
                if (dist > 0 && !n.isFixed()) {
                    float limitedDist = Math.min(maxDisplace * ((float) speed / SPEED_DIVISOR), dist);
                    n.setX((float) ((n.x() + xDist / dist * limitedDist)));
                    n.setY((float) ((n.y() + yDist / dist * limitedDist)));
                }
            }
        } finally {
            graph.readUnlockAll();
        }
    }

    @Override
    public void endAlgo() {
        graph.readLock();
        try {
            for (Node n : graph.getNodes()) {
                n.setLayoutData(null);
            }
        } finally {
            graph.readUnlockAll();
        }
    }

    @Override
    public boolean canAlgo() {
        return true;
    }

    @Override
    public LayoutProperty[] getProperties() {
        List<LayoutProperty> properties = new ArrayList<>();
        final String FRUCHTERMAN_REINGOLD = "Fruchterman Reingold";

        try {
            properties.add(LayoutProperty.createProperty(
                    this, Float.class,
                    NbBundle.getMessage(MyLayout1.class, "fruchtermanReingold.area.name"),
                    FRUCHTERMAN_REINGOLD,
                    "fruchtermanReingold.area.name",
                    NbBundle.getMessage(MyLayout1.class, "fruchtermanReingold.area.desc"),
                    "getArea", "setArea"));
            properties.add(LayoutProperty.createProperty(
                    this, Double.class,
                    NbBundle.getMessage(MyLayout1.class, "fruchtermanReingold.gravity.name"),
                    FRUCHTERMAN_REINGOLD,
                    "fruchtermanReingold.gravity.name",
                    NbBundle.getMessage(MyLayout1.class, "fruchtermanReingold.gravity.desc"),
                    "getGravity", "setGravity"));
            properties.add(LayoutProperty.createProperty(
                    this, Double.class,
                    NbBundle.getMessage(MyLayout1.class, "fruchtermanReingold.speed.name"),
                    FRUCHTERMAN_REINGOLD,
                    "fruchtermanReingold.speed.name",
                    NbBundle.getMessage(MyLayout1.class, "fruchtermanReingold.speed.desc"),
                    "getSpeed", "setSpeed"));
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }

        return properties.toArray(new LayoutProperty[0]);
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    /**
     * @return the gravity
     */
    public Double getGravity() {
        return gravity;
    }

    /**
     * @param gravity the gravity to set
     */
    public void setGravity(Double gravity) {
        this.gravity = gravity;
    }

    /**
     * @return the speed
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}
