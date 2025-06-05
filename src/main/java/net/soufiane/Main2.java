package net.soufiane;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

//@author Soufiane Elghariaoui

public class Main2 {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Ventes").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        //load file ventes.txt to RDD
        JavaRDD<String> rddLines = sc.textFile("src/main/resources/ventes.txt");

        // calcul le prix total des ventes des produits par ville et par année.
        JavaRDD<String> rddTotalVentes = rddLines
                .map(line -> line.split(";"))
                .mapToPair(parts -> new Tuple2<>(parts[1] + " " + parts[0].split("/")[2], Double.parseDouble(parts[3])))
                .reduceByKey(Double::sum)
                .map(tuple -> tuple._1 + " " + tuple._2);


        // collect and print the results par ville et par année
        System.out.println("Total des ventes par ville et par année :");
        // collect and print
        rddTotalVentes.collect().forEach(System.out::println);
        // close the context
        sc.close();

    }
}
