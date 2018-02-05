package pixLab.classes;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
    
    
    
  }
  
  public void keepOnlyBlue() {
	    Pixel[][] pixels = this.getPixels2D();
	    for (Pixel[] rowArray : pixels)
	    {
	      for (Pixel pixelObj : rowArray)
	      {
	        pixelObj.setRed(0);
	        pixelObj.setGreen(0);
	      }
	    }
  }
  
  public void negate() {
	    Pixel[][] pixels = this.getPixels2D();
	    for (Pixel[] rowArray : pixels)
	    {
	      for (Pixel pixelObj : rowArray)
	      {
	        pixelObj.setRed(255 - pixelObj.getRed());
	        pixelObj.setGreen(255 - pixelObj.getGreen());
	        pixelObj.setBlue(255 - pixelObj.getBlue());
	      }
	    }
  }
  
  public void grayscale() {
	  Pixel[][] pixels = this.getPixels2D();
	    for (Pixel[] rowArray : pixels)
	    {
	      for (Pixel pixelObj : rowArray)
	      {
	    	  	int average = (int) ((pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen()) / 3);
	        pixelObj.setRed(average);
	        pixelObj.setGreen(average);
	        pixelObj.setBlue(average);
	      }
	    }
  }
  
  public void fixUnderwater() {
	  Pixel[][] pixels = this.getPixels2D();
	    for (Pixel[] rowArray : pixels)
	    {
	      for (Pixel pixelObj : rowArray)
	      {
	    	  	if(pixelObj.getBlue() > 150 && pixelObj.getRed() < 21)
	        pixelObj.setBlue(255);
	      }
	    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /**filter I made*/
  public void filterIMade() {
	  Pixel[][] pixels = this.getPixels2D();
	  for(int rowChunk = 0; rowChunk < pixels.length/16; rowChunk++) {
		  for (int colChunk = 0; colChunk < pixels[0].length/16; colChunk++) {
			  int colorChannel = (int) (Math.random() * 3);
			  
			  for(int row = rowChunk * 16; row < (rowChunk+1)*16; row++) {
				  for(int col = colChunk * 16; col < (colChunk+1)*16; col++) {
					  if(colorChannel == 0) {
						 pixels[row][col].setRed(0); 
					  }else if(colorChannel == 1) {
						  pixels[row][col].setGreen(0); 
					  }else if(colorChannel == 2) {
						  pixels[row][col].setBlue(0); 
					  }
				  }
			  }
		  }
	  }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }
  
  public void coolFilter(int startRow, int startCol) {
	  Pixel fromPixel = null;
	  Pixel toPixel = null;
	  Picture cool = new Picture("cool.png");
	  
	  Pixel[][] toPixels = this.getPixels2D(); // The base layer of the picture
	  Pixel[][] fromPixels = cool.getPixels2D(); //The layer we are adding to the picture.
	  
	  int fromRow = 0;
	  
	  for(int toRow = startRow; fromRow < fromPixels.length && toRow < toPixels.length; toRow++) {
		  int fromCol = 0;
		  
		  for(int toCol = startCol; fromCol < fromPixels[0].length && toCol < toPixels[0].length; toCol++) {
			  fromPixel = fromPixels[fromRow][fromCol];
			  toPixel = toPixels[toRow][toCol];
			  if(!fromPixel.isTransparent()) {
				  toPixel.setColor(fromPixel.getColor());
			  }
			  fromCol ++;
		  }
		  
		  fromRow++;
	  }
	  
  }
  
  
  int randomWithRange(int min, int max)
  {
     int range = (max - min) + 1;     
     return (int)(Math.random() * range) + min;
  }
  

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  public void glitchFilter() {
	  Pixel[][] picturePixels = this.getPixels2D();
	  Color[][] originalPixels = new Color[picturePixels.length][picturePixels[0].length];
	  for(int row = 0; row < picturePixels.length; row++) {
		  for(int col = 0; col < picturePixels[0].length; col++) {
			  originalPixels[row][col] = picturePixels[row][col].getColor();
			  
		  }
	  }
	  //Wrap percentage
	  int percentageWrap = (int) randomWithRange(20,40);
	  
	  //What column it starts with.
	  double pixelLength =  (picturePixels[0].length * (percentageWrap/100.0));
	  int startWrapPixel = (int) (picturePixels[0].length - pixelLength);
	  int endWrapPixel = picturePixels[0].length;
	  
	  for(int row = 0; row < picturePixels.length; row++) {
		  int regularCol = 0;
		  for(int col = 0; col < picturePixels[0].length; col++) {
			  
			  
			  Color normalColor = originalPixels[row][col];
			  
			  int colShift = picturePixels[0].length - startWrapPixel + col;
			  if(colShift < picturePixels[0].length - 1) picturePixels[row][colShift].setColor(normalColor);
			  
			  
			  //Move end of shift to shift
			  if(!(startWrapPixel+col > picturePixels[0].length-1)) {
				  Color endColor = originalPixels[row][startWrapPixel+col];
				  
				  picturePixels[row][col].setColor(endColor);
				  regularCol++;
			  }
			  
			  
			  
		  }
	  }
	  
	  int chunkSize = 10;
	  int chunkAmount = picturePixels.length/chunkSize * picturePixels[0].length/chunkSize;
	  
	  int[] chunkChange = new int[80];
	  for(int i = 0; i < chunkChange.length; i++) {
		  chunkChange[i] = (int) (Math.random() * chunkAmount);
	  }
	  
	  for(int rowChunk = 0; rowChunk < picturePixels.length/chunkSize; rowChunk++) {
		  for(int colChunk = 0; colChunk < picturePixels[0].length/chunkSize; colChunk++) {
			  for(int row = rowChunk*chunkSize; row < (rowChunk+1)*chunkSize; row++) {
				  for(int col = colChunk*chunkSize; col < (colChunk+1)*chunkSize; col++) {
					  int chunkNumber = rowChunk * colChunk;
					  
					  
					  int randomized = randomWithRange(0,100);
					  int adjustedCol = col+randomized;
					  int adjustedRow = row+randomized;
					  
					  for(int i = 0; i < chunkChange.length; i++) {
						  randomized = randomWithRange(0,100);
						  adjustedCol = col+randomized;
						  adjustedRow = row+randomized;
						  
						  if(chunkNumber == chunkChange[i]) {
							  Color newColor = new Color(picturePixels[row][col].getRed(),0,picturePixels[row][col].getGreen());
							  if(adjustedRow < picturePixels.length-1 && adjustedCol < picturePixels[0].length-1) picturePixels[adjustedRow][adjustedCol].setColor(newColor);
							  
								  if(col < picturePixels[0].length && row < picturePixels.length) {
									  picturePixels[row][col].setBlue(randomWithRange(0,25));
									  picturePixels[row][col].setRed(randomWithRange(0,75));
									  picturePixels[row][col].setGreen(randomWithRange(0,97));
								  }
						  }
					  }
					  
					  
				  }
			  }
		  }
	  }
	  
	  
	  
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.glitchFilter();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
