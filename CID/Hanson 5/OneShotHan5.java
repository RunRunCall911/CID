package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OneShotHan5 extends Agent {

    public class Linear {

        public ArrayList<Double> x = new ArrayList<Double>(List.of(23.0,26.0,30.0,34.0,43.0,48.0,52.0,57.0,58.0));
        public ArrayList<Double> y = new ArrayList<Double>(List.of(651.0,762.0,856.0,1063.0,1190.0,1298.0,1421.0,1440.0,1518.0));
        public double beta0 = 0;
        public double beta1 = 0;
        public double sale = 0;
        public double adv  = 0;
        public double n = Double.valueOf(x.size());

        public void LinearRegression() {
            sale = beta0 + (beta1* adv);
        }

        public void gradientDescent() {
            beta0 = 0.0;
            beta1 = 0.0;
            double alpha = 0.0005;

            while (calculatemS() > 0.0001){
                double pDB0 = 0.0;
                double pDb1 = 0.0;
                for(int i =0; i < n; i++) {
                    pDB0 +=  (y.get(i) - (beta0 + beta1 * x.get(i)));
                    pDb1 += ((x.get(i) * (y.get(i) - (beta0 + beta1 * x.get(i)))));
                }

                pDB0 *= (-2/n);
                pDb1 *= (-2/n);

                beta0 -= (alpha * pDB0);
                beta1 -= (alpha * pDb1);
            }
        }

        public void setAdvertising(double adv) {
            this.adv = adv;
        }

        public double calculatemS() {
            double mS= 0.0;
            for(int i = 0; i < n; i++){
                mS += (y.get(i) - (beta0 + (beta1*x.get(i))));
            }
            return (mS*mS) * (1.0/n);
        }


        public double getSale() {
            return sale;
        }

        public double getAdv() {
            return adv;
        }

        public double getBeta0() {
            return beta0;
        }

        public double getBeta1() {
            return beta1;
        }

    }

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        addBehaviour(new MyOneShotBehaviour());
    }

    private class MyOneShotBehaviour extends OneShotBehaviour {

        public void action() {
            Linear linear = new Linear();
            linear.setAdvertising(Integer.valueOf(JOptionPane.showInputDialog("Insert value for X: ")));
            linear.gradientDescent();
            linear.LinearRegression();
            System.out.println("y = Beta0: " + String.valueOf(linear.getBeta0()) +
                    " + (" + String.valueOf(linear.getBeta1()) +
                    ")("+String.valueOf(linear.getAdv()) + ")");
            System.out.println("y = " + String.valueOf(linear.getSale()));
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }

    }    // END of inner class ...Behaviour
}
