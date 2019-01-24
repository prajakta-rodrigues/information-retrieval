package com.information.retrieval.system.crawler.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import com.information.retrieval.system.crawler.constants.Constants;
import com.information.retrieval.system.crawler.domains.Link;
import com.information.retrieval.system.crawler.domains.WebServer;
import com.information.retrieval.system.crawler.exception.InformationRetrievalException;


/**
 * The Class Utility gives general purpose methods.
 */
public final class Utility {

  /** The Constant defining the number of seconds in a minute */
  public static final int SECONDS_IN_A_MINUTE = 60;

  /** The Constant HASH. */
  private static final CharSequence HASH = "#";

  /**
   * This constructor is created to prevent instantiation.
   */
  private Utility() {
    // not called
  }

  /**
   * Checks if the given url is present in given list of Link type.
   *
   * @param urlToBeChecked the url to be checked
   * @param listToBeCheckedIn the list of Link type to be checked in
   * @return true, if url exists in given list of Link type
   */
  public static boolean checkIfPresent(String urlToBeChecked, List<Link> listToBeCheckedIn) {
    for (Link link : listToBeCheckedIn) {
      if (urlToBeChecked.equals(link.getUrl())) {
        return false;
      }

    }
    return true;
  }

  /**
   * Checks if url is section of same page.
   *
   * @param url the url to be checked
   * @return true, if is section of same page
   */
  public static boolean isSectionOfSamePage(String url) {
    return url.contains(HASH);
  }

  /**
   * Keep track of politeness by checking if the minimum time gap requirement between
   * two subsequent calls to the same server is not violated.
   *
   * @param hostName the host name which is to be visited
   * @param hostVisited the list of Web Servers which are already visited
   * @return true, if previous call to the server was more than defined minutes ago
   */
  public static boolean keepTrackOfPoliteness(String hostName, List<WebServer> hostVisited) {

    double difference = 0.0f;
    if (hostVisited == null || hostVisited.isEmpty())
      return true;
    for (WebServer webServer : hostVisited) {
      if (webServer.getName().equals(hostName)) {
        Date date = new Date();
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        difference = (webServer.getLastVisitedTime()).getSeconds() - currentTimestamp.getSeconds();
        break;
      } else {
        return true;
      }
    }
    return difference / (SECONDS_IN_A_MINUTE) > Integer
        .parseInt(Constants.POLITENESS_TIME_IN_SECONDS.getValue());

  }

  /**
   * Gets the host name from the url.
   *
   * @param url the url
   * @return the host name of the input url
   * @throws MalformedURLException the malformed URL exception if URL is not valid
   */
  public static String getHostName(String url) throws MalformedURLException {
    URL newUrl = new URL(url);
    return newUrl.getHost();

  }


  /**
   * Checks if given url is a administrative link.
   *
   * @param url the url
   * @return true, if is administrative link
   */
  public static boolean isAdministrativeLink(String url) {
    url = url.replaceFirst("http://", "");
    url = url.replaceFirst("https://", "");
    CharSequence charSequence = Constants.Colon.getValue();
    if (url.contains(charSequence)) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Generates hash using MD5 message digest.
   *
   * @param input the input string for which hash is to be generated
   * @return the generated hash
   * @throws InformationRetrievalException if there is error while processing request
   */
  public static String generateHash(String input) throws InformationRetrievalException {
    try {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(input.getBytes());
    return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
    }
    catch(NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new InformationRetrievalException("Invalid Algorithm Name");
    }
  }


  /**
   * Checks if given string has valid extension.
   *
   * @param inputString the input string
   * @param extention to be checked against
   * @return true, if string has given extension
   */
  public static boolean checkIfValidExtension(String inputString, String extention) {
    return inputString.endsWith(extention);
  }

  /**
   * Check if given url is a valid url.
   *
   * @param url the url to be checked
   * @return true, if input is a valid url
   */
  public static boolean checkIfValidUrl(String url) {
    return url.matches("^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9._]+)"
        + ".[a-zA-Z0-9]*.[a-z]{3}.[a-zA-Z0-9]*");
  }

  /**
   * Check if given location is exists on disk.
   *
   * @param fileDirectory the file directory to be checked
   * @return true, if valid location
   */
  public static boolean checkIfValidFileLocation(String fileDirectory) {
    try {
      new File(fileDirectory);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
