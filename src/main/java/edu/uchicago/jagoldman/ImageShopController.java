package edu.uchicago.jagoldman;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SampleGeneric Skeleton for 'imageshop.fxml' Controller Class
 */


public class ImageShopController implements Initializable {

    //private Desktop desktop = Desktop.getDesktop();
    private ToggleGroup mToggleGroup = new ToggleGroup();


    public enum Pen {
        CIR, SQR, FIL, DROP, BUCK;
    }

    public enum FilterStyle {
        SAT, DRK, LTN, GRY, INV, SEP, DSAT, HUS, OTHER;
    }



    private int penSize = 50;
    private Pen penStyle = Pen.CIR;
    private FilterStyle mFilterStyle = FilterStyle.DRK;
    private int sliderColorNumber;

    @FXML // fx:id="imgView"
    private ImageView imgView; // Value injected by FXMLLoader

//    @FXML // ResourceBundle that was given to the FXMLLoader
//    private ResourceBundle resources;

    // for mouse clicks
    private double xPos, yPos, hPos, wPos;

    private Color mColor = Color.WHITE;
    private boolean bSepia = false;

    ArrayList<Shape> removeShapes = new ArrayList<>(1000);


    //http://java-buddy.blogspot.com/2013/01/use-javafx-filechooser-to-open-image.html
    @FXML
    void mnuOpenAction(ActionEvent event) {

        Cc.getInstance().setImgView(this.imgView);


        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        //openFile(file);


        try {
            BufferedImage bufferedImage = ImageIO.read(file);


            // imgView.setImage(Cc.getInstance().getImg());

            Cc.getInstance().setImg(SwingFXUtils.toFXImage(bufferedImage, null));
            Cc.getInstance().setImageAndRefreshView(Cc.getInstance().getImg());

        } catch (IOException ex) {
            Logger.getLogger(ImageShopController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private Slider sliderColor;


    @FXML
    private ComboBox<String> cboSome;

    @FXML
    private ToggleButton tgbSquare;


    @FXML
    private ToggleButton tgbCircle;


    @FXML
    private ColorPicker cpkColor;



    @FXML
    private Slider sldSize;

    @FXML
    private ToggleButton tgbFilter;

    @FXML
    void mnuReOpenLast(ActionEvent event) {

        Cc.getInstance().reOpenLast();
    }

    @FXML
    private AnchorPane ancRoot;


    @FXML
    void mnuSaveAction(ActionEvent event) {
        Cc.getInstance().setImgView(this.imgView);
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {




                ImageIO.write(SwingFXUtils.fromFXImage(Cc.getInstance().getImg(),
                        null), "png", file);


            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }



    @FXML
    void mnuSaveAsAction(ActionEvent event) {
        mnuSaveAction(event);


    }

    @FXML
    void mnuQuitAction(ActionEvent event) {


        System.exit(0);


    }

    @FXML
    void mnuCloseAction(ActionEvent event) {
        Cc.getInstance().close();
    }

    @FXML
    void mnuGrayscale(ActionEvent event) {


        if (Cc.getInstance().getImg() == null)
            return;

        //make sure that we set the image view first, so we can roll back and do other operations to it.
        Cc.getInstance().setImgView(this.imgView);

        Image greyImage = Cc.getInstance().transform(Cc.getInstance().getImg(), Color::grayscale);
        Cc.getInstance().setImageAndRefreshView(greyImage);


    }

    @FXML
    void mnuSaturate(ActionEvent event) {


        Cc.getInstance().setImgView(this.imgView);


        Stage dialogStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/saturation.fxml"));
            Scene scene = new Scene(root);
            dialogStage.setTitle("Saturation");
            dialogStage.setScene(scene);
            //set the stage so that I can close it later.
            Cc.getInstance().setSaturationStage(dialogStage);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    @FXML
    void mnuUndo(ActionEvent event) {

        Cc.getInstance().undo();

    }
    @FXML
    private Button undoButton;
    @FXML
    private Button redoButton;


    @FXML
    void mnuRedo(ActionEvent event) {
        Cc.getInstance().redo();
    }

    @FXML
    private Button saturateButton;
    @FXML
    private Button desaturateButton;
    @FXML
    private Button darkenButton;
    @FXML
    private Button lightenButton;
    @FXML
    private Button invertButton;
    @FXML
    private Button grayButton;
    @FXML
    private Button hueButton;
    @FXML
    private Button sepiaButton;
    @FXML
    private ToggleButton bucketButton;
    @FXML
    private ToggleButton dropperButton;




    //##################################################################
    //INITIALIZE METHOD
    //see: http://docs.oracle.com/javafx/2/ui_controls/jfxpub-ui_controls.htm
    //##################################################################
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //mColor = Color.WHITE;
        Cc.getInstance().setImgView(this.imgView);
        tgbCircle.setToggleGroup(mToggleGroup);
        tgbSquare.setToggleGroup(mToggleGroup);
        tgbFilter.setToggleGroup(mToggleGroup);
        dropperButton.setToggleGroup(mToggleGroup);
        bucketButton.setToggleGroup(mToggleGroup);
        tgbCircle.setSelected(true);
//        cboSome.setValue("Darker");
        undoButton.setOnAction(event -> Cc.getInstance().undo());
        redoButton.setOnAction(event -> Cc.getInstance().redo());


        mToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == tgbCircle) {
                    penStyle = Pen.CIR;
                    System.out.println("Initialize: resources = "+resources );
                } else if (newValue == tgbSquare) {
                    penStyle = Pen.SQR;
                } else if (newValue == tgbFilter) {
                    penStyle = Pen.FIL;
                }
                else if (newValue == dropperButton) {
                    penStyle = Pen.DROP;
                }
                else if (newValue == bucketButton) {
                    penStyle = Pen.BUCK;
                }
                else {
                    penStyle = Pen.CIR;
                }
            }
        });


        imgView.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (penStyle == Pen.FIL || penStyle == Pen.BUCK || penStyle == Pen.DROP){
                    xPos = (int) me.getX();
                    yPos = (int) me.getY();
                }

                me.consume();
            }
        });


        imgView.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {

                if (penStyle == Pen.CIR || penStyle == Pen.SQR) {

                    System.out.println("mouse pressed! " + me.getSource());
                    SnapshotParameters snapshotParameters = new SnapshotParameters();
                    snapshotParameters.setViewport(new Rectangle2D(0, 0, imgView.getFitWidth(), imgView.getFitHeight()));
                    Image snapshot = ancRoot.snapshot(snapshotParameters, null);
                    Cc.getInstance().setImageAndRefreshView(snapshot);
                    ancRoot.getChildren().removeAll(removeShapes);
                    removeShapes.clear();

                } else if (penStyle == Pen.FIL){


                    wPos =  (int) me.getX() ;
                    hPos = (int) me.getY() ;

                    //default value
                   Image transformImage;

                    switch (mFilterStyle){
                        case DRK:
                            //make darker
                            transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                                    (x, y, c) -> (x > xPos && x < wPos)
                                            && (y > yPos && y < hPos) ?  c.deriveColor(0, 1, .5, 1): c
                            );
                            break;

                        case SAT:

                            //saturate
                            transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                                    (x, y, c) -> (x > xPos && x < wPos)
                                            && (y > yPos && y < hPos) ?  c.deriveColor(0, 1.0 / .1, 1.0, 1.0): c


                            );
                            break;
                        case LTN:
                            transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                                    (x, y, c) -> (x > xPos && x < wPos)
                                        && (y > yPos && y < hPos) ? c.
                                            deriveColor(0, 1, 2, 1): c
                            );


                            break;
                        case GRY:
                            transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),

                                    (x, y, c) -> (x > xPos && x < wPos)
                                            && (y > yPos && y < hPos) ? c.grayscale() : c
                            );

                            break;
                        case INV:

                            //saturate
                            transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                                    (x, y, c) -> (x > xPos && x < wPos)
                                            && (y > yPos && y < hPos) ?  c.invert(): c


                            );


                            break;
                        case SEP:
                            if (bSepia == false) {
                            bSepia = true;
                            SepiaTone sepiaTone = new SepiaTone(.7);
                            Cc.getInstance().getImgView().setEffect(sepiaTone);}
                            else {
                                Cc.getInstance().getImgView().setEffect(null);
                                bSepia = false;
                            }
                            transformImage = Cc.getInstance().getImg();


                            break;
                        case DSAT:
                            transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                                    (x, y, c) -> (x > xPos && x < wPos)
                                            && (y > yPos && y < hPos) ?  c.deriveColor(0, .75, 1.0, 1.0): c


                            );
                            break;
                        case HUS:
                            transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                                    (x, y, c) -> (x > xPos && x < wPos)
                                            && (y > yPos && y < hPos) ?  c.deriveColor(sliderColorNumber, 1, 1, 1) : c


                            );
                            break;


                        default:
                            //make darker
                            //make darker
                            transformImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                                    (x, y, c) -> (x > xPos && x < wPos)
                                            && (y > yPos && y < hPos) ?  c.deriveColor(0, 1, .5, 1): c
                            );
                            break;

                    }



                    Cc.getInstance().setImageAndRefreshView(transformImage);
                }
                else if (penStyle == Pen.DROP) {
                    xPos = me.getX();
                    yPos = me.getY();
                    Color pixelColor;
                    PixelReader pixelReader = Cc.getInstance().getImg().getPixelReader();
                    pixelColor = pixelReader.getColor(((int) xPos),((int) yPos));

                    cpkColor.setValue(pixelColor);
                    mColor = cpkColor.getValue();

                }
                else if (penStyle == Pen.BUCK) {
                    xPos = ((int)me.getX());
                    yPos = ((int)me.getY());
                    Image transformedImage;
                    transformedImage = Cc.getInstance().transform(Cc.getInstance().getImg(),
                            (x, y, c) -> (x == xPos && y == yPos) ?  mColor : c);

                    Cc.getInstance().setImageAndRefreshView(transformedImage);


                }


                else {
                    //do nothing right now

                }
                me.consume();
            }
        });


        imgView.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {

                if (penStyle == Pen.FIL || penStyle == Pen.BUCK || penStyle == Pen.DROP){
                    me.consume();
                    return;
                }

                // Line line = new Line(xPos, yPos, me.getX(), me.getY());

                xPos = me.getX();
                yPos = me.getY();

                int nShape = 0;
                //default value
                Shape shape = new Circle(xPos, yPos, 10);
                switch (penStyle) {
                    case CIR:
                        shape = new Circle(xPos, yPos, penSize);
                        break;
                    case SQR:
                        shape = new Rectangle(xPos, yPos, penSize, penSize);
                        break;


                    default:
                        shape = new Circle(xPos, yPos, penSize);
                        break;

                }

               // shape.setStroke(mColor);
                shape.setFill(mColor);

                ancRoot.getChildren().add(shape);
                removeShapes.add(shape);
                me.consume();

                //   Node shapeRemove =  ancRoot.getScene().lookup("789");
                //  ancRoot.getChildren().remove(shapeRemove);


            }
        });


        cpkColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mColor = cpkColor.getValue();
            }
        });

        sldSize.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double temp  = (Double) newValue; //automatic unboxing
                penSize = (int) Math.round(temp);
            }
        });
        sliderColor.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable2, Number oldValue2, Number newValue2) {
                double temp2 = (Double) newValue2;
                sliderColorNumber = (int) Math.round(temp2);
            }
        });


        saturateButton.setOnAction(event -> mFilterStyle = FilterStyle.SAT);
        desaturateButton.setOnAction(event -> mFilterStyle = FilterStyle.DSAT);
        lightenButton.setOnAction(event -> mFilterStyle = FilterStyle.LTN);
        darkenButton.setOnAction(event -> mFilterStyle = FilterStyle.DRK);
        invertButton.setOnAction(event -> mFilterStyle = FilterStyle.INV);
        grayButton.setOnAction(event -> mFilterStyle = FilterStyle.GRY);
        hueButton.setOnAction(event -> mFilterStyle = FilterStyle.HUS);
        sepiaButton.setOnAction(event -> mFilterStyle = FilterStyle.SEP);




    }//END INIT


    //invert

//    Cc.getInstance().setSaturationLevel((int)sldSaturation.getValue());
//
//
//    int nLevel = Cc.getInstance().getSaturationLevel();
//    double dLevel = (100-nLevel)/100;
//
//    //saturation value
//    Image image = Cc.transform(Cc.getInstance().getImg(), (Color c, Double d) -> c.deriveColor(0, 1.0/ d, 1.0, 1.0), dLevel);
//    Cc.getInstance().getImgView().setImage(image);
//
//    Cc.getInstance().getSaturationStage().close();




}
