/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2020-2021 Andres Almiray.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jreleaser.gradle.plugin.internal.dsl

import groovy.transform.CompileStatic
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.internal.provider.Providers
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.jreleaser.gradle.plugin.dsl.Glob

import javax.inject.Inject

/**
 *
 * @author Andres Almiray
 * @since 0.1.0
 */
@CompileStatic
class GlobImpl implements Glob {
    String name
    final DirectoryProperty directory
    final Property<String> pattern
    final Property<Boolean> recursive

    @Inject
    GlobImpl(ObjectFactory objects) {
        directory = objects.directoryProperty().convention(Providers.notDefined())
        pattern = objects.property(String).convention(Providers.notDefined())
        recursive = objects.property(Boolean).convention(Providers.notDefined())
    }

    void setDirectory(String path) {
        this.directory.set(new File(path))
    }

    org.jreleaser.model.Glob toModel() {
        org.jreleaser.model.Glob glob = new org.jreleaser.model.Glob()
        if (directory.present) {
            glob.directory = directory.asFile.get().absolutePath
        }
        if (pattern.present) glob.pattern = pattern.get()
        if (recursive.present) glob.recursive = recursive.get()
        glob
    }
}