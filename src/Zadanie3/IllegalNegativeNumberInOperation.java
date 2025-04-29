package Zadanie3;

public class IllegalNegativeNumberInOperation extends RuntimeException {
    //możliwość przekazywania w konstruktorze opisu błędu
    public IllegalNegativeNumberInOperation() {
      super("Nie można wykonać operacji z liczby ujemnej!");
    }
    public IllegalNegativeNumberInOperation(String message){
        super(message);
    }
  }
