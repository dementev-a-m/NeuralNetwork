package ru.dementev.mlp;

import java.io.Serializable;

public class MLP implements Serializable{

    private int numberLayer;
    private Weights weights;
    private Neuron neuron;
    private Summator summator;
    private double speed;
    public MLP(double speed, int numberHidden[], int numberLayer, int numberEnters){
        this.numberLayer=numberLayer;
        this.speed=speed;
        neuron=new Neuron(numberLayer,numberHidden);
        weights = new Weights( numberLayer, numberEnters,numberHidden);
        summator=new Summator(neuron,weights);
    }
    public  void study(double dataForLearning[][]){
        new Learning(speed,neuron,weights,summator).learning(dataForLearning);
    }
    public void start(double enters[][]) {
        double data[]= new double[enters[0].length];
        double out[] = new double[neuron.getHiddenNeuron(numberLayer-1).length];
        for (int i = 0; i < enters.length; i++) {
            for (int j = 0; j < enters[i].length; j++) {
                data[j] = enters[i][j];
                System.out.print(data[j]+" ");
            }
            System.out.println();
            out[i] = summator.summator(data);
        }
        showOut(out);
    }

    private void showOut(double[] out)
    {
        System.out.println("Результат:");
        for(double o:out)
            System.out.println(o);
    }
}
