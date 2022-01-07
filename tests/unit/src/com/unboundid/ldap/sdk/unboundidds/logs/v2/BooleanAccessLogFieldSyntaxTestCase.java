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



import org.testng.annotations.Test;

import com.unboundid.ldap.sdk.LDAPSDKTestCase;
import com.unboundid.util.ByteStringBuffer;
import com.unboundid.util.StaticUtils;



/**
 * This class provides a set of test cases for the Boolean access log field
 * syntax.
 */
public final class BooleanAccessLogFieldSyntaxTestCase
       extends LDAPSDKTestCase
{
  /**
   * Tests the basic functionality of the syntax.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testSyntax()
         throws Exception
  {
    final BooleanAccessLogFieldSyntax syntax =
         BooleanAccessLogFieldSyntax.getInstance();

    assertNotNull(syntax.getSyntaxName());
    assertEquals(syntax.getSyntaxName(), "boolean");

    assertNotNull(syntax.valueToSanitizedString(true));
    assertEquals(syntax.valueToSanitizedString(true), "true");

    final ByteStringBuffer buffer = new ByteStringBuffer();
    syntax.valueToSanitizedString(false, buffer);
    assertEquals(buffer.toString(), "false");

    assertNotNull(syntax.parseValue("true"));
    assertEquals(syntax.parseValue("true"), Boolean.TRUE);

    assertNotNull(syntax.parseValue("false"));
    assertEquals(syntax.parseValue("false"), Boolean.FALSE);

    try
    {
      syntax.parseValue("{REDACTED}");
      fail("Expected an exception when trying to parse a redacted value.");
    }
    catch (final RedactedValueException e)
    {
      // This was expected.
    }

    try
    {
      syntax.parseValue("{TOKENIZED:1234567890ABCDEF}");
      fail("Expected an exception when trying to parse a tokenized value.");
    }
    catch (final TokenizedValueException e)
    {
      // This was expected
    }

    try
    {
      syntax.parseValue("malformed");
      fail("Expected an exception when trying to parse a malformed value.");
    }
    catch (final LogSyntaxException e)
    {
      // This was expected.
      assertFalse((e instanceof RedactedValueException) ||
           (e instanceof TokenizedValueException));
    }

    assertFalse(syntax.completelyRedactedValueConformsToSyntax());

    assertNotNull(syntax.redactEntireValue());
    assertEquals(syntax.redactEntireValue(), "{REDACTED}");

    assertFalse(syntax.supportsRedactedComponents());

    assertFalse(syntax.valueStringIncludesRedactedComponent("true"));
    assertTrue(syntax.valueStringIncludesRedactedComponent("{REDACTED}"));
    assertTrue(syntax.valueStringIncludesRedactedComponent("a{REDACTED}b"));

    assertFalse(syntax.valueWithRedactedComponentsConformsToSyntax());

    assertNotNull(syntax.redactComponents(true));
    assertEquals(syntax.redactComponents(true), "{REDACTED}");

    assertNotNull(syntax.redactComponents(false));
    assertEquals(syntax.redactComponents(false), "{REDACTED}");

    assertFalse(syntax.valueStringIsCompletelyTokenized("true"));
    assertFalse(syntax.valueStringIsCompletelyTokenized("false"));

    assertFalse(syntax.completelyTokenizedValueConformsToSyntax());

    final byte[] pepper = StaticUtils.randomBytes(8, false);
    assertNotNull(syntax.tokenizeEntireValue(true, pepper));
    assertTrue(syntax.tokenizeEntireValue(true, pepper).startsWith(
         "{TOKENIZED:"));
    assertTrue(syntax.tokenizeEntireValue(true, pepper).endsWith("}"));

    assertFalse(syntax.supportsTokenizedComponents());

    assertFalse(syntax.valueStringIncludesTokenizedComponent("test"));

    assertFalse(syntax.valueWithTokenizedComponentsConformsToSyntax());

    assertNotNull(syntax.tokenizeComponents(true, pepper));
    assertTrue(syntax.tokenizeComponents(true, pepper).
         startsWith("{TOKENIZED:"));
    assertTrue(syntax.tokenizeComponents(true, pepper).endsWith("}"));
  }
}
