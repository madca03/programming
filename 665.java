import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class InputSet {
  private int numCoins = 0;
  private int weightings = 0;
  private List<Integer> trueCoins = new ArrayList<Integer>();
  private List<Integer> coins = new ArrayList<Integer>();

  InputSet (int numCoins, int weightings) {
    this.numCoins = numCoins;
    this.weightings = weightings;

    for (int i = 0; i < numCoins; i++) {
      coins.add(i+1);
    }
  }

  public int getNumCoins() {
    return this.numCoins;
  }

  public int getWeightings() {
    return this.weightings;
  }

  public List<Integer> getTrueCoins() {
    return this.trueCoins;
  }

  public void addTrueCoins(String weightingData) {
    String[] lineSplit = weightingData.split(" ");

    for (int i = 1; i < lineSplit.length; i++) {
      int newTrueCoin = Integer.parseInt(lineSplit[i]);

      if (!trueCoins.contains(newTrueCoin)) {
        trueCoins.add(Integer.parseInt(lineSplit[i]));
      }
    }
  }

  public boolean falseCoinIdentifiable() {
    if ((numCoins - trueCoins.size()) == 1) {
      return true;
    }
    return false;
  }

  public int getFalseCoin() {
    int currentCoin = 0;

    for (int i = 0; i <= coins.size(); i++) {
      currentCoin = coins.get(i);
      if (!trueCoins.contains(currentCoin)) {
        break;
      }
    }

    return currentCoin;
  }
}

class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    String line = null;
    int datasets = 0;

    datasets = Integer.parseInt(input.nextLine());
    input.nextLine(); // skip first blank space

    int i = 0;
    while (i < datasets && input.hasNextLine()) {
      line = input.nextLine();

      if (line.length() == 0) {
        i++;
        continue;
      }

      String[] lineSplit = line.split(" ");

      int numCoins = Integer.parseInt(lineSplit[0]);
      int weightings = Integer.parseInt(lineSplit[1]);

      InputSet inputSet = new InputSet (numCoins, weightings);

      int k = 0;
      String weightingData = null;
      String comparisonOperator = null;

      for (int j = 0; j < weightings * 2; j++) {
        line = input.nextLine();

        switch (k) {
          case 0:
            weightingData = line;
            k++;
            break;
          case 1:
            comparisonOperator = line;

            if (comparisonOperator.equals("=")) {
              inputSet.addTrueCoins(weightingData);
            }

            k = 0;
            break;
        } // end switch
      } // end for

      if (inputSet.falseCoinIdentifiable()) {
        if (i != datasets - 1) {
          System.out.println(inputSet.getFalseCoin() + "\n");
        } else {
          System.out.println(inputSet.getFalseCoin());
        }
      } else {
        if (i != datasets - 1) {
          System.out.println("0\n");
        } else {
          System.out.println("0");
        }
      }
    }
  }
}
