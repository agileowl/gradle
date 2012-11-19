/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.tasks.testing.junit.result;

import org.apache.commons.io.IOUtils;
import org.gradle.util.Clock;

import javax.xml.stream.XMLOutputFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import static org.gradle.internal.UncheckedException.throwAsUncheckedException;

/**
 * by Szczepan Faber, created at: 11/13/12
 */
public class NewJUnitXmlReportGenerator {

    public void generate(File testResultsDir, TestResultsProvider testResultsProvider) {
        Clock clock = new Clock();
        SaxJUnitXmlResultWriter saxWriter = new SaxJUnitXmlResultWriter(getHostname(), testResultsProvider, XMLOutputFactory.newFactory());
        Map<String, TestClassResult> results = testResultsProvider.provideResults();
        for (Map.Entry<String, TestClassResult> entry : results.entrySet()) {
            String className = entry.getKey();
            TestClassResult result = entry.getValue();

            Writer output = null;
            try {
                output = new FileWriter(new File(testResultsDir, "TEST-" + className + ".xml"));
                saxWriter.write(className, result, output);
            } catch (IOException e) {
                throw throwAsUncheckedException(e);
            } finally {
                IOUtils.closeQuietly(output);
            }
        }
        System.out.println("Generation of xml results took: " + clock.getTime());
    }

    private static String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }
}