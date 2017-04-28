package ru.dementev.mlp;

/**
 * Created by Антон Дементьев on 28.04.2017.
 */
public class Learning {
    private double speed;
    private Neuron neuron;
    private Weights weights;
    private Summator summator;
    public Learning(double speed, Neuron neuron,Weights weights,Summator summator){
        this.speed=speed;
        this.neuron=neuron;
        this.weights=weights;
        this.summator=summator;
    }

    protected void learning(double dataForLearning[][]){
        double data[]=new double[dataForLearning[0].length-1];
        double error[]=new double[dataForLearning.length];
        double gError;
        double outer;
        do {
            gError=0;
            for (int i = 0; i < dataForLearning.length; i++) {
                for (int j = 0; j < data.length; j++)
                    data[j] = dataForLearning[i][j];

                outer=summator.summator(data);

                double lError = dataForLearning[i][dataForLearning[i].length -1]-outer;

                gError+=Math.pow(lError,2);

                for(int layer=0;layer<weights.getWeightsHidden().length;layer++) {
                    for (int j = 0; j < neuron.getHiddenNeuron(layer).length; j++)
                        error[j] = lError * weights.getWeightsOut(j);

                    for (int j = 0; j < data.length; j++)
                        for (int k = 0; k < neuron.getHiddenNeuron(layer).length; k++)
                            weights.setWeightsHidden(weights.getWeightsHidden(layer,j,k)+speed * error[k] * data[j],layer,j,k) ;

                    for (int j = 0; j < neuron.getHiddenNeuron(layer).length; j++)
                        weights.setWeightsOut(weights.getWeightsOut(j)+speed * lError * neuron.getHiddenNeuron(layer,j),j);
                }
            }
            gError=0.5*gError;
        }
        while (gError!=0);
        System.out.println("Ошибка:"+ gError*100+"%");
    }

}
