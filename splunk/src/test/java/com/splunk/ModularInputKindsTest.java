/*
 * Copyright 2012 Splunk, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"): you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.splunk;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

public class ModularInputKindsTest extends SDKTestCase {
    private ResourceCollection<ModularInputKind> inputKinds;

    public void checkModularInputKind(ModularInputKind m) {
        if (service.versionIsEarlierThan("5.0") || !hasTestData()) {
            return;
        } else {
            installApplicationFromTestData("modular-inputs");
            inputKinds = service.getModularInputKinds();
        }

        if (m.getName().equals("test1")) {
            Assert.assertEquals("Test \"Input\" - 1", m.getTitle());
            Assert.assertEquals("xml", m.getStreamingMode());
        } else if (m.getName().equals("test2")) {
            Assert.assertEquals("test2", m.getTitle());
            Assert.assertEquals("simple", m.getStreamingMode());
        }

    }

    @Ignore("apps/appinstall endpoint is removed in version 8.1.0")
    @Test
    public void testListInputKinds() {
        if (service.versionIsEarlierThan("5.0") || !hasTestData()) {
            return;
        } else {
            installApplicationFromTestData("modular-inputs");
            inputKinds = service.getModularInputKinds();
        }

        for (ModularInputKind kind : inputKinds.values()) {
            checkModularInputKind(kind);
        }
    }

    @Ignore("apps/appinstall endpoint is removed in version 8.1.0")
    @Test
    public void testInputByName() {
        if (service.versionIsEarlierThan("5.0") || !hasTestData()) {
            return;
        } else {
            installApplicationFromTestData("modular-inputs");
            inputKinds = service.getModularInputKinds();
        }
        ModularInputKind m;
        m = inputKinds.get("test1");
        checkModularInputKind(m);
        m = inputKinds.get("test2");
        checkModularInputKind(m);
    }

    @Ignore("apps/appinstall endpoint is removed in version 8.1.0")
    @Test
    public void testNonexistantArg() {
        if (service.versionIsEarlierThan("5.0") || !hasTestData()) {
            return;
        } else {
            installApplicationFromTestData("modular-inputs");
            inputKinds = service.getModularInputKinds();
        }
        ModularInputKind test1 = inputKinds.get("test1");
        Assert.assertFalse(test1.hasArgument("nonexistant_argument"));
        Assert.assertNull(test1.getArgument("nonexistant_argument"));
    }

    @Ignore("apps/appinstall endpoint is removed in version 8.1.0")
    @Test
    public void testInputKindDescriptionAndTitle() {
        if (service.versionIsEarlierThan("5.0") || !hasTestData()) {
            return;
        } else {
            installApplicationFromTestData("modular-inputs");
            inputKinds = service.getModularInputKinds();
        }
        ModularInputKind test1 = inputKinds.get("test1");
        String expectedDescription1 = "A description of test input 1 with special characters: //;!*%";
        Assert.assertEquals(expectedDescription1, test1.getDescription());
        Assert.assertEquals("Test \"Input\" - 1", test1.getTitle());

        ModularInputKind test2 = inputKinds.get("test2");
        Assert.assertEquals(null, test2.getDescription());
        Assert.assertEquals("test2", test2.getTitle());
    }

    @Ignore("apps/appinstall endpoint is removed in version 8.1.0")
    @Test
    public void testArgDescription() {
        if (service.versionIsEarlierThan("5.0") || !hasTestData()) {
            return;
        } else {
            installApplicationFromTestData("modular-inputs");
            inputKinds = service.getModularInputKinds();
        }

        ModularInputKind test1 = inputKinds.get("test1");

        ModularInputKindArgument arg;

        Map<String,String> expectedValues = new HashMap<String,String>();
        expectedValues.put("key_id", "The key of the system");
        expectedValues.put("no_description", null);
        expectedValues.put("empty_description", null);

        for (String key : expectedValues.keySet()) {
            Assert.assertTrue(test1.hasArgument(key));
            arg = test1.getArgument(key);
            String expectedDescription = expectedValues.get(key);
            String foundDescription = arg.getDescription();
            Assert.assertEquals(expectedDescription, foundDescription);
        }
    }

    @Ignore("apps/appinstall endpoint is removed in version 8.1.0")
    @Test
    public void testArgDataType() {
        if (service.versionIsEarlierThan("5.0") || !hasTestData()) {
            return;
        } else {
            installApplicationFromTestData("modular-inputs");
            inputKinds = service.getModularInputKinds();
        }

        ModularInputKind test1 = inputKinds.get("test1");

        ModularInputKindArgument arg;

        Map<String,ModularInputKindArgument.Type> expectedValues =
                new HashMap<String,ModularInputKindArgument.Type>();
        expectedValues.put("number_field", ModularInputKindArgument.Type.NUMBER);
        expectedValues.put("boolean_field", ModularInputKindArgument.Type.BOOLEAN);
        expectedValues.put("string_field", ModularInputKindArgument.Type.STRING);

        for (String key : expectedValues.keySet()) {
            Assert.assertTrue(test1.hasArgument(key));
            arg = test1.getArgument(key);
            Assert.assertEquals(expectedValues.get(key), arg.getType());
        }
    }

    @Ignore("apps/appinstall endpoint is removed in version 8.1.0")
    @Test
    public void testRequiredOnCreate() {
        if (service.versionIsEarlierThan("5.0") || !hasTestData()) {
            return;
        } else {
            installApplicationFromTestData("modular-inputs");
            inputKinds = service.getModularInputKinds();
        }

        ModularInputKind test1 = inputKinds.get("test1");

        ModularInputKindArgument arg;

        Map<String,Boolean> expectedValues = new HashMap<String,Boolean>();
        expectedValues.put("required_on_create", true);
        expectedValues.put("not_required_on_create", false);

        for (String key : expectedValues.keySet()) {
            Assert.assertTrue(test1.hasArgument(key));
            arg = test1.getArgument(key);
            Assert.assertEquals((boolean) expectedValues.get(key), arg.getRequiredOnCreate());
        }
    }

    @Ignore("apps/appinstall endpoint is removed in version 8.1.0")
    @Test
    public void testRequiredOnEdit() {
        if (service.versionIsEarlierThan("5.0") || !hasTestData()) {
            return;
        } else {
            installApplicationFromTestData("modular-inputs");
            inputKinds = service.getModularInputKinds();
        }

        ModularInputKind test1 = inputKinds.get("test1");

        ModularInputKindArgument arg;

        Map<String,Boolean> expectedValues = new HashMap<String,Boolean>();
        expectedValues.put("arg_required_on_edit", true);
        expectedValues.put("not_required_on_edit", false);

        for (String key : expectedValues.keySet()) {
            Assert.assertTrue(test1.hasArgument(key));
            arg = test1.getArgument(key);
            Assert.assertEquals((boolean) expectedValues.get(key), arg.getRequiredOnEdit());
        }
    }

    @Ignore("apps/appinstall endpoint is removed in version 8.1.0")
    @Test
    public void testGetArguments() {
        if (service.versionIsEarlierThan("5.0") || !hasTestData()) {
            return;
        } else {
            installApplicationFromTestData("modular-inputs");
            inputKinds = service.getModularInputKinds();
        }

        ModularInputKind test1 = inputKinds.get("test1");
        Map<String, ModularInputKindArgument> args = test1.getArguments();

        Set<String> expectedKeys = new HashSet<String>();
        expectedKeys.add("name");
        expectedKeys.add("resname");
        expectedKeys.add("key_id");
        expectedKeys.add("no_description");
        expectedKeys.add("empty_description");
        expectedKeys.add("arg_required_on_edit");
        expectedKeys.add("not_required_on_edit");
        expectedKeys.add("required_on_create");
        expectedKeys.add("not_required_on_create");
        expectedKeys.add("number_field");
        expectedKeys.add("string_field");
        expectedKeys.add("boolean_field");

        Assert.assertEquals(expectedKeys, args.keySet());
    }
}
