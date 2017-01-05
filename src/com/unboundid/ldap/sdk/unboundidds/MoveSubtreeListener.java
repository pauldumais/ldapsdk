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
package com.unboundid.ldap.sdk.unboundidds;



import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.ReadOnlyEntry;
import com.unboundid.util.Extensible;
import com.unboundid.util.ThreadSafety;
import com.unboundid.util.ThreadSafetyLevel;



/**
 * This interface defines an API that may be implemented by classes which wish
 * to be notified of processing performed in the course of moving a subtree
 * between servers.
 * <BR>
 * <BLOCKQUOTE>
 *   <B>NOTE:</B>  This class is part of the Commercial Edition of the UnboundID
 *   LDAP SDK for Java.  It is not available for use in applications that
 *   include only the Standard Edition of the LDAP SDK, and is not supported for
 *   use in conjunction with non-UnboundID products.
 * </BLOCKQUOTE>
 */
@Extensible()
@ThreadSafety(level=ThreadSafetyLevel.INTERFACE_NOT_THREADSAFE)
public interface MoveSubtreeListener
{
  /**
   * Performs any processing which may be needed before the provided entry is
   * added to the target server.
   *
   * @param  entry  A read-only representation of the entry to be added to the
   *                target server.
   *
   * @return  The original entry if the add should proceed without changes, a
   *          new entry (which must have the same DN as the provided entry) if
   *          the entry should be added with changes, or {@code null} if the
   *          entry should not be added to the target server (but will still be
   *          removed from the source server).
   */
  ReadOnlyEntry doPreAddProcessing(final ReadOnlyEntry entry);



  /**
   * Performs any processing which may be needed after the provided entry has
   * been added to the target server.
   *
   * @param  entry  A read-only representation of the entry that was added to
   *                the target server.  Note that depending on the algorithm
   *                used to perform the move, the entry may not yet be
   *                accessible in the target server.  Also note that the add may
   *                potentially be reverted if move processing encounters an
   *                error later in its processing.
   */
  void doPostAddProcessing(final ReadOnlyEntry entry);



  /**
   * Performs any processing which may be needed before the specified entry is
   * deleted from the source server.
   *
   * @param  entryDN  The DN of the entry that is to be removed from the
   *                  source server.  Note that depending on the algorithm used
   *                  to perform the move, the entry may already be inaccessible
   *                  in the source server.
   */
  void doPreDeleteProcessing(final DN entryDN);



  /**
   * Performs any processing which may be needed after the specified entry has
   * been deleted from the source server.
   *
   * @param  entryDN  The DN of the entry that has been removed from the source
   *                  server.  Note that the delete may potentially be reverted
   *                  if move processing encounters an error later in its
   *                  processing.
   */
  void doPostDeleteProcessing(final DN entryDN);
}
