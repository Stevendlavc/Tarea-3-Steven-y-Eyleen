/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Eyleen
 * @author Steven
 */
public class LineExample extends JPanel {

    public LineExample() {
        this.setPreferredSize(new Dimension(1366, 720));
    } // constructor

    private void draw(Graphics g) {
        // dibujar los ejes del plano cartesiano
        g.setColor(Color.red);

        g.setColor(Color.black);
        linearFunction(g, 500, 10, 500, 500);
        //linearFunction(g, 0, 0, 100, 600);

    } // draw

    // funcion lineal f(x) = m x + b
    // x1 y x2 es el rango en el que se graficara la funcion
    private void linearFunction(Graphics g, double x0, double y0, double x1, double y1) {
        //abriviaciones para las funciones
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
        JFrame window = new JFrame("Graphing Function");
        window.setContentPane(new LineExample());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.pack();
        window.setResizable(false);
        window.setLocation(150, 100);
        window.setVisible(true);
    }

} // fin clase

