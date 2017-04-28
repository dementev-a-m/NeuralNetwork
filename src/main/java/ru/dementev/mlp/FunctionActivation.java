package ru.dementev.mlp;

/**
 * Created by Антон Дементьев on 28.04.2017.
 */
public class FunctionActivation {
    protected double threshold(double x){
        if (x > 0.5)
            x = 1;
        else
            x = 0;
        return x;
    }

    protected double signed(double x) {
        if (x > 0)
            x = 1;
        else
            x = -1;
        return x;
    }
    protected double sigmoid(double x){
        x=1/(1+Math.pow(Math.E,-x));
        return x;
    }
    protected double semilinear(double x){
        if (x < 0.5)
            x = 0;
        return x;
    }

}
