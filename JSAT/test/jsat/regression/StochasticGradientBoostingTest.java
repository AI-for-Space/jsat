/*
 * Copyright (C) 2015 Edward Raff <Raff.Edward@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jsat.regression;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jsat.FixedProblems;
import jsat.classifiers.trees.DecisionStump;
import jsat.classifiers.trees.DecisionTree;
import jsat.datatransform.LinearTransform;
import jsat.utils.SystemInfo;
import jsat.utils.random.RandomUtil;
import jsat.utils.random.XORWOW;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Edward Raff <Raff.Edward@gmail.com>
 */
public class StochasticGradientBoostingTest
{
    
    public StochasticGradientBoostingTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    @Test
    public void testTrainC_RegressionDataSet()
    {
        System.out.println("train");

        StochasticGradientBoosting instance = new StochasticGradientBoosting(new DecisionTree(), 50);

        RegressionDataSet train = FixedProblems.get2DLinearRegression(500, RandomUtil.getRandom(1));
        RegressionDataSet test = FixedProblems.get2DLinearRegression(100, RandomUtil.getRandom(1));

        RegressionModelEvaluation rme = new RegressionModelEvaluation(instance, train);
        rme.evaluateTestSet(test);

        assertTrue(rme.getMeanError() <= test.getTargetValues().mean() * 0.25);

    }

    @Test
    public void testTrainC_RegressionDataSet_ExecutorService()
    {
        System.out.println("train");

        StochasticGradientBoosting instance = new StochasticGradientBoosting(new DecisionTree(), 50);

        RegressionDataSet train = FixedProblems.get2DLinearRegression(500, RandomUtil.getRandom(1));
        RegressionDataSet test = FixedProblems.get2DLinearRegression(100, RandomUtil.getRandom(1));

        RegressionModelEvaluation rme = new RegressionModelEvaluation(instance, train, true);
        rme.evaluateTestSet(test);

        assertTrue(rme.getMeanError() <= test.getTargetValues().mean() * 0.25);
    }
    
    @Test
    public void testClone()
    {
        System.out.println("clone");

        StochasticGradientBoosting instance = new StochasticGradientBoosting(new DecisionTree(), 50);

        RegressionDataSet t1 = FixedProblems.get2DLinearRegression(500, RandomUtil.getRandom(1));
        RegressionDataSet t2 = FixedProblems.get2DLinearRegression(100, RandomUtil.getRandom(1));
        t2.applyTransform(new LinearTransform(t2, 1, 10));

        instance = instance.clone();

        instance.train(t1);

        StochasticGradientBoosting result = instance.clone();
        /*
        for (int i = 0; i < t1.size(); i++)
            assertEquals(t1.getTargetValue(i), result.regress(t1.getDataPoint(i)), t1.getTargetValues().mean()*0.5);
        result.train(t2);

        for (int i = 0; i < t1.size(); i++)
            assertEquals(t1.getTargetValue(i), instance.regress(t1.getDataPoint(i)), t1.getTargetValues().mean()*0.5);

        for (int i = 0; i < t2.size(); i++)
            assertEquals(t2.getTargetValue(i), result.regress(t2.getDataPoint(i)), t2.getTargetValues().mean()*0.5);
        */
    }
    
}
