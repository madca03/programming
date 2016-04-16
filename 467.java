import java.util.Scanner;

class InputSet {
  private Signal[] signals = null;
  private int signalLength = 0;
  public static int inputCount = 0;
  private int syncTime = 0;

  InputSet (String input) {
    this.parseInput(input);
    this.inputCount++;
  }

  public int getSignalLength() {
    return this.signalLength;
  }

  public Signal[] getSignals() {
    return this.signals;
  }

  public int getSyncTime() {
    return this.syncTime;
  }

  public void computeSyncTime() {
    int i = 0;
    int j = 1;
    int k = 0;

    this.signals[0].moveToNextCycle();

    while(true) {
      if (this.signals[i].getGreenLightTime() <= this.signals[j].getCurrentTime()) {
        this.signals[i].moveToNextCycle();

        if (this.signals[i].getCurrentTime() > 60*60) {
          this.syncTime = 0;
          break;
        }

        k = 0;
        if (i != this.signalLength - 1) {
          i++;
          j = 0;
        } else {
          i = 0;
          j = 1;
        }
      }
      else {
        if (j != this.signalLength - 1) {
          j++;
        }
        else {
          k++;

          if (k == this.signalLength) {
            this.computeMaxSyncTime();
            break;
          }

          if (i != this.signalLength - 1) {
            i++;
            j = 0;
          } else {
            i = 0;
            j = 1;
          }
        }
      }
    }
  }

  private void computeMaxSyncTime() {
    int max = this.signals[0].getCurrentTime();

    for (int i = 1; i < this.signalLength; i++) {
      if (max < this.signals[i].getCurrentTime()) {
        max = this.signals[i].getCurrentTime();
      }
    }

    this.syncTime = max;
  }

  private void parseInput(String input) {
    String[] strSignals = input.split(" ");
    Integer[] intSignals = new Integer[strSignals.length];

    for (int i = 0; i < strSignals.length; i++) {
      intSignals[i] = Integer.parseInt(strSignals[i]);
    }

    this.quickSort(intSignals, 0, intSignals.length - 1);

    this.signals = new Signal[intSignals.length];
    this.signalLength = intSignals.length;

    for (int i = 0; i < signals.length; i++) {
      this.signals[i] = new Signal(intSignals[i]);
    }
  }

  private void quickSort(Integer[] signals, int low, int high) {
    if (low < high) {
      int p = partition(signals, low, high);
      quickSort(signals, low, p - 1);
      quickSort(signals, p + 1, high);
    }
  }

  private int partition(Integer[] signals, int low, int high) {
    int pivot = signals[high];
    int i = low;

    for (int j = low; j < high; j++) {
      if (signals[j] < pivot) {
        int temp = signals[j];
        signals[j] = signals[i];
        signals[i] = temp;
        i++;
      }
    }

    int temp = signals[i];
    signals[i] = signals[high];
    signals[high] = temp;

    return i;
  }
}

class Signal {
  private int cycleTime;
  private int totalCycleTime;
  private int currentTime;
  private int greenLightTime;

  Signal (int cycleTime) {
    this.cycleTime = cycleTime;
    this.totalCycleTime = cycleTime*2;
    this.currentTime = 0;
    this.greenLightTime = this.currentTime + (this.cycleTime - 5);
  }

  public int getCycleTime() {
    return this.cycleTime;
  }

  public int getCurrentTime() {
    return this.currentTime;
  }

  public int getGreenLightTime() {
    return this.greenLightTime;
  }

  public void moveToNextCycle() {
    this.currentTime += this.totalCycleTime;
    this.greenLightTime = this.currentTime + (this.cycleTime - 5);
  }
}

class SyncTime {
  private int minutes;
  private int seconds;

  SyncTime (int minutes, int seconds) {
    this.minutes = minutes;
    this.seconds = seconds;
  }

  public int getMinutes() {
    return this.minutes;
  }

  public int getSeconds() {
    return this.seconds;
  }
}

class Main {
  public static SyncTime convertToMinutes(int timeInSeconds) {
    int minutes = timeInSeconds / 60;
    int secondsRemainder = timeInSeconds % 60;

    return (new SyncTime(minutes, secondsRemainder));
  }

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    String line = null;
    InputSet inputSet = null;

    while (input.hasNextLine()) {
      line = input.nextLine();
      inputSet = new InputSet(line);
      inputSet.computeSyncTime();

      if (inputSet.getSyncTime() == 0) {
        System.out.println("Set " +
          InputSet.inputCount +
          " is unable to synch after one hour.");
      } else {
        SyncTime syncTime = convertToMinutes(inputSet.getSyncTime());

        System.out.println("Set " +
          InputSet.inputCount +
          " synchs again at " +
          syncTime.getMinutes() +
          " minute(s) and " +
          syncTime.getSeconds() +
          " second(s) after all turning green."
          );
      }
    }
  }
}
