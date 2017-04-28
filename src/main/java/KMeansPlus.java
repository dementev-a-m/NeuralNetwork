
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeansPlus {
    public KMeansPlus(){}

    static void start(double [][] date) {


        double[][] rawData =   new double[date.length][];
        /*
        String path = "C:\\Users\\Антон\\Desktop\\divisible.txt";
        String[] str = System.IO.File.ReadAllLines(path);
        double[][] rawData =   new double[str.length][];
        for (int i = 0; i < str.length; i++)
        {

            rawData[i] = str[i].Split(default(String[]), StringSplitOptions.RemoveEmptyEntries).Select(x => double.Parse(x)).ToArray<double>();
        }
        */

        showData(rawData, true, true);

        int numClusters = 5;
        int[] clustering = cluster(rawData, numClusters, 0);

        showClustered(rawData, clustering, numClusters);
        calculateError(rawData, clustering, numClusters);
    }

    static int[] cluster(double[][] rawData, int numClusters, int seed) {

        double[][] data = normalized(rawData);

        boolean changed=true;
        boolean success=true;

        double[][] means = initMeans(numClusters, data, seed);// номер кластера

        int[] clustering = new int[data.length];

        int maxCount = data.length * 10;
        int ct = 0;
        while (changed && success && ct < maxCount) {
            changed = updateClustering(data, clustering, means);
            success = updateMeans(data, clustering, means);
            ct++;
        }

        return clustering;
    }

    private static double[][] initMeans(int numClusters, double[][] data, int seed){

        double[][] means = makeMatrix(numClusters, data[0].length);

        List<Integer> used = new ArrayList<Integer>();
        Random rnd = new Random(seed);

        int idx = rnd.nextInt(data.length)+1;
        System.arraycopy(data[idx],0, means[0],0, data[idx].length);//копируем рандомные элементы
        used.add(idx);

        for (int k = 1; k < numClusters; k++) {
            double[] dSquared = new double[data.length];
            int newMean = -1;
            for (int i = 0; i < data.length; i++) {
                if (used.contains(i)) continue;

                double[] distances = new double[k];
                for (int j = 0; j < k; j++)
                    distances[j] = distance(data[i], means[k]);

                int m = getMinIndex(distances);

                dSquared[i] = distances[m] * distances[m];
            }


            double p = rnd.nextDouble();
            double sum = 0;
            for (double element:  dSquared)
                sum += element;
            double cumulative = 0;

            int ii = 0;
            int sanity = 0;
            while (sanity < data.length * 2) {
                cumulative += dSquared[ii] / sum;
                if (cumulative >= p && !used.contains(ii)) {
                    newMean = ii;
                    used.add(newMean);
                    break;
                }
                ii++;
                if (ii >= dSquared.length) ii = 0;
                sanity++;
            }

            System.arraycopy(data[newMean],0, means[k],0, data[newMean].length);
        }

        return means;

    }

    private static double[][] normalized(double[][] rawData) {

        double[][] result = new double[rawData.length][];
        for (int i = 0; i < rawData.length; ++i) {
            result[i] = new double[rawData[i].length];
            System.arraycopy(rawData[i], 0,result[i], 0,rawData[i].length);
        }

        for (int j = 0; j < result[0].length; ++j) {
            double colSum = 0;
            for (double[] res :result)
                colSum += res[j];
            double mean = colSum / result.length;
            double sum = 0;
            for (int i = 0; i < result.length; ++i)
                sum += (result[i][j] - mean) * (result[i][j] - mean);
            double sd = sum / result.length;
            for (int i = 0; i < result.length; ++i)
                result[i][j] = (result[i][j] - mean) / sd;
        }
        return result;
    }

    private static double[][] makeMatrix(int rows, int cols) {

        double[][] result = new double[rows][];
        for (int i = 0; i < rows; i++)
            result[i] = new double[cols];
        return result;
    }
    // перебирает данные и находит центр тяжести кластера
    private static boolean updateMeans(double[][] data, int[] clustering, double[][] means){

        int numClusters = means.length;
        int[] clusterCounts = new int[numClusters];
        for (int i = 0; i < data.length; i++) {
            int cluster = clustering[i];
            clusterCounts[cluster]++;
        }

        for (int k = 0; k < numClusters; k++)
            if (clusterCounts[k] == 0)
                return false;

        for (int k = 0; k < means.length; k++)
            for (int j = 0; j < means[k].length; j++)
                means[k][j] = 0.0;

        for (int i = 0; i < data.length; i++) {
            int cluster = clustering[i];
            for (int j = 0; j < data[i].length; j++)
                means[cluster][j] += data[i][j];
        }

        for (int k = 0; k < means.length; k++)
            for (int j = 0; j < means[k].length; j++)
                means[k][j] /= clusterCounts[k];
        return true;
    }

    private static boolean updateClustering(double[][] data, int[] clustering, double[][] means) {

        int numClusters = means.length;
        boolean changed = false;

        int[] newClustering = new int[clustering.length];
        System.arraycopy(clustering,0, newClustering,0, clustering.length);

        double[] distances = new double[numClusters];

        for (int i = 0; i < data.length; i++) {
            for (int k = 0; k < numClusters; k++)
                distances[k] = distance(data[i], means[k]);

            int newClusterID = getMinIndex(distances);
            if (newClusterID != newClustering[i]) {
                changed = true;
                newClustering[i] = newClusterID;
            }
        }

        if (!changed)
            return false;
        int[] clusterCounts = new int[numClusters];
        for (int i = 0; i < data.length; i++) {
            int cluster = newClustering[i];
            ++clusterCounts[cluster];
        }

        for (int k = 0; k < numClusters; k++)
            if (clusterCounts[k] == 0)
                return false;

        System.arraycopy(newClustering, 0,clustering,0, newClustering.length);
        return true;
    }
    //возваращает евклидого расстояние
    private static double distance(double[] tuple, double[] mean){

        double sumSquaredDiffs = 0;
        for (int j = 0; j < tuple.length; j++)
            sumSquaredDiffs += Math.pow((tuple[j] - mean[j]), 2);
        return Math.sqrt(sumSquaredDiffs);
    }

    private static int getMinIndex(double[] distances) {
        int indexOfMin = 0;
        double smallDist = distances[0];
        for (int k = 0; k < distances.length; k++) {
            if (distances[k] < smallDist) {
                smallDist = distances[k];
                indexOfMin = k;
            }
        }
        return indexOfMin;
    }

    private static void showData(double[][] data, boolean indices, boolean newLine) {
        for (int i = 0; i < data.length; ++i) {
            if (indices) System.out.print(i + " ");
            for (int j = 0; j < data[i].length; ++j) {
                if (data[i][j] >= 0.0) System.out.print(" ");
                System.out.print(data[i][j] + " ");
            }
            System.out.println("");
        }
        if (newLine) System.out.println("");
    }

    private static void showClustered(double[][] data, int[] clustering,int numClusters) {
        for (int k = 0; k < numClusters; k++) {
            System.out.println("Кластер №" + (k+1));
            for (int i = 0; i < data.length; i++) {
                int clusterID = clustering[i];// номер кластера
                if (clusterID != k) continue;
                System.out.print(i + " ");
                for (int j = 0; j < data[i].length; j++) {

                    System.out.print(data[i][j]+ " ");
                }
                System.out.println("");
            }

        }
    }

    private static void calculateError(double[][] data, int[] clustering, int numClusters) {
        //int[,] test = new int[numClusters, numClusters];

        double result = 0;
        //for (int j = 0; j < numClusters; j++)
        //{
        //    for (int i = (data.length / numClusters) * j; i < (data.length / numClusters) * (j + 1); i++)
        //    {
        //        for (int k = 0; k < numClusters; k++)
        //        {
        //            if (clustering[i] == k) test[j, k]++;

        //        }


        //    }

        //    for (int i = 0; i < numClusters; i++)
        //    {

        //        if (test[j, i] != (data.length / numClusters) && test[j, i] != 0 && test[j, i] <= (data.length / numClusters) / 2)
        //        {

        //            result += (data.length / numClusters) - test[j, i];


        //        }
        //        test1[j] += test[i, j];
        //    }

        //    if (test1[j] > (data.length / numClusters))
        //    {

        //        result += Math.Abs((data.length / numClusters) - test1[j]);
        //    }
        //}
        for (int j = 0; j < numClusters; j++) {
            int[] test1 = new int[numClusters];
            for (int k = 0; k < numClusters; k++) {
                for (int i = j * data.length / numClusters; i < (j + 1) * data.length / numClusters; i++) {
                    if (clustering[i] == k) test1[k]++;
                }
            }
            System.out.println(getMax(test1));
            result+=Math.abs(data.length / numClusters-getMax(test1));

        }
        result = (result / data.length) * 100;
        System.out.println("Процент ошибки программы= " + result + "%");
    }
    private static int getMax(int[] array) {
       int max=array[0];
       for(int i:array)
           if(max<i)
               max=i;
       return max;
    }
}

    
