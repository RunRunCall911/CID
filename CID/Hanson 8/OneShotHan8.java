package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

import javax.swing.*;
import java.util.*;



public class OneShotHan8 extends Agent {

    //    INICIO DE CLASE PERSONA, DONDE SE ALMACENARA LOS DATOS DE LA TABLA QUE NOS PROPORCIONO EN CLASSROOM
    public class Persona implements Comparable<Persona> {
        private double Peso     = 0.0;
        private double Altura   = 0.0;
        private double Edad     = 0.0;
        private Double Distancia = 0.0;

        public Persona(double Altura, double Edad, double Peso) {
            this.Edad   = Edad;
            this.Altura = Altura;
            this.Peso   = Peso;
        }

        public Persona() {
        }

        public void setEdad(double Edad) {
            this.Edad = Edad;
        }

        public void setAltura(double Altura) {
            this.Altura = Altura;
        }

        public void setPeso(double Peso) {
            this.Peso = Peso;
        }

        public double getEdad() {
            return Edad;
        }

        public double getAltura() {
            return Altura;
        }

        public double getPeso() {
            return Peso;
        }

        public void setDistancia(Double Distancia) {
            this.Distancia = Distancia;
        }

        public Double getDistancia() {
            return Distancia;
        }

        public int compareTo(Persona x) {
            return Distancia.compareTo(x.getDistancia());
        }
    }
// FIN DE CLASE PERSONA

//    INICIO DE CLASE KNN DONDE SE LLEVARA ACABO EL ALGORIMTO KNN

    public class KNearestNeighbors {

        public ArrayList<Persona> Personas = new ArrayList<Persona>(List.of(
                new Persona(5.0, 45.0, 77.0), new Persona(5.11, 26.0, 47.0),
                new Persona(5.6, 30.0, 55.0), new Persona(5.9, 34.0, 59.0),
                new Persona(4.8, 40.0, 72.0), new Persona(5.8, 36.0, 60.0),
                new Persona(5.3, 19.0, 40.0), new Persona(5.8, 28.0, 60.0),
                new Persona(5.5, 23.0, 45.0), new Persona(5.6, 32.0, 58.0)));
        public ArrayList<Persona> PersonaNorm = new ArrayList<Persona>(List.of(
                new Persona(), new Persona(), new Persona(),
                new Persona(), new Persona(), new Persona(),
                new Persona(), new Persona(), new Persona(), new Persona()));
        public double k     = 2;
        public double Altura = 0.0;
        public double Peso  = 0.0;
        public double Edad  = 0.0;
        public double minA  = 100000.0;
        public double maxA  = 0.0;
        public double minH  = 100000.0;
        public double maxH  = 0.0;

        public void KNearestNeighbors(double Altura, double Edad, double k) {
            this.k = k;
            this.Altura = Altura;
            this.Edad = Edad;

            double sum = 0.0;
            Normalizar();
            //normalizeGivenValues();
            setDistances();
            Collections.sort(PersonaNorm);
            System.out.println(k + " Puntos mas cercanos: \n");
            for (int i = 0; i < k; i++) {
                System.out.print(PersonaNorm.get(i).getPeso() + "(");
                System.out.print(PersonaNorm.get(i).getDistancia() + ")\n");
                sum += PersonaNorm.get(i).getPeso();
            }

            Peso = sum / this.k;
        }

        public void setDistances() {
            for (int i = 0; i < 10; i++) {
                PersonaNorm.get(i).setDistancia(DistanciaEuclidiana(PersonaNorm.get(i).getAltura(), PersonaNorm.get(i).getEdad()));
            }
        }

        public double DistanciaEuclidiana(double Altura, double Edad) {
            return Math.sqrt((Math.pow(this.Altura - Altura, 2) + Math.pow(this.Edad - Edad, 2)));
        }

        public double getPeso() {
            return Peso;
        }

        public void calculateMinMax() {
            for (int i = 0; i < 10; i++) {
                double Edad = Personas.get(i).getEdad();
                double Altura = Personas.get(i).getAltura();
                if (Edad < minA)
                    minA = Edad;
                if (Edad > maxA)
                    maxA = Edad;
                if (Altura < minH)
                    minH = Altura;
                if (Edad > maxH)
                    maxH = Altura;
            }
        }

        public void Normalizar() {
            calculateMinMax();
            for (int i = 0; i < 10; i++) {
                Persona persona = new Persona();
                persona.setEdad((Personas.get(i).getEdad() - minA) / (maxA - minA));
                persona.setAltura((Personas.get(i).getAltura() - minH) / (maxH - minH));
                persona.setPeso(Personas.get(i).getPeso());
                PersonaNorm.set(i, persona);
            }
        }

        public void normalizeGivenValues() {
            for (int i = 0; i < 10; i++) {
                Altura = ((Altura - minH) / (maxH - minH));
                Edad = ((Edad - minA) / (maxA - minA));
            }
        }
    }

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        addBehaviour(new MyOneShotBehaviour());
    }

    private class MyOneShotBehaviour extends OneShotBehaviour {

        public void action() {
            KNearestNeighbors KNN = new KNearestNeighbors();
            KNN.KNearestNeighbors(Double.valueOf(JOptionPane.showInputDialog("Altura : ")), Double.valueOf(JOptionPane.showInputDialog("Edad   : ")), Double.valueOf(JOptionPane.showInputDialog("K      : ")));
            System.out.println("Peso = " + KNN.getPeso());
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }

    }    // END of inner class ...Behaviour
}
