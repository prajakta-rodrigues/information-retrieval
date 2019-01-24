package com.information.retrieval.system.crawler.tasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.information.retrieval.system.crawler.domains.Link;
import com.information.retrieval.system.crawler.domains.WebServer;
import com.information.retrieval.system.crawler.util.Utility;



/**
 * The Class BasicCrawling provides methods for crawling the web and saving corresponding html
 * files.
 */
public class BasicCrawling {

  private PrintWriter writer = null;

  /** The list of visited servers. */
  private List<WebServer> serverVisited;

  /**
   * Crawls the web starting from a seed.
   *
   * @param seedUrl the seed url to start crawling
   * @param fileName the file name to save the crawled urls of webpages
   * @param prefixServer the prefix server that is to be followed
   * @param maxUrlLimit the max url limit to be crawled
   * @param maxDepth the max depth to be achieved while crawling
   * @return the list of the crawled urls
   */
  public List<Link> crawl(String seedUrl, String fileName, String prefixServer, int maxUrlLimit,
      int maxDepth) {
    LinkedList<Link> frontierList = new LinkedList<>();
    serverVisited = new ArrayList<>();
    int parentDepth = 0;

    try {
      writer = new PrintWriter(fileName, "UTF-8");
      frontierList.add(new Link(seedUrl, "Carbon_footprint", parentDepth + 1, parentDepth));
      writer.println(seedUrl + " " + (parentDepth + 1));
      while (frontierList.size() <= maxUrlLimit) {
        Link link = frontierList.poll();
        if (link.getDepth() > maxDepth) {
          writer.flush();
          writer.close();
          return frontierList;
        }

        if (!Utility.keepTrackOfPoliteness(Utility.getHostName(link.getUrl()), serverVisited)) {
          TimeUnit.SECONDS.sleep(2);
        }

        Document doc = (Jsoup.connect(link.getUrl())).get();
        this.addOrUpdateList(Utility.getHostName(link.getUrl()));
        Elements links = doc.select("a[href]");
        String temp = null;
        for (Element linkInPage : links) {
          temp = linkInPage.attr("abs:href");
          if (frontierList.size() >= maxUrlLimit - 1) {
            writer.flush();
            writer.close();
            return frontierList;
          }
          if (temp.startsWith(prefixServer) && !Utility.isAdministrativeLink(temp)
              && !Utility.isSectionOfSamePage(temp) && Utility.checkIfPresent(temp, frontierList)) {
              frontierList.add(new Link(temp, Utility.generateHash(linkInPage.text()),
                  link.getDepth() + 1, link.getDepth()));
              writer.println(temp + " " + (link.getDepth() + 1));
            
          }

        }

      }
    } catch (Exception exception) {

    } finally {
      writer.flush();
      writer.close();
    }

    return frontierList;

  }

  /**
   * Adds the or update list of visited servers
   *
   * @param hostName the host name to be added to the visited server list
   */
  private void addOrUpdateList(String hostName) {
    for (WebServer webServer : this.serverVisited) {
      if (hostName.equals(webServer.getName())) {
        webServer.setLastVisitedTime(new Timestamp(new Date().getTime()));
        return;
      }
    }
    this.serverVisited.add(new WebServer(hostName, new Timestamp(new Date().getTime())));
  }



  /**
   * Save web pages as html files from given list of links.
   *
   * @param directoryName the directory name where the files are to be stored
   * @param frontierList the frontier list which contains the list of links whose 
   * web pages are to be saved
   * @return true, if successful
   */
  public boolean saveHtmlFiles(String directoryName, List<Link> frontierList) {
    Document doc = null;
    try {

      for (Link link : frontierList) {
        if (!Utility.keepTrackOfPoliteness(Utility.getHostName(link.getUrl()), serverVisited)) {
          TimeUnit.SECONDS.sleep(2);
        }
        doc = (Jsoup.connect(link.getUrl())).timeout(0).get();
        writer = new PrintWriter(directoryName + link.getHash() + ".html", "UTF-8");
        writer.println(doc.html());
        

      }
    } catch (InterruptedException interruptedException) {
      return false;

    } catch (IOException iOException) {
      
      return false;
    } catch (Exception exception) {
      return false;
    }
    finally {
      writer.flush();
      writer.close();
    }
    return true;

  }

}
