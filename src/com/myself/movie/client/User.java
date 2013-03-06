/**
 * 
 */
package com.myself.movie.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author jasonhotsauce
 *
 */
/**
 * @author jasonhotsauce
 *
 */
public class User implements IsSerializable {
  private int userId;
  private String firstName;
  private String lastName;
  
  /**
   * @return the userId
   */
  public int getUserId() {
    return userId;
  }
  /**
   * @param userId the userId to set
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }
  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }
  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }
  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + userId;
    return result;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (userId != other.userId)
      return false;
    return true;
  }
  
}
