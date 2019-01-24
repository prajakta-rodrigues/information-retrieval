package com.information.retrieval.system.crawler.domains;

import java.sql.Timestamp;

/**
 * The Class WebServer.
 */
public class WebServer {

  String name;

  Timestamp lastVisitedTime;

  /**
   * Instantiates a new web server.
   *
   * @param name the name of the server
   * @param lastVisitedTime the last time when a page was fetched from the server.
   */
  public WebServer(String name, Timestamp lastVisitedTime) {
    this.name = name;
    this.lastVisitedTime = lastVisitedTime;
  }

  /**
   * Gets the name of the server.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the server.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the last time when a page was fetched from the server.
   *
   * @return the last visited time
   */
  public Timestamp getLastVisitedTime() {
    return lastVisitedTime;
  }

  /**
   * Sets the last time when a page was fetched from the server.
   *
   * @param lastVisitedTime the last visited time
   */
  public void setLastVisitedTime(Timestamp lastVisitedTime) {
    this.lastVisitedTime = lastVisitedTime;
  }



}
