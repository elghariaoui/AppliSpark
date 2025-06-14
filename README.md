# AppliSpark 

# Description
AppliSpark est une application Java utilisant Apache Spark pour traiter des données de ventes. Elle est conçue pour analyser les ventes d'une entreprise à partir d'un fichier texte et produire des résultats agrégés par ville et par année.

1. application Spark permettant, à partir d’un
   fichier texte (ventes.txt) en entré, contenant les ventes d’une entreprise dans
   les différentes villes, de déterminer le total des ventes par ville. La structure
   du fichier ventes.txt est de la forme suivante :
   date ville produit prix
2. calcule du  prix total
   des ventes des produits par ville et par année.

# Résultat de  l'application

<table>
  <tr>
    <th>Programme</th>
    <th>Résultat</th>
  </tr>
  <tr>
    <td>Main.java (Spark en mode local)</td>
    <td><img src="src/main/resources/Captures/Capture_resultat_main.png"></td>
  </tr>
  <tr>
    <td>Main2.java (Spark en mode local)</td>
    <td><img src="src/main/resources/Captures/Capture_resultat_main2.png"></td>
  </tr>
   <tr>
    <td>Main3.java (Spark en mode cluster local)</td>
    <td>
        Exécution de l'application en mode local avec Spark :
        <img src="src/main/resources/Captures/Capture_spark_local_3.png">
       Résultat de l'application en mode cluster local avec Spark :
       <img src="src/main/resources/Captures/Capture_resultat_cluster_spark_local.png">
    </td>
   </tr>
    <tr>
        <td>Main3.java (Spark en mode cluster HDFS)</td>
        <td>
            Exécution de l'application en mode cluster avec HDFS :
            <img src="src/main/resources/Captures/Capture_spark_HDFS_3.png">
         Résultat de l'application en mode cluster avec HDFS :
         <img src="src/main/resources/Captures/Capture_spark_HDFS_5.png">
        </td>
    </tr>
</table>

# Prérequis
- Java 8 ou supérieur (11 recommandé)
- Apache Spark 3.0 ou supérieur
- Maven pour la gestion des dépendances
- Un cluster Spark pour l'exécution en mode cluster (optionnel)
- Un fichier `ventes.txt` avec le format spécifié
- Un ficher `context.txt` pour définir le chemin du fichier à traiter par l'application Spark (Main3.java)


# Installation Spark en mode local avec Docker
1. Clonez le dépôt :
   ```bash
   git clone

2. Lancement du cluster Spark local avec Docker :
   ```bash
   docker-compose up -d
   ```
   <img src="src/main/resources/Captures/Capture_Docker_Cluster_Spark_local.png">

3. Packager le projet avec Maven :
   ```bash
   cd AppliSpark
   mvn package
   ```
4. Exécutez l'application :
   - Pour exécuter en mode local de Main.java :
     ```bash
     java -cp target/AppliSpark-1.0-SNAPSHOT.jar com.example.Main
     ```
   - Pour exécuter en mode local de Main2.java :
     ```bash
     java -cp target/AppliSpark-1.0-SNAPSHOT.jar com.example.Main2
     ```
   - Pour exécuter en mode cluster local :
     ```bash
        Copier les fichiers context.txt, ventes.txt et appli_spark-1.0-SNAPSHOT-jar-with-dependencies.jar dans le master et les workers Spark :
        Se connecter au master Spark :
        * docker exec -it -u root spark-master bash
        Exécutez la commande suivante sur le master :
        * spark-submit --master spark://spark-master:7077 --deploy-mode cluster appli_spark-1.0-SNAPSHOT-jar-with-dependencies.jar
     ```
     <img src="src/main/resources/Captures/Capture_spark_local_1.png">
     <img src="src/main/resources/Captures/Capture_spark_local_2.png">

# Installation Spark en mode cluster et HDFS avec Docker 
1. Clonez le dépôt :
   ```bash
   git clone

2. Lancement du cluster Spark local avec Docker :
   ```bash
   docker-compose up -d
   ```
   <img src="src/main/resources/Captures/Capture_spark_HDFS_6.png">

3. Packager le projet avec Maven :
   ```bash
   cd AppliSpark
   mvn package
   ```

4. Copier les fichiers  ventes.txt et appli_spark-1.0-SNAPSHOT-jar-with-dependencies.jar dans docker puis HDFS :
   
    - Copiez les fichiers `ventes.txt` et `appli_spark-1.0-SNAPSHOT-jar-with-dependencies.jar` dans le répertoire de votre choix sur le datanode :
      ```bash
      docker cp ventes.txt cluster_spark_with_hdfs-namenode-1:/opt/haddop
      docker cp appli_spark-1.0-SNAPSHOT-jar-with-dependencies.jar cluster_spark_with_hdfs-namenode-1:/opt/haddop
      ```
   - Exécutez la commande suivante sur le datanode :
     ```bash
     hdfs dfs -mkdir -p /app
     hdfs dfs -put /opt/hadoop/ventes.txt /app/ventes.txt
     hdfs dfs -put /opt/hadoop/appli_spark-1.0-SNAPSHOT-jar-with-dependencies.jar /app/appli_spark-1.0-SNAPSHOT-jar-with-dependencies.jar
     ```
   - Copier le fichier context.txt sur le spark-master et les workers Spark:
     ```bash
     docker cp context.txt spark-master:/opt/bitnami/spark
     docker cp context.txt spark-worker-1:/opt/bitnami/spark
     docker cp context.txt spark-worker-2:/opt/bitnami/spark
     docker cp context.txt spark-worker-3:/opt/bitnami/spark
     ```
5. Exécutez l'application :
    - Pour exécuter en mode cluster :
      ```bash
      docker exec -it -u root spark-master bash
      spark-submit --master spark://spark-master:7077 --deploy-mode cluster hdfs://namenode:8020/app/appli_spark-1.0-SNAPSHOT-jar-with-dependencies.jar
      ```
      <img src="src/main/resources/Captures/Capture_spark_HDFS_1.png">
      
      <img src="src/main/resources/Captures/Capture_spark_HDFS_3.png">
          
## Note
1. Exécutez l'application en mode local ou en mode cluster selon vos besoins.:
    - Pour exécuter l'application en mode cluster local. se connecter au master Spark :
      ```bash
      docker exec -it -u root spark-master bash
      vi context.txt
      /opt/bitnami/spark/ventes.txt
      #hdfs://namenode:8020/app/ventes.txt
      spark-submit --master spark://spark-master:7077 --deploy-mode cluster hdfs://namenode:8020/app/appli_spark-1.0-SNAPSHOT-jar-with-dependencies.jar
      ```
    - Pour exécuter en mode cluster HDFS. Se connecter au master Spark :
      ```bash
      docker exec -it -u root spark-master bash
      vi context.txt
      hdfs://namenode:8020/app/ventes.txt
      #/opt/bitnami/spark/ventes.txt
      spark-submit --master spark://spark-master:7077 --deploy-mode cluster hdfs://namenode:8020/app/appli_spark-1.0-SNAPSHOT-jar-with-dependencies.jar
      ```  