package Interface;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Window extends Application implements Runnable {

    private final int WIDTH = 1366;
    private final int HEIGHT = 720;
    private Pane pane;
    private Scene scene;
    private Canvas canvas;
    private Thread thread;

    //se establece el punto inicial del cuadrado
    int squareX0 = randomCoordX();
    int squareY0 = randomCoordY();

    int circleX0 = randomCoordX();
    int circleY0 = randomCoordY();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Random Graphics");
        initComponents(primaryStage);
        primaryStage.show();
    }

    private void initComponents(Stage primaryStage) {
        this.pane = new Pane();
        this.scene = new Scene(this.pane, WIDTH, HEIGHT);
        this.canvas = new Canvas(WIDTH, HEIGHT);

        this.thread = new Thread(this);
        this.thread.start();

        this.pane.getChildren().add(this.canvas);
        primaryStage.setScene(this.scene);
        primaryStage.setOnCloseRequest(exit);
    }

    private void myDraw(GraphicsContext gc) {
        while (true) {
            try {
                draw(gc);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public void run() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        myDraw(gc);

    }

    EventHandler<WindowEvent> exit = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            System.exit(0);
        }
    };

    private void draw(GraphicsContext gc) throws InterruptedException {
        //punto destino aleatorio
        int squareX1 = randomCoordX();
        int squareY1 = randomCoordY();
        
        int circleX1 = randomCoordX();
        int circleY1 = randomCoordY();

        //se dibuja el cuadrado
        figures(gc, squareX0, squareY0, squareX1, squareY1, circleX0, circleY0, circleX1, circleY1);


    } // draw

    //metodo que dibuja el cuadrado
    private void figures(GraphicsContext gc, double sqX0, double sqY0, double sqX1, double sqY1, double circX0, double circY0, double circX1, double circY1) throws InterruptedException {
        //calcula el siguiente punto para el cuadrado
        double[] targetSquare = targetPoint(sqX0, sqY0, sqX1, sqY1);
        //calcula el siguiente punto para el circulo
        double[] targetCircle = targetPoint(circX0, circY0, circX1, circY1);
        
        //se encarga de dibujar las figuras siguiendo una trayectoria
        for (;;) {
            //se dibuja el cuadrado
            gc.fillRect(targetSquare[0], targetSquare[1], 80, 80);
            targetSquare = targetPoint(targetSquare[0], targetSquare[1], sqX1, sqY1);
            
            //se dibuja el circulo
            gc.fillOval(targetCircle[0], targetCircle[1], 80, 80);
            targetCircle = targetPoint(targetCircle[0], targetCircle[1], circX1, circY1);
            //pausa del hilo y limpieza de la pantalla
            Thread.sleep(5);
            gc.clearRect(0, 0, WIDTH, HEIGHT);
            
            //este if valida la condicion de parada del for, que es cuando una figura llega a su punto objetivo
            if((targetSquare[0]==sqX1 && targetSquare[1]==sqY1) || (targetCircle[0]==circX1 && targetCircle[1]==circY1)){
                //si el for se detiene, asigna el punto en el que la figura quedo como nuevo punto de inicio
                circleX0=(int)targetCircle[0];
                circleY0=(int)targetCircle[1];
                squareX0=(int)targetSquare[0];
                squareY0=(int)targetSquare[1];
                break;
            }
        }
    }//square

    //Metodos random
    public int randomCoordX() {
        int x = (int) (Math.random() * (1286 - 0 + 1) + 0);
        return x;
    }//randomCoordX

    public int randomCoordY() {
        int y = (int) (Math.random() * (660 - 0 + 1) + 0);
        return y;
    }//randomCoordY

    public double[] targetPoint(double x0, double y0, double x1, double y1) {
        //variables para abreviar la formula de la recta
        double dx = x1 - x0;
        double dy = y1 - y0;

        double[] point = new double[2];

        //en este if se define en que direccion se va a dibujar
        if (Math.abs(dx) > Math.abs(dy)) {
            double m = dy / dx;
            double b = y0 - m * x0;
            if (dx < 0) {
                dx = -1;
            } else {
                dx = 1;
            }

            if (x0 != x1) {
                x0 += dx;
                y0 = Math.round(m * x0 + b);
                point[0] = x0;
                point[1] = y0;
                return point;
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

                if (y0 != y1) {
                    y0 += dy;
                    x0 = Math.round(m * y0 + b);
                    point[0] = x0;
                    point[1] = y0;
                    return point;
                }
            }
        }
        return null;
    }
}
