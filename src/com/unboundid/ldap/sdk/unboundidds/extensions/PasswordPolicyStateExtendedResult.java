/*
 * Copyright 2008-2017 UnboundID Corp.
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



import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.unboundid.asn1.ASN1Element;
import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.asn1.ASN1Sequence;
import com.unboundid.ldap.sdk.Control;
import com.unboundid.ldap.sdk.ExtendedResult;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.util.NotMutable;
import com.unboundid.util.ThreadSafety;
import com.unboundid.util.ThreadSafetyLevel;

import static com.unboundid.ldap.sdk.unboundidds.extensions.ExtOpMessages.*;
import static com.unboundid.util.Debug.*;



/**
 * This class implements a data structure for storing the information from an
 * extended result for the password policy state extended request as used in the
 * Ping Identity, UnboundID, or Alcatel-Lucent 8661 Directory Server.  It is
 * able to decode a generic extended result to obtain the user DN and
 * operations.  See the documentation in the
 * {@link PasswordPolicyStateExtendedRequest} class for an example that
 * demonstrates the use of the password policy state extended operation.
 * <BR>
 * <BLOCKQUOTE>
 *   <B>NOTE:</B>  This class is part of the Commercial Edition of the UnboundID
 *   LDAP SDK for Java.  It is not available for use in applications that
 *   include only the Standard Edition of the LDAP SDK, and is not supported for
 *   use in conjunction with non-UnboundID products.
 * </BLOCKQUOTE>
 */
