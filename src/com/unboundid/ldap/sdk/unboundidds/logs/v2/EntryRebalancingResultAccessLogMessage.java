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
package com.unboundid.ldap.sdk.unboundidds.logs.v2;



import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.util.NotExtensible;
import com.unboundid.util.Nullable;
import com.unboundid.util.ThreadSafety;
import com.unboundid.util.ThreadSafetyLevel;



/**
 * This class provides a data structure that holds information about an entry
 * rebalancing result access log message.
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
@ThreadSafety(level=ThreadSafetyLevel.INTERFACE_THREADSAFE)
public interface EntryRebalancingResultAccessLogMessage
       extends EntryRebalancingRequestAccessLogMessage
{
  /**
   * Retrieves the result code for the entry-rebalancing operation.
   *
   * @return  The result code for the entry-rebalancing operation, or
   *          {@code null} if it is not included in the log message.
   */
  @Nullable()
  ResultCode getResultCode();



  /**
   * Retrieves a message with information about any errors that were encountered
   * during processing.
   *
   * @return  A message with information about any errors that were encountered
   *          during processing, or {@code null} if no errors were encountered
   *          or it is not included in the log message.
   */
  @Nullable()
  String getErrorMessage();



  /**
   * Retrieves a message with information about any administrative action that
   * may be required to bring the source and target servers back to a consistent
   * state with regard to the migrated subtree.
   *
   * @return  A message with information about any administrative action that
   *          may be required to bring the source and target servers back to a
   *          consistent state with regard to the migrated subtree, or
   *          {@code null} if no administrative action is required or it is not
   *          included in the log message.
   */
  @Nullable()
  String getAdminActionMessage();



  /**
   * Indicates whether data in the source server was altered as a result of
   * processing for this entry-rebalancing operation.
   *
   * @return  {@code true} if data in the source server was altered as a result
   *          of processing for this entry-rebalancing operation, {@code false}
   *          if no data in the source server was altered as a result of
   *          entry-rebalancing processing, or {@code null} if it is not
   *          included in the log message.
   */
  @Nullable()
  Boolean getSourceServerAltered();



  /**
   * Indicates whether data in the target server was altered as a result of
   * processing for this entry-rebalancing operation.
   *
   * @return  {@code true} if data in the target server was altered as a result
   *          of processing for this entry-rebalancing operation, {@code false}
   *          if no data in the target server was altered as a result of
   *          entry-rebalancing processing, or {@code null} if it is not
   *          included in the log message.
   */
  @Nullable()
  Boolean getTargetServerAltered();



  /**
   * Retrieves the number of entries that were read from the source server.
   *
   * @return  The number of entries that were read from the source server, or
   *          {@code null} if it is not included in the log message.
   */
  @Nullable()
  Integer getEntriesReadFromSource();



  /**
   * Retrieves the number of entries that were added to the target server.
   *
   * @return  The number of entries that were added to the target server, or
   *          {@code null} if it is not included in the log message.
   */
  @Nullable()
  Integer getEntriesAddedToTarget();



  /**
   * Retrieves the number of entries that were deleted from the source server.
   *
   * @return  The number of entries that were deleted from the source server, or
   *          {@code null} if it is not included in the log message.
   */
  @Nullable()
  Integer getEntriesDeletedFromSource();
}
