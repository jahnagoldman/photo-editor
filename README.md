# photo-editor
This is a photo editing application in Java using JavaFX and FXML that I created in my Advanced Java Programming course.
It's meant for the user to upload any photo they wish to then make edits. It features multiple image effects including saturate, desaturate, lighten, darken, 
color inversion, grayscale, hue shift, and sepia tone. They are usable by selecting the effect you wish to use, clicking the "Apply Effect" button, 
and then clicking and dragging the mouse over the area you would like to change (i.e. a square). All can be undone/redone by using the undo/redo
buttons up to 100 times, except sepia tone, which can switch from sepia to regular by using the same button. There is a pen tool that can
 be used in circle or square shape and color can be selected. There is also a color dropper tool that will change the color selector based on where
 you click on the image to match that color. There is also a bucket tool, which colors an individual pixel whatever color is chosen in the color selector. 
 Most of the buttons/sliders have a tool-tip explaining what they are, as above.
 
 I used Apache Maven as a build tool for this project, and also utilized some Java 8 in the form of lambda expressions. The Command Center (CC) class
 is a singleton, to manage the single instance of the application.