@NotMutable()
@ThreadSafety(level=ThreadSafetyLevel.COMPLETELY_THREADSAFE)
public final class PasswordPolicyStateExtendedResult
       extends ExtendedResult
{
  /**
   * The serial version UID for this serializable class.
   */
  private static final long serialVersionUID = 7140468768443263344L;



  // A map containing all of the response operations, indexed by operation type.
  private final Map<Integer,PasswordPolicyStateOperation> operations;

  // The user DN from the response.
  private final String userDN;



  /**
   * Creates a new password policy state extended result from the provided
   * extended result.
   *
   * @param  extendedResult  The extended result to be decoded as a password
   *                         policy state extended result.  It must not be
   *                         {@code null}.
   *
   * @throws  LDAPException  If the provided extended result cannot be decoded
   *                         as a password policy state extended result.
   */
  public PasswordPolicyStateExtendedResult(final ExtendedResult extendedResult)
         throws LDAPException
  {
    super(extendedResult);

    final ASN1OctetString value = extendedResult.getValue();
    if (value == null)
    {
      userDN = null;
      operations = Collections.emptyMap();
      return;
    }

    final ASN1Element[] elements;
    try
    {
      final ASN1Element valueElement = ASN1Element.decode(value.getValue());
      elements = ASN1Sequence.decodeAsSequence(valueElement).elements();
    }
    catch (Exception e)
    {
      debugException(e);
      throw new LDAPException(ResultCode.DECODING_ERROR,
                              ERR_PWP_STATE_RESPONSE_VALUE_NOT_SEQUENCE.get(e),
                              e);
    }

    if ((elements.length < 1) || (elements.length > 2))
    {
      throw new LDAPException(ResultCode.DECODING_ERROR,
                              ERR_PWP_STATE_RESPONSE_INVALID_ELEMENT_COUNT.get(
                                   elements.length));
    }

    userDN = ASN1OctetString.decodeAsOctetString(elements[0]).stringValue();

    final LinkedHashMap<Integer,PasswordPolicyStateOperation> ops =
         new LinkedHashMap<Integer,PasswordPolicyStateOperation>();
    if (elements.length == 2)
    {
      try
      {
        final ASN1Element[] opElements =
             ASN1Sequence.decodeAsSequence(elements[1]).elements();
        for (final ASN1Element e : opElements)
        {
          final PasswordPolicyStateOperation op =
               PasswordPolicyStateOperation.decode(e);
          ops.put(op.getOperationType(), op);
        }
      }
      catch (Exception e)
      {
        debugException(e);
        throw new LDAPException(ResultCode.DECODING_ERROR,
                                ERR_PWP_STATE_RESPONSE_CANNOT_DECODE_OPS.get(e),
                                e);
      }
    }

    operations = Collections.unmodifiableMap(ops);
  }



  /**
   * Creates a new password policy state extended result with the provided
   * information.
   * @param  messageID          The message ID for the LDAP message that is
   *                            associated with this LDAP result.
   * @param  resultCode         The result code from the response.
   * @param  diagnosticMessage  The diagnostic message from the response, if
   *                            available.
   * @param  matchedDN          The matched DN from the response, if available.
   * @param  referralURLs       The set of referral URLs from the response, if
   *                            available.
   * @param  userDN             The user DN from the response.
   * @param  operations         The set of operations from the response, mapped
   *                            from operation type to the corresponding
   *                            operation data.
   * @param  responseControls   The set of controls from the response, if
   *                            available.
   */
  public PasswordPolicyStateExtendedResult(final int messageID,
              final ResultCode resultCode, final String diagnosticMessage,
              final String matchedDN, final String[] referralURLs,
              final String userDN,
              final PasswordPolicyStateOperation[] operations,
              final Control[] responseControls)
  {
    super(messageID, resultCode, diagnosticMessage, matchedDN, referralURLs,
          null, encodeValue(userDN, operations), responseControls);

    this.userDN = userDN;

    if ((operations == null) || (operations.length == 0))
    {
      this.operations = Collections.emptyMap();
    }
    else
    {
      final LinkedHashMap<Integer,PasswordPolicyStateOperation> ops =
           new LinkedHashMap<Integer,PasswordPolicyStateOperation>(
                    operations.length);
      for (final PasswordPolicyStateOperation o : operations)
      {
        ops.put(o.getOperationType(), o);
      }
      this.operations = Collections.unmodifiableMap(ops);
    }
  }



  /**
   * Encodes the provided information into a suitable value for this control.
   *
   * @param  userDN             The user DN from the response.
   * @param  operations         The set of operations from the response, mapped
   *                            from operation type to the corresponding
   *                            operation data.
   *
   * @return  An ASN.1 octet string containing the appropriately-encoded value
   *          for this control, or {@code null} if there should not be a value.
   */
  private static ASN1OctetString encodeValue(final String userDN,
       final PasswordPolicyStateOperation[] operations)
  {
    if ((userDN == null) && ((operations == null) || (operations.length == 0)))
    {
      return null;
    }

    final ArrayList<ASN1Element> elements = new ArrayList<ASN1Element>(2);
    elements.add(new ASN1OctetString(userDN));

    if ((operations != null) && (operations.length > 0))
    {
      final ASN1Element[] opElements = new ASN1Element[operations.length];
      for (int i=0; i < operations.length; i++)
      {
        opElements[i] = operations[i].encode();
      }

      elements.add(new ASN1Sequence(opElements));
    }

    return new ASN1OctetString(new ASN1Sequence(elements).encode());
  }




  /**
   * Retrieves the user DN included in the response.
   *
   * @return  The user DN included in the response, or {@code null} if the user
   *          DN is not available (e.g., if this is an error response).
   */
  public String getUserDN()
  {
    return userDN;
  }



  /**
   * Retrieves the set of password policy operations included in the response.
   *
   * @return  The set of password policy operations included in the response.
   */
  public Iterable<PasswordPolicyStateOperation> getOperations()
  {
    return operations.values();
  }



  /**
   * Retrieves the specified password policy state operation from the response.
   *
   * @param  opType  The operation type for the password policy state operation
   *                 to retrieve.
   *
   * @return  The requested password policy state operation, or {@code null} if
   *          no such operation was included in the response.
   */
  public PasswordPolicyStateOperation getOperation(final int opType)
  {
    return operations.get(opType);
  }



  /**
   * Retrieves the value for the specified password policy state operation as a
   * string.
   *
   * @param  opType  The operation type for the password policy state operation
   *                 to retrieve.
   *
   * @return  The string value of the requested password policy state operation,
   *          or {@code null} if the specified operation was not included in the
   *          response or did not have any values.
   */
  public String getStringValue(final int opType)
  {
    final PasswordPolicyStateOperation op = operations.get(opType);
    if (op == null)
    {
      return null;
    }

    return op.getStringValue();
  }



  /**
   * Retrieves the set of string values for the specified password policy state
   * operation.
   *
   * @param  opType  The operation type for the password policy state operation
   *                 to retrieve.
   *
   * @return  The set of string values for the requested password policy state
   *          operation, or {@code null} if the specified operation was not
   *          included in the response.
   */
  public String[] getStringValues(final int opType)
  {
    final PasswordPolicyStateOperation op = operations.get(opType);
    if (op == null)
    {
      return null;
    }

    return op.getStringValues();
  }



  /**
   * Retrieves the value of the specified password policy state operation as a
   * boolean.
   *
   * @param  opType  The operation type for the password policy state operation
   *                 to retrieve.
   *
   * @return  The boolean value of the requested password policy state
   *          operation.
   *
   * @throws  NoSuchElementException  If the specified operation was not
   *                                  included in the response.
   *
   * @throws  IllegalStateException  If the specified password policy state
   *                                 operation does not have exactly one value,
   *                                 or if the value cannot be parsed as a
   *                                 boolean value.
   */
  public boolean getBooleanValue(final int opType)
         throws NoSuchElementException, IllegalStateException
  {
    final PasswordPolicyStateOperation op = operations.get(opType);
    if (op == null)
    {
      throw new NoSuchElementException(
                     ERR_PWP_STATE_RESPONSE_NO_SUCH_OPERATION.get());
    }

    return op.getBooleanValue();
  }



  /**
   * Retrieves the value of the specified password policy state operation as an
   * integer.
   *
   * @param  opType  The operation type for the password policy state operation
   *                 to retrieve.
   *
   * @return  The integer value of the requested password policy state
   *          operation.
   *
   * @throws  NoSuchElementException  If the specified operation was not
   *                                  included in the response.
   *
   * @throws  IllegalStateException  If the value of the specified password
   *                                 policy state operation cannot be parsed as
   *                                 an integer value.
   */
  public int getIntValue(final int opType)
         throws NoSuchElementException, IllegalStateException
  {
    final PasswordPolicyStateOperation op = operations.get(opType);
    if (op == null)
    {
      throw new NoSuchElementException(
                     ERR_PWP_STATE_RESPONSE_NO_SUCH_OPERATION.get());
    }

    return op.getIntValue();
  }



  /**
   * Retrieves the value for the specified password policy state operation as a
   * {@code Date} in generalized time format.
   *
   * @param  opType  The operation type for the password policy state operation
   *                 to retrieve.
   *
   * @return  The value of the requested password policy state operation as a
   *          {@code Date}, or {@code null} if the specified operation was not
   *          included in the response or did not have any values.
   *
   * @throws  ParseException  If the value cannot be parsed as a date in
   *                          generalized time format.
   */
  public Date getGeneralizedTimeValue(final int opType)
         throws ParseException
  {
    final PasswordPolicyStateOperation op = operations.get(opType);
    if (op == null)
    {
      return null;
    }

    return op.getGeneralizedTimeValue();
  }



  /**
   * Retrieves the set of values for the specified password policy state
   * operation as {@code Date}s in generalized time format.
   *
   * @param  opType  The operation type for the password policy state operation
   *                 to retrieve.
   *
   * @return  The set of values of the requested password policy state operation
   *          as {@code Date}s.
   *
   * @throws  ParseException  If any of the values cannot be parsed as a date in
   *                          generalized time format.
   */
  public Date[] getGeneralizedTimeValues(final int opType)
         throws ParseException
  {
    final PasswordPolicyStateOperation op = operations.get(opType);
    if (op == null)
    {
      return null;
    }

    return op.getGeneralizedTimeValues();
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public String getExtendedResultName()
  {
    return INFO_EXTENDED_RESULT_NAME_PW_POLICY_STATE.get();
  }



  /**
   * Appends a string representation of this extended result to the provided
   * buffer.
   *
   * @param  buffer  The buffer to which a string representation of this
   *                 extended result will be appended.
   */
  @Override()
  public void toString(final StringBuilder buffer)
  {
    buffer.append("PasswordPolicyStateExtendedResult(resultCode=");
    buffer.append(getResultCode());

    final int messageID = getMessageID();
    if (messageID >= 0)
    {
      buffer.append(", messageID=");
      buffer.append(messageID);
    }

    buffer.append(", userDN='");
    buffer.append(userDN);
    buffer.append("', operations={");

    final Iterator<PasswordPolicyStateOperation> iterator =
         operations.values().iterator();
    while (iterator.hasNext())
    {
      iterator.next().toString(buffer);
      if (iterator.hasNext())
      {
        buffer.append(", ");
      }
    }
    buffer.append('}');

    final String diagnosticMessage = getDiagnosticMessage();
    if (diagnosticMessage != null)
    {
      buffer.append(", diagnosticMessage='");
      buffer.append(diagnosticMessage);
      buffer.append('\'');
    }

    final String matchedDN = getMatchedDN();
    if (matchedDN != null)
    {
      buffer.append(", matchedDN='");
      buffer.append(matchedDN);
      buffer.append('\'');
    }

    final String[] referralURLs = getReferralURLs();
    if (referralURLs.length > 0)
    {
      buffer.append(", referralURLs={");
      for (int i=0; i < referralURLs.length; i++)
      {
        if (i > 0)
        {
          buffer.append(", ");
        }

        buffer.append('\'');
        buffer.append(referralURLs[i]);
        buffer.append('\'');
      }
      buffer.append('}');
    }

    final Control[] responseControls = getResponseControls();
    if (responseControls.length > 0)
    {
      buffer.append(", responseControls={");
      for (int i=0; i < responseControls.length; i++)
      {
        if (i > 0)
        {
          buffer.append(", ");
        }

        buffer.append(responseControls[i]);
      }
      buffer.append('}');
    }

    buffer.append(')');
  }
}
