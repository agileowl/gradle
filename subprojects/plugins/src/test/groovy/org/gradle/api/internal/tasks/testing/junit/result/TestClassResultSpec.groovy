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

package org.gradle.api.internal.tasks.testing.junit.result

import org.gradle.api.internal.tasks.testing.results.DefaultTestResult
import org.gradle.api.tasks.testing.TestResult
import spock.lang.Specification

import static java.util.Arrays.asList

/**
 * by Szczepan Faber, created at: 11/19/12
 */
class TestClassResultSpec extends Specification {

    def "provides test class result information"() {
        def result = new TestClassResult(100)

        when:
        result.add(new TestMethodResult("foo",   new DefaultTestResult(TestResult.ResultType.SUCCESS, 100, 200, 1, 1, 0, asList())))
        result.add(new TestMethodResult("fail",  new DefaultTestResult(TestResult.ResultType.FAILURE, 200, 300, 1, 0, 1, asList(new RuntimeException("bar")))))
        result.add(new TestMethodResult("fail2", new DefaultTestResult(TestResult.ResultType.FAILURE, 300, 450, 1, 0, 1, asList(new RuntimeException("foo")))))

        then:
        result.failuresCount == 2
        result.testsCount == 3
        result.duration == 350
    }
}
