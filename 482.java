import java.util.Scanner;

class InputSet {
  private Data[] data = null;

  InputSet (int[] indices, String[] values) {
    this.createDataObjects(indices, values);
  }

  private void createDataObjects(int[] indices, String[] values) {
    data = new Data[indices.length];

    for (int i = 0; i < data.length; i++) {
      data[i] = new Data(indices[i], values[i]);
    }
  }

  public void printValues() {
    for (int i = 0; i < this.data.length; i++) {
      System.out.println(this.data[i].getValue());
    }
  }

  public void sort() {
    quickSort(this.data, 0, this.data.length - 1);
  }

  private void quickSort(Data[] data, int low, int high) {
    if (low < high) {
      int p = partition(data, low, high);
      quickSort(data, low, p-1);
      quickSort(data, p+1, high);
    }
  }

  private int partition(Data[] data, int low, int high) {
    int pivot = data[high].getIndex();
    int i = low;

    for (int j = low; j < high; j++) {
      if (data[j].getIndex() < pivot) {
        Data temp = data[j];
        data[j] = data[i];
        data[i] = temp;
        i++;
      }
    }

    Data temp = data[i];
    data[i] = data[high];
    data[high] = temp;

    return i;
  }
}

class Data {
  private int index;
  private String value;

  Data (int index, String value) {
    this.index = index;
    this.value = value;
  }

  public int getIndex() {
    return this.index;
  }

  public String getValue() {
    return this.value;
  }
}

class Main {
  public static int[] getArrayIndices(String line) {
    int[] indices = null;
    String[] strIndices = line.split(" ");

    indices = new int[strIndices.length];
    for (int i = 0; i < strIndices.length; i++) {
      indices[i] = Integer.parseInt(strIndices[i]);
    }

    return indices;
  }

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    int testCases = Integer.parseInt(input.nextLine());
    String line = null;


    for (int j = 0; j < testCases; j++) {
      int[] indices = null;
      String[] values = null;
      for (int i = 0; i < 3; i++) {

        if (input.hasNextLine()) {
          line = input.nextLine();

          switch (i) {
            case 0:
              continue;
            case 1:
              indices = getArrayIndices(line);
              break;
            case 2:
              values = line.split(" ");
              break;
          }

        } else {
          break;
        }

      } // end for loop

      InputSet inputSet = new InputSet(indices, values);
      inputSet.sort();
      inputSet.printValues();

      if (j != testCases - 1) {
        System.out.println("");
      }
    } // end for loop
  }
}
