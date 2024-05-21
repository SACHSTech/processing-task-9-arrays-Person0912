import processing.core.PApplet;

public class Sketch extends PApplet {
	
  // Variable to set the number of snowflakes that will initially appear on the screen
  int intNumSnowFlakes = 25;

  // Empty arrays to store the x and y values of the snowflakes
	float[] fltSnowX = new float[intNumSnowFlakes];
  float[] fltSnowY = new float[intNumSnowFlakes];

  // Variable to control the speed of the snowflakes and player
  float fltSpd;

  // Variables to control the movement of the player
  float fltCircX;
  float fltCircY;

  // Boolean values to control the movement of the player
  boolean boolW = false;
  boolean boolS = false;
  boolean boolA = false;
  boolean boolD = false;

  // Boolean to check whether the player currently collides with a snowflake
  boolean boolCurCollided = false;

  // Variable to save the index of the snowflake the player collides with
  int intSavedColIndex;

  // Variable to count the number of lives the player has
  int intLives = 0;
  
  // Arrays to store the red and green values to colour the player's three lives
  int[] intRed = {0, 0, 0};
  int[] intGreen = {255, 255, 255};

  // Array to store whether or not a snowflake is hidden
  int[] intSnowHideStatus = new int[intNumSnowFlakes];
  
  // Variable to save the index of the snowflake the player's mouse hovers over
  int intSavedSnowHideStatIndex;

  // Boolean to check whether the mouse cursor is within a snowflake or not
  boolean boolCurOnSnow = false;

  /**
   * Determines the size of the window and positions the player circle in the centre of the window
   */
  public void settings() {
    
    // Window size
    size(600, 600);
    
    // Set the starting coordinates of the player circle to the centre of the screen
    fltCircX = width / 2;
    fltCircY = height / 2;
  }

  /**
   * Removes strokes and randomizes the starting coordinates for each snowflake
   */
  public void setup() {
    
    // Remove strokes
    noStroke();

    // Randomize the intial x and y coordinates for each snowflake
    for (int i = 0; i < fltSnowX.length; i ++) {
      
      fltSnowX[i] = random(0, width);
      fltSnowY[i] = random(0, height);
      }
    }
  
  /**
   * Draws everything onto the screen
   */
  public void draw() {
	  
    // Draw the background to ensure the ellipses do not leave a trail
    background(144, 240, 252);

    // Adjusts the speed of the snowflakes and player when the up and down arrow keys are pressed
    if (keyPressed) {

      // Increases the speed when the player presses the down arrow key
      if (keyCode == DOWN) {
        
        fltSpd = 5;
      }

      // Decreases the speed when the player presses the up arrow key
      else if (keyCode == UP) {
        
        fltSpd = 1;
      }
    }

    // Resets the speed of the snowfall to it's default when no keys are pressed
    else if (!keyPressed) {
      fltSpd = 3;
    }

    // Set the colour of the snowflakes to white
    fill(255, 255, 255);

    // For loop that goes through the indexes in all of the arrays
    for (int j = 0; j < fltSnowX.length; j ++) {
      
      // Draws the snowflake at their position as long as they have not been clicked on
      if (intSnowHideStatus[j] != -1) {
        
        ellipse(fltSnowX[j], fltSnowY[j], 20, 20);
      }
      
      // Changes the x and y values of the snowflakes that have been clicked on to be off screen
      else if (intSnowHideStatus[j] == -1) {
        fltSnowX[j] = -1;
        fltSnowY[j] = -1;
      }

      // Makes the snowflakes fall
      fltSnowY[j] += fltSpd;

      // Moves the snowflake back to the top of the screen when it goes off screen and assigns it a random x-value
      if (fltSnowY[j] > height) {
        
        fltSnowX[j] = random(width);
        fltSnowY[j] = 0;
      }

      // Detects whether or not the player collides with a snowflake and if the player is not currently collided with a snowflake
      if (dist(fltSnowX[j], fltSnowY[j], fltCircX, fltCircY) < 20 && boolCurCollided == false) {
        
        // Changes one of the rectangle colours to red to indicate a lost life
        intRed[intLives] = 255;
        intGreen[intLives] = 0;
        
        // Increments the number of lives
        intLives += 1;  

        // Saves the index of the snowball the player collided with
        intSavedColIndex = j;

        // Sets the player being currently collided to true
        boolCurCollided = true;
      }
      
      // Checks if the player stops being collided with the snowflake and sets current collided to false
      if (dist(fltSnowX[intSavedColIndex], fltSnowY[intSavedColIndex], fltCircX, fltCircY) > 20) {
        boolCurCollided = false;
      }

      // Checks whether or not the mouse cursor is on a snowflake
      if (dist(mouseX, mouseY, fltSnowX[j], fltSnowY[j]) < 10) {
        
        // Sets the boolean cursor on snow to true
        boolCurOnSnow = true;

        // Saves the index of the snowflake the cursor is hovering over
        intSavedSnowHideStatIndex = j;
      }
      
      // Checks if the cursor goes off the snowflake
      if (dist(mouseX, mouseY, fltSnowX[intSavedSnowHideStatIndex], fltSnowY[intSavedSnowHideStatIndex]) > 10) {
        
        // Sets the cursor on the snowflake to false
        boolCurOnSnow = false;
      }
    }
    
    // Rectangular boxes to show the player's lives
    fill(intRed[0], intGreen[0], 0);
    rect(580, 10, 10, 10);

    fill(intRed[1], intGreen[1], 0);
    rect(560, 10, 10, 10);

    fill(intRed[2], intGreen[2], 0);
    rect(540, 10, 10, 10);

    // Moves the player when the WASD keys are pressed
    if (boolW) {
      fltCircY -= fltSpd;
    }
    if (boolS) {
      fltCircY += fltSpd;
    }
    if (boolA) {
      fltCircX -= fltSpd;
    }
    if (boolD) {
      fltCircX += fltSpd;
    }
    
    // If statements to ensure the player does not move off screen
    if (fltCircY > height) {
      fltCircY = 0;
    }
    else if (fltCircY < 0) {
      fltCircY = height;
    }
    
    if (fltCircX > width) {
      fltCircX = 0;
    }
    else if (fltCircX < 0) {
      fltCircX = width;
    }

    // Draws the player circle to the screen
    fill(0, 0, 255);
    ellipse(fltCircX, fltCircY, 20, 20);

    // Makes the screen white when the player runs out of lives
    if (intLives == 3) {
      noLoop();
      background(255, 255, 255);
    }
  }

  /**
   * Checks whether the WASD keys are pressed to control the player's movements
   */
  public void keyPressed() {
    if (key == 'w') {
      boolW = true;
    }
    else if (key == 's') {
      boolS = true;
    }
    else if (key == 'a') {
      boolA = true;
    }
    else if (key == 'd') {
      boolD = true;
    }
  }
  
  /**
   * Checks whether the WASD keys are released to control the player's movements
   */
  public void keyReleased() {
    if (key == 'w') {
      boolW = false;
    }
    else if (key == 's') {
      boolS = false;
    }
    else if (key == 'a') {
      boolA = false;
    }
    else if (key == 'd') {
      boolD = false;
    }
  }

  /**
   * Checks if the mouse is pressed while the cursor is on a snowflake
   */
  public void mousePressed() {
    if (boolCurOnSnow) {

      // Sets the index of the snowflake clicked on to -1 in the intSnowHideStatus array
      intSnowHideStatus[intSavedSnowHideStatIndex] = -1;
    }
  }
}