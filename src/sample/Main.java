package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

//import javax.management.timer.Timer;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    private double WORLD_WIDTH = 600;
    private double WORLD_HEIGHT = 600;

    Timer zapperTimer = new Timer();
    Missile missile;
    Barry barry;
    Zapper zapper;
    int tempDy = 0;

    @Override
    public void start(Stage stage) throws Exception {

        World world = new World() {
            @Override
            public void act(long now) {

            }
        };

        stage.setTitle("P1 Group 8 Game Engine");

        Scene scene = new Scene(world, WORLD_WIDTH, WORLD_HEIGHT);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        WORLD_WIDTH = primaryScreenBounds.getWidth();
        WORLD_HEIGHT = primaryScreenBounds.getHeight();

        barry = new Barry();
        zapper = new Zapper();
        missile = new Missile();
        barry.setLocation(80, WORLD_HEIGHT - 82);
        zapper.setLocation(WORLD_WIDTH + 50, WORLD_HEIGHT + 50);
        missile.setLocation(WORLD_WIDTH + 50, WORLD_HEIGHT + 50);


        barry.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.W) {
                    barry.setDy(-(5 + tempDy));
                    tempDy += 2;
                    barry.setDx(0);
//                } else if (e.getCode() == KeyCode.A) {
//                    barry.setDy(0);
//                    barry.setDx(-5);
//                } else if (e.getCode() == KeyCode.D) {
//                    barry.setDy(0);
//                    barry.setDx(5);
                } else if (e.getCode() == KeyCode.S) {
                    barry.setDy(5);
                    barry.setDx(0);
                }
            }
        });

        barry.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                barry.setDx(0);
                barry.setDy(7);
                tempDy = 0;
            }
        });

        barry.setImage(new Image("file:img/barry.png"));
        zapper.setImage(new Image("file:img/zapper.png"));
        missile.setImage(new Image("file:img/missile.png"));

        world.add(barry);
        world.add(zapper);
        world.add(missile);


        Timeline zapperTimeline = new Timeline(new KeyFrame(
                Duration.millis(2000),
                ae -> shootZapper()));
        zapperTimeline.setCycleCount(Animation.INDEFINITE);
        zapperTimeline.play();

        Timeline missileTimeline = new Timeline(new KeyFrame(
                Duration.millis(3000),
                ae -> shootMissile()));
        missileTimeline.setCycleCount(Animation.INDEFINITE);
        missileTimeline.play();
//
//        timeline.stop();

        world.start();

        stage.setScene(scene);

        stage.show();
    }

    public void shootMissile(){
        int randomY = (int) (Math.random() * WORLD_HEIGHT);
        missile.setLocation(WORLD_WIDTH, randomY);
        missile.setDx(-20);
    }

    public void shootZapper(){
        int randomY = (int) (Math.random() * WORLD_HEIGHT);
        zapper.setLocation(WORLD_WIDTH, randomY);
        zapper.setDx(-15);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
