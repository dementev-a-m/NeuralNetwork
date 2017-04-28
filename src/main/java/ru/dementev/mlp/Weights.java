package ru.dementev.mlp;

import java.io.Serializable;

public class Weights implements Serializable    {
    private double weightsOut[];//веса на выхлдном слое [от какого нейрона]
    private double weightsHidden[][][];//веса на скрытом слое [от какого нейрона][к какому нейрону]
    public Weights(int numberLayer,int numberEnters,int numberHidden[]){
        weightsHidden = new double[numberLayer][][];
        for(int i =0;i<weightsHidden.length;i++){
            if(i==0)
                weightsHidden[i]=new double[numberEnters][numberHidden[i]];
            else
                weightsHidden[i]=new double[numberHidden[i-1]][numberHidden[i]];
        }
        weightsOut = new double[numberHidden[numberLayer-1]];

        initWeights();
    }
    private void initWeights(){
        for(int layer=0;layer<weightsHidden.length;layer++){
            for(int i= 0 ; i<weightsHidden[layer].length;i++) {
                for (int j = 0; j < weightsHidden[layer][i].length; j++) {
                    weightsHidden[layer][i][j] = Math.random() * 0.3 + 0.1;
                }
            }
        }
        for(int i= 0 ; i<weightsOut.length;i++){
            weightsOut[i]=Math.random()*0.3+0.1;
        }
    }



    protected double[] getWeightsOut() {
        return weightsOut;

    }

    protected void setWeightsOut(double[] weightsOut) {
        this.weightsOut = weightsOut;
    }

    protected double[][][] getWeightsHidden() {
        return weightsHidden;
    }

    protected void setWeightsHidden(double[][][] weightsHidden) {
        this.weightsHidden = weightsHidden;
    }

    protected double getWeightsOut(int i) {
        return weightsOut[i];

    }

    protected void setWeightsOut(double weightsOut, int i) {
        this.weightsOut[i] = weightsOut;
    }

    protected double[][] getWeightsHidden(int i) {
        return weightsHidden[i];
    }

    protected void setWeightsHidden(double[][] weightsHidden, int i) {
        this.weightsHidden[i] = weightsHidden;
    }


    protected  double[] getWeightsHidden(int i,int j) {
        return weightsHidden[i][j];
    }

    protected void setWeightsHidden(double[] weightsHidden,int i,int j) {
        this.weightsHidden[i][j] = weightsHidden;
    }
    protected   double getWeightsHidden(int i,int j,int k) {
        return weightsHidden[i][j][k];
    }

    protected void setWeightsHidden(double weightsHidden,int i,int j,int k) {
        this.weightsHidden[i][j][k] = weightsHidden;
    }


}
