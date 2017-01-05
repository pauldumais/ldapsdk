/*
 * Copyright 2008-2017 UnboundID Corp.
 * All Rights Reserved.
 */
/*
 * Copyright (C) 2008-2017 UnboundID Corp.
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
package com.unboundid.util;



import java.io.Serializable;



/**
 * This class defines an element that may be used to generate a portion of the
 * string representation of a {@link ValuePattern}.  All value pattern component
 * implementations must be completely threadsafe.
 */
@NotExtensible()
abstract class ValuePatternComponent
         implements Serializable
{
  /**
   * The serial version UID for this serializable class.
   */
  private static final long serialVersionUID = -5740038096026337244L;



  /**
   * Appends a string representation of the next value generated by this
   * component to the provided buffer.
   *
   * @param  buffer  The buffer to which the value should be appended.
   */
  abstract void append(final StringBuilder buffer);



  /**
   * Indicates whether this value pattern component may be targeted by a back
   * reference.
   *
   * @return  {@code true} if this value pattern component may be targeted by a
   *          back reference, or {@code false} if not.
   */
  abstract boolean supportsBackReference();
}
