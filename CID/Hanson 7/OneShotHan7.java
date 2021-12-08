package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OneShotHan7 extends Agent {

    public class LogistR {

        public ArrayList<Double> x1 = new ArrayList<Double>(List.of(1.0,1.0,1.0));
        public ArrayList<Double> x2 = new ArrayList<Double>(List.of(1.0,4.0,2.0));
        public ArrayList<Double> x3 = new ArrayList<Double>(List.of(1.0,2.0,4.0));
        public ArrayList<Double> w = new ArrayList<Double>(List.of(0.0,0.0,0.0));
        public double y1 = 0;
        public double y2 = 1;
        public double y3 = 1;
        public double rep  = 100;
        public double lrp = 0.1;


        public void LogisticRegression() {
            for(int i = 0; i < rep; i++){
                double sigmoide1 = cal(x1);
                double sigmoide2 = cal(x2);
                double sigmoide3 = cal(x3);
                w.set(0, w.get(0) - (lrp*(((sigmoide1-y1)*x1.get(0)) + ((sigmoide2-y2)*x2.get(0)) + ((sigmoide3-y3)*x3.get(0)))));
                w.set(1, w.get(1) - (lrp*(((sigmoide1-y1)*x1.get(1)) + ((sigmoide2-y2)*x2.get(1)) + ((sigmoide3-y3)*x3.get(1)))));
                w.set(2, w.get(2) - (lrp*(((sigmoide1-y1)*x1.get(2)) + ((sigmoide2-y2)*x2.get(2)) + ((sigmoide3-y3)*x3.get(2)))));
            }
        }

        public double cal(List<Double> x){
            return (1.0/(1.0+Math.exp(-((w.get(0))+(w.get(1)*x.get(1))+(w.get(2)*x.get(2))))));
        }

        public double getw0(){
            return  w.get(0);
        }

        public double getw1(){
            return w.get(1);
        }

        public double getw2(){
            return w.get(2);
        }

    }

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        addBehaviour(new MyOneShotBehaviour());
    }

    private class MyOneShotBehaviour extends OneShotBehaviour {

        public void action() {
            LogistR logistR = new LogistR();
            logistR.LogisticRegression();
            System.out.println("w = (   W1: " + logistR.getw0() + ",    W2: " + logistR.getw1() + ",    W3: " +  logistR.getw2() + ")");
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }

    }    // END of inner class ...Behaviour
}
