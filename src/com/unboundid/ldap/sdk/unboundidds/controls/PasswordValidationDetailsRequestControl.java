/*
 * Copyright 2015-2022 Ping Identity Corporation
 * All Rights Reserved.
 */
/*
 * Copyright 2015-2022 Ping Identity Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Copyright (C) 2015-2022 Ping Identity Corporation
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
package com.unboundid.ldap.sdk.unboundidds.controls;



import com.unboundid.ldap.sdk.Control;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.util.NotMutable;
import com.unboundid.util.NotNull;
import com.unboundid.util.ThreadSafety;
import com.unboundid.util.ThreadSafetyLevel;

import static com.unboundid.ldap.sdk.unboundidds.controls.ControlMessages.*;



/**
 * This class provides an implementation for a request control that can be
 * included in an add, modify, or password modify request.  Its presence in one
 * of those requests will indicate that the server should provide a response
 * control with information about the password quality requirements that are in
 * effect for the operation and information about whether the password included
 * in the request satisfies each of those requirements.
 * <BR>
 * <BLOCKQUOTE>
 *   <B>NOTE:</B>  This class, and other classes within the
 *   {@code com.unboundid.ldap.sdk.unboundidds} package structure, are only
 *   supported for use against Ping Identity, UnboundID, and
 *   Nokia/Alcatel-Lucent 8661 server products.  These classes provide support
 *   for proprietary functionality or for external specifications that are not
 *   considered stable or mature enough to be guaranteed to work in an
 *   interoperable way with other types of LDAP servers.
 * </BLOCKQUOTE>
 * <BR>
 * This request control has an OID of 1.3.6.1.4.1.30221.2.5.40, and it is
 * recommended that the criticality be {@code false}.  It does not have a value.
 */
@NotMutable()
@ThreadSafety(level=ThreadSafetyLevel.COMPLETELY_THREADSAFE)
public final class PasswordValidationDetailsRequestControl
       extends Control
{
 /**
  * The OID (1.3.6.1.4.1.30221.2.5.40) for the password validation details
  * request control.
  */
 @NotNull public static final String PASSWORD_VALIDATION_DETAILS_REQUEST_OID =
      "1.3.6.1.4.1.30221.2.5.40";



 /**
  * The serial version UID for this serializable class.
  */
 private static final long serialVersionUID = -956099348044171899L;



  /**
   * Creates a new password validation details request control with
   * a criticality of {@code false}.
   */
  public PasswordValidationDetailsRequestControl()
  {
    this(false);
  }



  /**
   * Creates a new password validation details request control with the
   * specified criticality.
   *
   * @param  isCritical  Indicates whether this control should be considered
   *                     critical.
   */
  public PasswordValidationDetailsRequestControl(final boolean isCritical)
  {
    super(PASSWORD_VALIDATION_DETAILS_REQUEST_OID, isCritical, null);
  }



  /**
   * Creates a new password validation details request control which is decoded
   * from the provided generic control.
   *
   * @param  control  The generic control to be decoded as a password validation
   *                  details request control.
   *
   * @throws  LDAPException  If the provided control cannot be decoded as a
   *                         password validation details request control.
   */
  public PasswordValidationDetailsRequestControl(
              @NotNull final Control control)
         throws LDAPException
  {
    super(control);

    if (control.hasValue())
    {
      throw new LDAPException(ResultCode.DECODING_ERROR,
                              ERR_PW_VALIDATION_REQUEST_HAS_VALUE.get());
    }
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  @NotNull()
  public String getControlName()
  {
    return INFO_CONTROL_NAME_PW_VALIDATION_REQUEST.get();
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public void toString(@NotNull final StringBuilder buffer)
  {
    buffer.append("PasswordValidationDetailsRequestControl(isCritical=");
    buffer.append(isCritical());
    buffer.append(')');
  }
}
