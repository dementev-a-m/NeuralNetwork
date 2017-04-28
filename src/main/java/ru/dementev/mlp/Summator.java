package ru.dementev.mlp;

/**
 * Created by Антон Дементьев on 28.04.2017.
 */
public class Summator {
    private Neuron neuron;
    private Weights weights;
    private FunctionActivation functionActivation;
    protected Summator(Neuron neuron,Weights weights){
        this.neuron=neuron;
        this.weights=weights;
        functionActivation=new FunctionActivation();
    }
    protected double summator(double data[]) {
        double outer=0;
        for(int layer=0;layer<neuron.getHiddenNeuron().length;layer++) {
            for (int i = 0; i < neuron.getHiddenNeuron(layer).length; i++) {
                neuron.setHiddenNeuron(0,layer,i);
                for (int j = 0; j < data.length; j++)
                    neuron.setHiddenNeuron((neuron.getHiddenNeuron(layer,i) + data[j] * weights.getWeightsHidden(layer,j,i)),layer,i);

                neuron.setHiddenNeuron(functionActivation.threshold(neuron.getHiddenNeuron(layer,i)),layer,i);
            }
            data=neuron.getHiddenNeuron(layer);
        }
        for(int i=0;i<data.length;i++) {
            outer+= data[i]*weights.getWeightsOut(i);
        }
        outer = functionActivation.threshold(outer);
        return outer;
    }
}
