package ru.dementev.mlp;

public class Main {
    public static void main(String[] args){
        int numberHidden[]=new int[]{3,4};//кол-во нейронов на каждом скрытом слое

        double data [][]= new double[][]{{0,0},{0,1},{1,0},{1,1}};
        double dataForLearning[][]= new double[][]{{0,0,0},{0,1,0},{1,0,0},{1,1,1}};
        MLP neuralNetwork= new MLP(0.1, numberHidden,2,data.length);
        neuralNetwork.study(dataForLearning);
        neuralNetwork.start(data);
    }
}
