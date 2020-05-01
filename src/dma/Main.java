package dma;

import dma.controller.OrderManagement;

public class Main {

    public static void main(String[] args) {
        programm1();
    }

    private static void programm1() {
       // MenuManagement.start();
        OrderManagement.startEvaluation(); // AUSWERTUNG PROGRAMM 1
        OrderManagement.exportData(); // EXPORTE PROGRAMM 1
    }

    private static void programm2() {
        OrderManagement.start();
    }
}
