/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.api.internal.externalresource;

import org.gradle.api.internal.artifacts.repositories.transport.http.HttpResourceCollection;
import org.gradle.util.hash.HashValue;

public class CachedExternalResourceAdapter extends LocalFileStandInExternalResource {
    private final CachedExternalResource cachedExternalResource;

    public CachedExternalResourceAdapter(String source, CachedExternalResource cachedExternalResource, HttpResourceCollection resourceCollection) {
        super(source, cachedExternalResource.getCachedFile(), resourceCollection);
        this.cachedExternalResource = cachedExternalResource;
    }

    @Override
    public String toString() {
        return "CachedResource: " + cachedExternalResource.getCachedFile() + " for " + getName();
    }

    public long getLastModified() {
        return cachedExternalResource.getExternalLastModifiedAsTimestamp();
    }

    public long getContentLength() {
        return cachedExternalResource.getContentLength();
    }

    @Override
    protected HashValue getLocalFileSha1() {
        return cachedExternalResource.getSha1();
    }
}