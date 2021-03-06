import com.rsredsq.gradient_descent.GradientDescent;
import com.rsredsq.gradient_descent.LinearRegression;
import com.rsredsq.gradient_descent.Point2D;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class GradientDescentWithLinearRegressionTest {

    private JavaSparkContext jsc;

    @Before
    public void before() {
        SparkConf conf = new SparkConf();
        conf.setAppName("Linear Regression Test").setMaster("local");

        jsc = new JavaSparkContext(conf);
    }

    @Test
    public void linearRegressionTest() {
        List<Point2D> points = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            points.add(new Point2D(i, i));
        }
        GradientDescent gradientDescent = new GradientDescent(jsc, jsc.parallelize(points));

        gradientDescent.setLearningRate(0.0001)
                .setIterationsCount(1000)
                .setRegression(new LinearRegression());

        double[] expectedLineParameters = {1.0, 0.0};

        gradientDescent.run();

        double[] actualLineParameters = gradientDescent.getRegressionParameters();

        assertArrayEquals(expectedLineParameters, actualLineParameters, 0.1);
    }
}


