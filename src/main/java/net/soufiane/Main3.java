package net.soufiane;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;


//
//@author Soufiane Elghariaoui

/*

application Spark permettant, à partir d’un
fichier texte (ventes.txt) en entré, contenant les ventes d’une entreprise dans
les différentes villes, de déterminer le total des ventes par ville. La structure
du fichier ventes.txt est de la forme suivante
date ville produit prix
 */
public class Main3 {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Ventes");
        JavaSparkContext sc = new JavaSparkContext(conf);
        //load file ventes.txt to RDD
        JavaRDD<String> rddLines = sc.textFile("src/main/resources/ventes.txt");

        // Calcul du total des ventes par ville
        JavaRDD<String> rddTotalVentes = rddLines
                .map(line -> line.split(";"))
                .mapToPair(parts -> new Tuple2<>(parts[1], Double.parseDouble(parts[3])))
                .reduceByKey(Double::sum)
                .map(tuple -> tuple._1 + " " + tuple._2);
        //collect and print
        rddTotalVentes.collect().forEach(System.out::println);
        //close the context
        sc.close();






    }
}