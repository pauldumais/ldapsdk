/*
 * Copyright 2022 Ping Identity Corporation
 * All Rights Reserved.
 */
/*
 * Copyright 2022 Ping Identity Corporation
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
 * Copyright (C) 2022 Ping Identity Corporation
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
package com.unboundid.ldap.sdk.unboundidds.logs.v2.json;



import java.util.List;

import com.unboundid.ldap.sdk.DereferencePolicy;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.unboundidds.logs.AccessLogOperationType;
import com.unboundid.ldap.sdk.unboundidds.logs.LogException;
import com.unboundid.ldap.sdk.unboundidds.logs.v2.SearchRequestAccessLogMessage;
import com.unboundid.util.NotExtensible;
import com.unboundid.util.NotMutable;
import com.unboundid.util.NotNull;
import com.unboundid.util.Nullable;
import com.unboundid.util.ThreadSafety;
import com.unboundid.util.ThreadSafetyLevel;
import com.unboundid.util.json.JSONObject;



/**
 * This class provides a data structure that holds information about a
 * JSON-formatted search request access log message.
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
 */
@NotExtensible()
@NotMutable()
@ThreadSafety(level=ThreadSafetyLevel.COMPLETELY_THREADSAFE)
public class JSONSearchRequestAccessLogMessage
       extends JSONRequestAccessLogMessage
       implements SearchRequestAccessLogMessage
{
  /**
   * The serial version UID for this serializable class.
   */
  private static final long serialVersionUID = -913390265725394880L;



  // The types only flag for the search request.
  @Nullable private final Boolean typesOnly;

  // The alias dereferencing policy for the search request.
  @Nullable private final DereferencePolicy dereferencePolicy;

  // The size limit for the search request.
  @Nullable private final Integer sizeLimit;

  // The time limit for the search request.
  @Nullable private final Integer timeLimit;

  // The list of requested attributes for the search request.
  @NotNull private final List<String> requestedAttributes;

  // The scope for the search request.
  @Nullable private final SearchScope scope;

  // The base DN for ths search request.
  @Nullable private final String baseDN;

  // The filter for ths search request.
  @Nullable private final String filter;



  /**
   * Creates a new JSON search request access log message from the provided
   * JSON object.
   *
   * @param  jsonObject  The JSON object that contains an encoded representation
   *                     of this log message.  It must not be {@code null}.
   *
   * @throws  LogException  If the provided JSON object cannot be parsed as a
   *                        valid log message.
   */
  public JSONSearchRequestAccessLogMessage(
              @NotNull final JSONObject jsonObject)
         throws LogException
  {
    super(jsonObject);

    baseDN = getString(JSONFormattedAccessLogFields.SEARCH_BASE_DN);
    filter = getString(JSONFormattedAccessLogFields.SEARCH_FILTER);
    sizeLimit =
         getIntegerNoThrow(JSONFormattedAccessLogFields.SEARCH_SIZE_LIMIT);
    timeLimit = getIntegerNoThrow(
         JSONFormattedAccessLogFields.SEARCH_TIME_LIMIT_SECONDS);
    typesOnly = getBooleanNoThrow(
         JSONFormattedAccessLogFields.SEARCH_TYPES_ONLY);
    requestedAttributes = getStringList(
         JSONFormattedAccessLogFields.SEARCH_REQUESTED_ATTRIBUTES);


    final Integer scopeValue = getIntegerNoThrow(
         JSONFormattedAccessLogFields.SEARCH_SCOPE_VALUE);
    if (scopeValue == null)
    {
      scope = null;
    }
    else
    {
      scope = SearchScope.valueOf(scopeValue);
    }


    final String derefStr =
         getString(JSONFormattedAccessLogFields.SEARCH_DEREF_POLICY);
    if (derefStr == null)
    {
      dereferencePolicy = null;
    }
    else
    {
      DereferencePolicy policy = null;
      for (final DereferencePolicy p : DereferencePolicy.values())
      {
        if (p.getName().equalsIgnoreCase(derefStr))
        {
          policy = p;
          break;
        }
      }

      dereferencePolicy = policy;
    }
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  @NotNull()
  public final AccessLogOperationType getOperationType()
  {
    return AccessLogOperationType.SEARCH;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  @Nullable()
  public final String getBaseDN()
  {
    return baseDN;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  @Nullable()
  public final SearchScope getScope()
  {
    return scope;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  @Nullable()
  public final String getFilter()
  {
    return filter;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  @Nullable()
  public final DereferencePolicy getDereferencePolicy()
  {
    return dereferencePolicy;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  @Nullable()
  public final Integer getSizeLimit()
  {
    return sizeLimit;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  @Nullable()
  public final Integer getTimeLimitSeconds()
  {
    return timeLimit;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  @Nullable()
  public final Boolean getTypesOnly()
  {
    return typesOnly;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  @NotNull()
  public final List<String> getRequestedAttributes()
  {
    return requestedAttributes;
  }
}
