/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.columbia.stat.wood.bnol.util;

import java.util.Iterator;

/**
 * Uniform discrete distribution over the integers in [leftType, rightType].
 * @author nicholasbartlett
 */

public class IntUniformDiscreteDistribution extends IntDiscreteDistribution{
    
    private static final long serialVersionUID = 1;

    private int leftType, rightType;
    private double p;

    /**
     * Creates a uniform discrete distribution over the integers [0, alphabetSize).
     * @param alphabetSize
     */
    public IntUniformDiscreteDistribution(int alphabetSize){
        leftType = 0;
        rightType = alphabetSize - 1;
        p = 1.0 / (double) alphabetSize;
    }

    /**
     * Creates a uniform discrete distribution over the integers [leftType, rightType].
     * @param leftType
     * @param rightType
     */
    public IntUniformDiscreteDistribution(int leftType, int rightType){
        this.leftType = leftType;
        this.rightType = rightType;
        p = 1.0 / (double) ((long) rightType - (long) leftType + (long) 1);
    }

    /**
     * Gets the probability of an integer type.
     * @param type
     * @return probability of type
     */
    public double probability(int type) {
        if(type <= rightType && type >= leftType){
            return p;
        } else {
            return 0.0;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int generate(MersenneTwisterFast rng){
        double randomNumber = rng.nextDouble(), cuSum = 0.0;
        int sample = leftType - 1;

        while(cuSum <= randomNumber && sample <= rightType){
            cuSum += p;
            sample++;
        }

        if(cuSum <= randomNumber || cuSum > 1.0){
            throw new RuntimeException("Something bad happened, cuSum = " + cuSum + ", randomNumber = " + randomNumber);
        }

        return sample;
    }

    /**
     * Gets an iterator over Integer Double pairs such that the Double value is
     * the probability of the integer type in this discrete distribution.
     * @return iterator
     */
    public Iterator<Pair<Integer, Double>> iterator() {
        return new UniformIterator();
    }

    private class UniformIterator implements Iterator<Pair<Integer, Double>> {
        int type = leftType;
        
        public boolean hasNext() {
            return type <= rightType;
        }

        public Pair<Integer, Double> next() {
            return new Pair(type++, p);
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }
    }
}
