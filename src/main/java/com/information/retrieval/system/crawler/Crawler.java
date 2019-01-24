package com.information.retrieval.system.crawler;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import com.information.retrieval.system.crawler.domains.Link;
import com.information.retrieval.system.crawler.tasks.BasicCrawling;
import com.information.retrieval.system.crawler.util.Utility;

/**
 * This is a crawler that crawls the web using given seed.
 *
 */
public class Crawler {
  public static void main(String[] args) throws SecurityException, IOException {
    String seedUrl = "";
    String prefixServer = "";
    String fileDirectory = "";
    BasicCrawling basicCrawling = null;
    List<Link> result = null;
    Scanner sc = new Scanner(System.in);

    System.out.println(
        "Enter the prefix of the web server to be followed:(eg.https://en.wikipedia.org/wiki)");
    prefixServer = sc.nextLine();

    while (prefixServer.matches("^(http|https)://\r\n")) {
      System.out.println("Invalid prefix for web server to be followed. Enter again");
      prefixServer = sc.nextLine();
    }

    System.out.println("Enter the seed url for crawling(eg.https://en.wikipedia.org/wiki/Carbon):");
    seedUrl = sc.nextLine();

    while (!Utility.checkIfValidUrl(seedUrl)) {
      System.out.println("Invalid url.Enter again:");
      seedUrl = sc.nextLine();
    }

    System.out.println("Enter the max no of urls to be obtained:");
    int maxUrlLimit = sc.nextInt();

    System.out.println("Enter the max depth which is to be crawled:");
    int maxDepth = sc.nextInt();

    // scan the next line after next int
    sc.nextLine();

    System.out.println(
        "Enter the text file path for saving the urls found:" + "eg. C:\\Documents\\file.txt");
    fileDirectory = sc.nextLine();

    while (!Utility.checkIfValidExtension(fileDirectory, ".txt")
        || !Utility.checkIfValidFileLocation(fileDirectory)) {
      System.out.println("Invalid file extension.Enter valid text file location:");
      fileDirectory = sc.nextLine();
    }

    basicCrawling = new BasicCrawling();
    result = basicCrawling.crawl(seedUrl, fileDirectory, prefixServer, maxUrlLimit, maxDepth);

    System.out.println("Do you want to download corresponding html files?(Y/N)");

    if ("Y".equalsIgnoreCase(sc.nextLine())) {
      System.out.println("Enter the file location for saving html files:");
      String htmlFileLocation = sc.nextLine();
      basicCrawling.saveHtmlFiles(htmlFileLocation + "/", result);
      System.out.println("Files downloaded for " + seedUrl);
    }

  }


}
