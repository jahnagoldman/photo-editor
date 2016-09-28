# Photo Editor
This is a photo editing application in Java using JavaFX and FXML that I created in my Advanced Java Programming course.
The user may upload a photo and then make edits to this photo as they wish. 

## About
- The application includes a toolbar featuring several image effects as follows:
  - Saturate
  - Desaturate
  - Lighten
  - Darken 
  - Color Inversion
  - Grayscale
  - Hue Shift
  - Sepia Tone.
These are usable by selecting the effect you wish to use, clicking the "Apply Effect" button, 
and clicking and dragging the mouse over the area you would like to change (i.e. a square).

* Undo/Redo Buttons: All actions can be undone/redone up to 100 times, except for Sepia Tone, which can be switched back and forth by pressing the Sepia Tone button.

* Pen Tool - circle or square shape, color selected with the color picker
* Color Dropper Tool - changes the color picker to match the color of wherever you click 
* Bucket Tool - colors an individual pixel the color chosen in the color picker
 
 
 (Most of the buttons/sliders have a tool-tip explaining what they are, as above.)
 

## Other Notes
- I used Apache Maven as a build tool for this project.
- I utilized Java 8 code in the form of lambda expressions, etc. in the application.
- The Command Center (CC) class is a singleton, to manage the single instance of the application.



