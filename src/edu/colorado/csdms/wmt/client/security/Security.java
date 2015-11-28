/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 mcflugen
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package edu.colorado.csdms.wmt.client.security;

import edu.colorado.csdms.wmt.client.control.DataManager;
import edu.colorado.csdms.wmt.client.ui.handler.AuthenticationHandler;

/**
 * A class for working with usernames and passwords in WMT.
 * 
 * @author Mark Piper (mark.piper@colorado.edu)
 */
public class Security {

  private AuthenticationHandler authHandler;  
  private String hpccHostname;
  private String hpccUsername; // for the HPCC where the model is run
  private String hpccPassword;
  private String wmtUsername;  // for logging into WMT
  private String wmtPassword;
  private Boolean isLoggedIn = false;
  
  /**
   * Initializes the Security object used in a WMT session.
   * 
   * @param data the DataManager object for the WMT session
   */
  public Security(DataManager data) {
    this.authHandler = new AuthenticationHandler(data);
  }
  
  public AuthenticationHandler getAuthenticationHandler() {
    return authHandler;
  }

  public void setAuthenticationHandler(AuthenticationHandler authHandler) {
    this.authHandler = authHandler;
  }

  /**
   * Returns the hostname of the machine where the user wants the model to be
   * run.
   */
  public String getHpccHostname() {
    return hpccHostname;
  }

  /**
   * Stores the hostname of the machine where the user wants the model to be
   * run.
   * 
   * @param hostname the hostname
   */
  public void setHpccHostname(String hostname) {
    this.hpccHostname = hostname;
  }

  /**
   * Returns the user's username for the host on which the model is to be run.
   */
  public String getHpccUsername() {
    return hpccUsername;
  }

  /**
   * Stores the user's username for the host on which the model is to be run.
   * 
   * @param username the username
   */
  public void setHpccUsername(String username) {
    this.hpccUsername = username;
  }

  /**
   * Returns the user's password for the host on which the model is to be run.
   */
  public String getHpccPassword() {
    return hpccPassword;
  }

  /**
   * Stores the user's password for the host on which the model is to be run.
   * 
   * @param password the user's password
   */
  public void setHpccPassword(String password) {
    this.hpccPassword = password;
  }

  /**
   * Returns the user's login name for the WMT client.
   */
  public String getWmtUsername() {
    return wmtUsername;
  }

  /**
   * Stores the user's login name for the WMT client.
   * 
   * @param wmtUsername the user's login name
   */
  public void setWmtUsername(String wmtUsername) {
    this.wmtUsername = wmtUsername;
  }

  /**
   * Returns the user's password for the WMT client.
   */
  public String getWmtPassword() {
    return wmtPassword;
  }

  /**
   * Stores the user's password for the WMT client.
   * 
   * @param wmtPassword the password
   */
  public void setWmtPassword(String wmtPassword) {
    this.wmtPassword = wmtPassword;
  }

  /**
   * Is the user currently logged into WMT?
   */
  public Boolean isLoggedIn() {
    return isLoggedIn;
  }

  /**
   * Sets whether the user is currently logged into WMT.
   * 
   * @param isLoggedIn true if user is logged in
   */
  public void isLoggedIn(Boolean isLoggedIn) {
    this.isLoggedIn = isLoggedIn;
  }
}
