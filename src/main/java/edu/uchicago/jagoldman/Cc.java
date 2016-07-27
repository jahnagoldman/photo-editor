package edu.uchicago.jagoldman;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public class Cc {

    private static Cc stateManager;




    private Stage mMainStage, mSaturationStage;
    private ImageView imgView; // Value injected by FXMLLoader
    private Image img, imgUndo;
    private int mPointTemp;
    private boolean bFirstUndo = true;
    boolean bSepia = false;
    public static final int MAX_UNDOS = 100;

    private static ArrayList<Image> backImages;



    /**
     * Create private constructor
     */
    private Cc(){

    }
    /**
     * Create a static method to get instance.
     */
    public static Cc getInstance(){
        if(stateManager == null){
            stateManager = new Cc();
            backImages = new ArrayList<>();
        }
        return stateManager;
    }




    //from Horstmann
    public static Image transform(Image in, UnaryOperator<Color> f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(in.getPixelReader().getColor(x, y)));
        return out;
    }

    public static <T> Image transform(Image in, BiFunction<Color, T, Color> f, T arg) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(in.getPixelReader().getColor(x, y), arg));
        return out;
    }

    public static Image transform(Image in, ColorTransformer f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(x, y, in.getPixelReader().getColor(x, y)));
        return out;
    }



    public Stage getMainStage() {
        return mMainStage;
    }

    public void setMainStage(Stage mMainStage) {
        this.mMainStage = mMainStage;
    }

    public Stage getSaturationStage() {
        return mSaturationStage;
    }

    public void setSaturationStage(Stage mSaturationStage) {
        this.mSaturationStage = mSaturationStage;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
        bFirstUndo = true;

    }


    public void undo(){

        if (bFirstUndo) {
            mPointTemp = backImages.size() - 1;
        }
        if (mPointTemp >= 0) {
            bFirstUndo = false;
            mPointTemp--;
            this.img = backImages.get(mPointTemp);
        }
        imgView.setImage(img);


    }


    public void redo(){
        if (mPointTemp < backImages.size()-1) {
            mPointTemp++;
            this.img = backImages.get(mPointTemp);
            imgView.setImage(img);
        }


   }
   public void reOpenLast() {
       if (img != null) {
           imgView.setImage(img);
           bFirstUndo = true;
       }
   }



    public void setImageAndRefreshView(Image img){
        addBackImages(img);
        System.out.println(img);

        this.img = img;
        imgView.setImage(img);
        bFirstUndo = true;


    }
    public void addBackImages(Image imagePrev) {
        if (backImages.size() >= MAX_UNDOS) {
            backImages.remove(0);
            backImages.add(imagePrev);

        }
        else {
            backImages.add(imagePrev);
        }
    }




    public void close(){

        imgView.setImage(null);
    }
}
