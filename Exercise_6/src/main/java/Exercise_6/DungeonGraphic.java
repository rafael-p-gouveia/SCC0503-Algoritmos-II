/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise_6;

/**
 *
 * @author Dell
 */
import javax.swing.*;
import java.awt.*;

public class DungeonGraphic extends JFrame
{
    Graph graph;
    public DungeonGraphic(Graph graph)
    {
        super("Dungeon");
        this.graph = graph;
        getContentPane().setBackground(Color.black);
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.repaint();
    }

    private void drawDungeon(Graphics g)
    {
        var graphics2D = (Graphics2D) g;
        if(graph != null)
        {
            for (var i = 0; i < graph.getNumberOfVertices(); i++)
            {
                graphics2D.setColor(Color.BLUE);
                var currentVertex = graph.getVertices().get(i);
                graphics2D.draw(((Room)currentVertex).getRectangle());
                if(((Room)currentVertex).isCheckpoint())
                {
                    graphics2D.setColor(Color.YELLOW);
                    graphics2D.fill(((Room) currentVertex).getRectangle());
                }
                else if(((Room)currentVertex).isExit())
                {
                    graphics2D.setColor(Color.RED);
                    graphics2D.fill(((Room) currentVertex).getRectangle());
                }
                else if(((Room)currentVertex).isEntrance())
                {
                    graphics2D.setColor(Color.GREEN);
                    graphics2D.fill(((Room) currentVertex).getRectangle());
                }
                
                if(((Room)currentVertex).isKey()){
                    graphics2D.setColor(Color.MAGENTA);
                    graphics2D.fill(new Rectangle(((Room)currentVertex).getRectangle().getLocation(),new Dimension(10,10)));
                }
                else if(((Room)currentVertex).isLock()){
                    graphics2D.setColor(Color.LIGHT_GRAY);
                    graphics2D.fill(new Rectangle(((Room)currentVertex).getRectangle().getLocation(),new Dimension(10,10)));
                }
                g.setColor(Color.PINK);
                var adjacentVertex = graph.getFirstConnectedVertex(currentVertex);
                while(adjacentVertex != null)
                {
                    g.drawLine((int)((Room) currentVertex).getPoint().getX(), (int)((Room) currentVertex).getPoint().getY(),
                            (int)((Room) adjacentVertex).getPoint().getX(),(int) ((Room) adjacentVertex).getPoint().getY());
                    adjacentVertex = graph.getNextConnectedVertex(currentVertex, adjacentVertex);
                }
            }
        }
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        drawDungeon(g);
    }
}
