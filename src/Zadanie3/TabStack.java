/**
 * @author Sławek
 * Klasa implementująca stos za pomocą tablicy
 */
package Zadanie3;

import java.util.NoSuchElementException;

public class TabStack {
    private String[] stack = new String[20];
    private int size = 0;
    /**
     * Metoda zdejmująca wartość ze stosu
     * @return wartość z góry stosu
     */
    public String pop(){
        if(size < 0) throw new NoSuchElementException("Program próbuje zwrócić element tablicy o indeksie mniejszym niż 0! Sprawdź poprawnośc nawiasów, ilość operatorów i argumentów.");
        size--;
        return stack[size];
    }
    /**
     * metoda dokładająca na stos
     * @param a - wartość dokładana do stosu
     */
    public void push(String a){
        if ( size == 20 ) throw new ArrayIndexOutOfBoundsException("Przekroczono rozmiar stosu (20 znaków)");
        stack[size] = a;
        size++;
    }
    /**
     * Metoda zwraca tekstową reprezentację stosu
     */
    public String toString(){
        String tmp = "";
        for(int i = 0; i < size; i++)
            tmp += stack[i] + " ";
        return tmp;
    }
    /**
     * Metoda zwraca rozmiar stosu
     * @return size rozmiar stosu
     */
    public int getSize(){
        return size;
    }
    /**
     * Ustawia wartość stosu
     * @param i
     */
    public void setSize(int i){
        size = i;
    }
    /**
     * Metoda zwraca wartość z określonej pozycji stosu
     * @param i pozycja parametru do zobaczenia
     * @return wartość stosu
     */
    public String showValue(int i){
        if(i < size)
            return stack[i];
        else return null;
    }
}