package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OneShotHan4 extends Agent {

    public class Table {

        public double beta0 = 0;
        public double beta1 = 0;
        public ArrayList<Double> x = new ArrayList<Double>(List.of(2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0));
        public double sumx = 0;
        public ArrayList<Double> y = new ArrayList<Double>(List.of(4.0, 8.0, 12.0, 16.0, 20.0, 24.0, 28.0, 32.0, 36.0));
        public double sumy = 0;
        public ArrayList<Double> xx = new ArrayList<>();
        public double sumxx = 0;
        public ArrayList<Double> xy = new ArrayList<>();
        public double sumxy = 0;

    }

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        addBehaviour(new MyOneShotBehaviour());
    }

    private class MyOneShotBehaviour extends OneShotBehaviour {

        public void action() {
            Table table = new Table();

            for (int c = 0; c < 9; c++) {
                double xx = table.x.get(c) * table.x.get(c);
                table.xx.add(xx);
            }

            for (int c = 0; c < 9; c++) {
                double xy = table.x.get(c) * table.y.get(c);
                table.xy.add(xy);
            }

            table.sumx = sumatoriax(table.x);
            table.sumy = sumatoriay(table.y);
            table.sumxx = sumatoriaxx(table.xx);
            table.sumxy = sumatoriaxy(table.xy);

            table.beta1 = Beta1(9, table.sumxy, table.sumx, table.sumy, table.sumxx);
            table.beta0 = Beta0(table.sumx, table.sumy, 9, table.beta1);

            System.out.println("Lista de Datos X: " + table.x);
            System.out.println("Lista de Datos Y: " + table.y);
            System.out.println("Lista de Datos X Cuadrada: " + table.xx);
            System.out.println("Lista de Datos XY: " + table.xy);
            System.out.println("Sumatoria de X: " + table.sumx);
            System.out.println("Sumatoria de Y: " + table.sumy);
            System.out.println("Sumatoria de X Cuadrada: " + table.sumxx);
            System.out.println("Sumatoria de XY: " + table.sumxy);
            System.out.println("Beta 1 es: " + table.beta1);
            System.out.println("Beta 0 es: " + table.beta0);

            double Pred = Double.parseDouble(JOptionPane.showInputDialog("Insert value for X: "));
            double Result = ((table.beta0) + (table.beta1 * Pred));
            System.out.println("Resultado es " + " :" + Result);
        }

        private static double sumatoriax(ArrayList<Double> x) {
            double sumatoriax = 0;
            for (int i = 0; i < x.size(); i++)
                sumatoriax += x.get(i);
            return sumatoriax;
        }

        private static double sumatoriay(ArrayList<Double> y) {
            double sumatoriay = 0;
            for (int i = 0; i < y.size(); i++)
                sumatoriay += y.get(i);
            return sumatoriay;
        }

        private static double sumatoriaxx(ArrayList<Double> xx) {
            double sumatoriaxx = 0;
            for (int i = 0; i < xx.size(); i++)
                sumatoriaxx += xx.get(i);
            return sumatoriaxx;
        }

        private static double sumatoriaxy(ArrayList<Double> xy) {
            double sumatoriaxy = 0;
            for (int i = 0; i < xy.size(); i++)
                sumatoriaxy += xy.get(i);
            return sumatoriaxy;
        }

        private static double Beta1(int n, double sumxy, double sumx, double sumy, double sumxx) {
            double S = ((n * sumxy) - (sumx * sumy));
            double I = ((n * sumxx) - (sumx * sumx));
            return S / I;
        }

        private static double Beta0(double sumx, double sumy, int n, double beta1) {
            double Y = sumy / n;
            double X = sumx / n;
            return ((Y) - (beta1 * X));
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }    // END of inner class ...Behaviour
}
