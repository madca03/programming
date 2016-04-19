import java.util.Scanner;

class InputSet {
  private int numberOfStacks;
  private int[] heights;
  private int averageHeight;
  public static int inputSetCount = 0;
  private int bricksToBeMove = 0;

  InputSet (int numberOfStacks, String[] heights) {
    this.numberOfStacks = numberOfStacks;
    this.convertHeights(heights);
    this.computeAverageHeight();
    inputSetCount++;
  }

  public void computeBricksMoved() {
    for (int i = 0; i < this.numberOfStacks; i++) {
      if (this.heights[i] > this.averageHeight) {
        this.bricksToBeMove += (this.heights[i] - this.averageHeight);
      }
    }
  }

  public int getBricksToBeMove() {
    return this.bricksToBeMove;
  }

  public static int getInputSetCount() {
    return inputSetCount;
  }

  private void convertHeights(String[] heights) {
    this.heights = new int[this.numberOfStacks];

    for (int i = 0; i < heights.length; i++) {
        this.heights[i] = Integer.parseInt(heights[i]);
    }
  }

  private void computeAverageHeight() {
    int sum = 0;
    for (int i = 0; i < this.numberOfStacks; i++) {
      sum += this.heights[i];
    }

    this.averageHeight = sum / this.numberOfStacks;
  }
}

class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    String line = null;

    int i = 0;
    int numberOfStacks = 0;
    String[] heights = null;
    while (input.hasNextLine()) {
      line = input.nextLine();

      if (line == "0") {
        break;
      }

      switch(i) {
        case 0:
          numberOfStacks = Integer.parseInt(line);
          i++;
          break;
        case 1:
          heights = line.split(" ");
          InputSet inputSet = new InputSet(numberOfStacks, heights);
          inputSet.computeBricksMoved();

          System.out.println("Set #" + InputSet.getInputSetCount());
          System.out.println("The minimum number of moves is " + inputSet.getBricksToBeMove() + ".\n");
          i = 0;
          break;
      }
    }
  }
}
