/*
 * Copyright 2012-2017 UnboundID Corp.
 * All Rights Reserved.
 */
/*
 * Copyright (C) 2015-2017 UnboundID Corp.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPLv2 only)
 * or the terms of the GNU Lesser General Public License (LGPLv2.1 only)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 */
package com.unboundid.ldap.sdk.unboundidds.extensions;



/**
 * This enum defines the set of possible values for the element of a
 * multi-update result which indicates whether any updates were applied to
 * server data.
 * <BR>
 * <BLOCKQUOTE>
 *   <B>NOTE:</B>  This class is part of the Commercial Edition of the UnboundID
 *   LDAP SDK for Java.  It is not available for use in applications that
 *   include only the Standard Edition of the LDAP SDK, and is not supported for
 *   use in conjunction with non-UnboundID products.
 * </BLOCKQUOTE>
 *
 * @see MultiUpdateExtendedResult
 */
public enum MultiUpdateChangesApplied
{
  /**
   * Indicates that none of the changes contained in the multi-update request
   * were applied to the server.
   */
  NONE(0),



  /**
   * Indicates that all of the changes contained in the multi-update request
   * were applied to the server.
   */
  ALL(1),



  /**
   * Indicates that one or more changes from the multi-update request were
   * applied, but at least one failure was also encountered.
   */
  PARTIAL(2);



  // The integer value associated with this changes applied value.
  private final int intValue;



  /**
   * Creates a new multi-update changes applied value with the provided integer
   * representation.
   *
   * @param  intValue  The integer value associated with this changes applied
   *                   value.
   */
  MultiUpdateChangesApplied(final int intValue)
  {
    this.intValue = intValue;
  }



  /**
   * Retrieves the integer value associated with this changes applied value.
   *
   * @return  The integer value associated with this changes applied value.
   */
  public int intValue()
  {
    return intValue;
  }



  /**
   * Retrieves the multi-update changes applied value with the specified integer
   * value.
   *
   * @param  intValue  The integer value for the changes applied value to
   *                   retrieve.
   *
   * @return  The multi-update changes applied value with the specified integer
   *          value, or {@code null} if there is no changes applied value with
   *          the specified integer value.
   */
  public static MultiUpdateChangesApplied valueOf(final int intValue)
  {
    for (final MultiUpdateChangesApplied v : values())
    {
      if (intValue == v.intValue)
      {
        return v;
      }
    }

    return null;
  }
}
