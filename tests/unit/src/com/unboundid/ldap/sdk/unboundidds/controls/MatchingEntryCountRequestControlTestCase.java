/*
 * Copyright 2014-2022 Ping Identity Corporation
 * All Rights Reserved.
 */
/*
 * Copyright 2014-2022 Ping Identity Corporation
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
 * Copyright (C) 2014-2022 Ping Identity Corporation
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



import org.testng.annotations.Test;

import com.unboundid.asn1.ASN1Integer;
import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.asn1.ASN1Sequence;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.sdk.Control;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSDKTestCase;
import com.unboundid.ldap.sdk.RootDSE;



/**
 * This class provides a set of test cases for the matching entry count request
 * control.
 */
public final class MatchingEntryCountRequestControlTestCase
       extends LDAPSDKTestCase
{
  /**
   * Tests the behavior of a matching entry count request control created with
   * the default constructor.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testDefaultConstructor()
         throws Exception
  {
    MatchingEntryCountRequestControl c = new MatchingEntryCountRequestControl();
    c = new MatchingEntryCountRequestControl(c);

    assertNotNull(c.getOID());
    assertEquals(c.getOID(), "1.3.6.1.4.1.30221.2.5.36");
    assertEquals(c.getOID(),
         MatchingEntryCountRequestControl.MATCHING_ENTRY_COUNT_REQUEST_OID);

    assertTrue(c.isCritical());

    assertNotNull(c.getValue());

    assertEquals(c.getMaxCandidatesToExamine(), 0);

    assertFalse(c.alwaysExamineCandidates());

    assertFalse(c.processSearchIfUnindexed());

    assertFalse(c.skipResolvingExplodedIndexes());

    assertNull(c.getFastShortCircuitThreshold());

    assertNull(c.getSlowShortCircuitThreshold());

    assertFalse(c.includeExtendedResponseData());

    assertFalse(c.includeDebugInfo());

    assertNotNull(c.getControlName());

    assertNotNull(c.toString());
  }



  /**
   * Tests the behavior of a matching entry count request control created with
   * all non-default settings.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testNonDefaultConstructor()
         throws Exception
  {
    MatchingEntryCountRequestControl c =
         new MatchingEntryCountRequestControl(false, 123, true, true, true, 5L,
              20L, true);
    c = new MatchingEntryCountRequestControl(c);

    assertNotNull(c.getOID());
    assertEquals(c.getOID(), "1.3.6.1.4.1.30221.2.5.36");
    assertEquals(c.getOID(),
         MatchingEntryCountRequestControl.MATCHING_ENTRY_COUNT_REQUEST_OID);

    assertFalse(c.isCritical());

    assertNotNull(c.getValue());

    assertEquals(c.getMaxCandidatesToExamine(), 123);

    assertTrue(c.alwaysExamineCandidates());

    assertTrue(c.processSearchIfUnindexed());

    assertTrue(c.skipResolvingExplodedIndexes());

    assertNotNull(c.getFastShortCircuitThreshold());
    assertEquals(c.getFastShortCircuitThreshold().longValue(), 5L);

    assertNotNull(c.getSlowShortCircuitThreshold());
    assertEquals(c.getSlowShortCircuitThreshold().longValue(), 20L);

    assertFalse(c.includeExtendedResponseData());

    assertTrue(c.includeDebugInfo());

    assertNotNull(c.getControlName());

    assertNotNull(c.toString());
  }



  /**
   * Tests the behavior of a matching entry count request control when created
   * from properties with all default settings.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testDefaultProperties()
         throws Exception
  {
    final MatchingEntryCountRequestControlProperties properties =
         new MatchingEntryCountRequestControlProperties();

    MatchingEntryCountRequestControl c =
         new MatchingEntryCountRequestControl(true, properties);
    c = new MatchingEntryCountRequestControl(c);

    assertNotNull(c.getOID());
    assertEquals(c.getOID(), "1.3.6.1.4.1.30221.2.5.36");
    assertEquals(c.getOID(),
         MatchingEntryCountRequestControl.MATCHING_ENTRY_COUNT_REQUEST_OID);

    assertTrue(c.isCritical());

    assertNotNull(c.getValue());

    assertEquals(c.getMaxCandidatesToExamine(), 0);

    assertFalse(c.alwaysExamineCandidates());

    assertFalse(c.processSearchIfUnindexed());

    assertFalse(c.skipResolvingExplodedIndexes());

    assertNull(c.getFastShortCircuitThreshold());

    assertNull(c.getSlowShortCircuitThreshold());

    assertFalse(c.includeExtendedResponseData());

    assertFalse(c.includeDebugInfo());

    assertNotNull(c.getControlName());

    assertNotNull(c.toString());
  }



  /**
   * Tests the behavior of a matching entry count request control when created
   * from properties with all non-default settings.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testNonDefaultProperties()
         throws Exception
  {
    final MatchingEntryCountRequestControlProperties properties =
         new MatchingEntryCountRequestControlProperties();
    properties.setMaxCandidatesToExamine(123);
    properties.setAlwaysExamineCandidates(true);
    properties.setProcessSearchIfUnindexed(true);
    properties.setSkipResolvingExplodedIndexes(true);
    properties.setFastShortCircuitThreshold(5L);
    properties.setSlowShortCircuitThreshold(20L);
    properties.setIncludeExtendedResponseData(true);
    properties.setIncludeDebugInfo(true);

    MatchingEntryCountRequestControl c =
         new MatchingEntryCountRequestControl(false, properties);
    c = new MatchingEntryCountRequestControl(c);

    assertNotNull(c.getOID());
    assertEquals(c.getOID(), "1.3.6.1.4.1.30221.2.5.36");
    assertEquals(c.getOID(),
         MatchingEntryCountRequestControl.MATCHING_ENTRY_COUNT_REQUEST_OID);

    assertFalse(c.isCritical());

    assertNotNull(c.getValue());

    assertEquals(c.getMaxCandidatesToExamine(), 123);

    assertTrue(c.alwaysExamineCandidates());

    assertTrue(c.processSearchIfUnindexed());

    assertTrue(c.skipResolvingExplodedIndexes());

    assertNotNull(c.getFastShortCircuitThreshold());
    assertEquals(c.getFastShortCircuitThreshold().longValue(), 5L);

    assertNotNull(c.getSlowShortCircuitThreshold());
    assertEquals(c.getSlowShortCircuitThreshold().longValue(), 20L);

    assertTrue(c.includeExtendedResponseData());

    assertTrue(c.includeDebugInfo());

    assertNotNull(c.getControlName());

    assertNotNull(c.toString());
  }



  /**
   * Tests the behavior when trying to decode a control that does not have a
   * value.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeMissingValue()
         throws Exception
  {
    new MatchingEntryCountRequestControl(
         new Control("1.3.6.1.4.1.30221.2.5.36", true, null));
  }



  /**
   * Tests the behavior when trying to decode a control whose value is not a
   * sequence.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeValueNotSequence()
         throws Exception
  {
    new MatchingEntryCountRequestControl(
         new Control("1.3.6.1.4.1.30221.2.5.36", true,
              new ASN1OctetString("foo")));
  }



  /**
   * Tests the behavior when trying to decode a control whose value sequence
   * includes a max candidates to examine element with a negative value.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeValueSequenceNegativeMaxCandidatesToExamine()
         throws Exception
  {
    final ASN1Sequence valueSequence = new ASN1Sequence(
         new ASN1Integer((byte) 0x80, -1));

    new MatchingEntryCountRequestControl(
         new Control("1.3.6.1.4.1.30221.2.5.36", true,
              new ASN1OctetString(valueSequence.encode())));
  }



  /**
   * Provides test coverage for the methods used to determine whether a server
   * supports including extended response data in the matching entry count
   * response control.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testServerSupportsExtendedResponseData()
         throws Exception
  {
    final RootDSE rootDSEWithoutSupport;
    final InMemoryDirectoryServer ds = getTestDS();
    try (LDAPConnection conn = ds.getConnection())
    {
      rootDSEWithoutSupport = conn.getRootDSE();
      assertNotNull(rootDSEWithoutSupport);
      assertFalse(rootDSEWithoutSupport.supportsFeature(
           "1.3.6.1.4.1.30221.2.12.7"));

      assertFalse(MatchingEntryCountRequestControl.
           serverSupportsExtendedResponseData(conn));
      assertFalse(MatchingEntryCountRequestControl.
           serverSupportsExtendedResponseData(rootDSEWithoutSupport));
    }


    final Entry rootDSEEntryWithSupport = rootDSEWithoutSupport.duplicate();
    rootDSEEntryWithSupport.addAttribute("supportedFeatures",
         "1.3.6.1.4.1.30221.2.12.7");
    final RootDSE rootDSEWithSupport = new RootDSE(rootDSEEntryWithSupport);
    assertTrue(MatchingEntryCountRequestControl.
         serverSupportsExtendedResponseData(rootDSEWithSupport));
  }
}
