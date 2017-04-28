package ru.dementev.mlp;

/**
 * Created by Антон Дементьев on 28.04.2017.
 */
public class Neuron {

    private double hiddenNeuron[][];//нейроны на скрытом слое
    protected Neuron(int numberLayer,int numberHidden[]){
        hiddenNeuron = new double[numberLayer] [];
        for(int i=0;i<hiddenNeuron.length;i++) {
            hiddenNeuron[i]=new double[numberHidden[i]];
        }
    }
    public double[][] getHiddenNeuron() {
        return hiddenNeuron;
    }
    public  double[] getHiddenNeuron(int i){
        return hiddenNeuron[i];
    }
    public double getHiddenNeuron(int i,int j){
        return hiddenNeuron[i][j];
    }
    public  void setHiddenNeuron(double[][] hiddenNeuron) {
        this.hiddenNeuron = hiddenNeuron;
    }
    public void setHiddenNeuron(double[] hiddenNeuron,int i) {
        this.hiddenNeuron[i] = hiddenNeuron;
    }
    public void setHiddenNeuron(double hiddenNeuron,int i, int j) {
        this.hiddenNeuron[i][j] = hiddenNeuron;
    }

}
