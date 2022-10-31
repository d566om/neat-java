package examples;

import com.evo.NEAT.Environment;
import com.evo.NEAT.Pool;
import com.evo.NEAT.genome.Genome;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WordSearch implements Environment {

    private class inputClass {
        public float[] inputArr = new float[12];
        public int result;
        public inputClass(String str) {
            for (int i = 0; i < str.length(); i++) {
                inputArr[i] = str.charAt(i);
            }

            result = str.contains("CAT") ? 1 : 0;
        }
    }

    private List<inputClass> trainingSet;

    public WordSearch() {
        trainingSet = new LinkedList<>();
        trainingSet.add(new inputClass("ASDGGEREGERG"));
        trainingSet.add(new inputClass("ASDGGCATGERG"));
        trainingSet.add(new inputClass("WERVFWFWERER"));
        trainingSet.add(new inputClass("ASDGGEREGERG"));
        trainingSet.add(new inputClass("ACATGEREGERG"));
        trainingSet.add(new inputClass("ASDGVEWRVREV"));
        trainingSet.add(new inputClass("ASDGGEREECAT"));
        trainingSet.add(new inputClass("ERTERTERTECA"));
        trainingSet.add(new inputClass("VWERECATRTRE"));
        trainingSet.add(new inputClass("WEFVRRMUTUKD"));
    }

    @Override
    public void evaluateFitness(ArrayList<Genome> population) {
        for (Genome gene: population) {
            float fitness = 0;
            gene.setFitness(0);

            for (inputClass c : trainingSet) {
                float inputs[] = c.inputArr;
                float output[] = gene.evaluateNetwork(inputs);
                int expected = c.result;
                fitness += (1 - Math.abs(expected - output[0]));
            }

            //fitness = fitness * fitness;

            gene.setFitness(fitness);
        }
    }


    public static void main(String arg0[]){
        WordSearch wordSearch = new WordSearch();

        Pool pool = new Pool();
        pool.initializePool();

        Genome topGenome;
        int generation = 0;
        while(true){
            pool.evaluateFitness(wordSearch);
            topGenome = pool.getTopGenome();
            System.out.println("TopFitness : " + topGenome.getPoints());

            if(topGenome.getPoints()>7){
                break;
            }
            System.out.println("Population : " + pool.getCurrentPopulation() );
            System.out.println("Generation : " + generation );

            pool.breedNewGeneration();
            generation++;

        }
        System.out.println(topGenome.evaluateNetwork(new float[]{1,0})[0]);
        System.out.println("TopGenome: " + topGenome.toString());
    }
}
