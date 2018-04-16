/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author Steven
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Eyleen
 * @author Steven
 */
public class RandomLines extends JPanel {

    public RandomLines() {
        this.setPreferredSize(new Dimension(1366, 720));
    } // constructor

    private void draw(Graphics g) {
        int green = 0;
        for (int i = 0; i <= 1000; i++) {
            if (green == 255) {
                green = 0;
            }
            g.setColor(new Color(0, green++, 0));
            int x0 = (int) randomCoordX();
            int y0 = (int) randomCoordY();
            int x1 = (int) randomCoordX();
            int y1 = (int) randomCoordY();
            lines(g, x0, y0, x1, y1);
        }
    } // draw

    private void lines(Graphics g, double x0, double y0, double x1, double y1) {
        double dx = x1 - x0;
        double dy = y1 - y0;

        if (Math.abs(dx) > Math.abs(dy)) {
            double m = dy / dx;
            double b = y0 - m * x0;
            if (dx < 0) {
                dx = -1;
            } else {
                dx = 1;
            }

            while (x0 != x1) {
                x0 += dx;
                y0 = Math.round(m * x0 + b);
                g.drawLine((int) coord_x(x0), (int) coord_y(y0), (int) coord_x(x0), (int) coord_y(y0));
            }
        } else {
            if (dy != 0) {
                double m = dx / dy;
                double b = x0 - m * y0;
                if (dy < 0) {
                    dy = -1;
                } else {
                    dy = 1;
                }
                while (y0 != y1) {
                    y0 += dy;
                    x0 = Math.round(m * y0 + b);
                    g.drawLine((int) coord_x(x0), (int) coord_y(y0), (int) coord_x(x0), (int) coord_y(y0));
                }
            }
        }
    }// linearFunction

    //Metodos random
    public double randomCoordX() {
        double x = Math.random() * 1366;
        return x;
    }//randomCoordX

    public double randomCoordY() {
        double y = Math.random() * 720;
        return y;
    }//randomCoordY

    
    
    private double coord_x(double x) {
        return x;
    }

    private double coord_y(double y) {
        double real_y = (double) this.getHeight() - y;
        return real_y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // se llama al meto draw
        draw(g);

    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Random Lines");
        window.setContentPane(new RandomLines());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.pack();
        window.setResizable(false);
        window.setLocation(150, 100);
        window.setVisible(true);
    }
}