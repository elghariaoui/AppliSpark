package net.soufiane;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


//
//@author Soufiane Elghariaoui

/*

application Spark en mode cluster permettant, à partir d’un
fichier texte (ventes.txt) en entré, contenant les ventes d’une entreprise dans
les différentes villes, de déterminer le total des ventes par ville. La structure
du fichier ventes.txt est de la forme suivante
date ville produit prix
 */
public class Main3 {
    public static void main(String[] args) throws FileNotFoundException {

        SparkConf conf = new SparkConf().setAppName("Ventes");
        JavaSparkContext sc = new JavaSparkContext(conf);
        //load file ventes.txt to RDD
        Scanner scanner = new Scanner(new File("/opt/bitnami/spark/context.txt"));
        String file = scanner.nextLine();
        JavaRDD<String> rddLines = sc.textFile(file);

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