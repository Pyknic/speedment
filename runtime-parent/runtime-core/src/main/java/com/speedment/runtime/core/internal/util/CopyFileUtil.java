/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.core.internal.util;

import com.speedment.runtime.core.exception.SpeedmentException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.speedment.runtime.core.util.StaticClassUtil.instanceNotAllowed;

/**
 *
 * @author Emil Forslund
 */
public final class CopyFileUtil {

    public static void generate(String srcFolder, String destFolder, String className) {
        final String destFilename = destFolder + className;

        final Path writePath = Paths.get(destFilename);
        try {
            try {
                writePath.getParent().toFile().mkdirs();
            } catch (SecurityException se) {
                throw new SpeedmentException("Unable to create directory " + writePath.toString(), se);
            }
            Files.copy(CopyFileUtil.class.getResourceAsStream(className),
                writePath,
                StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException ex) {
            throw new SpeedmentException(
                "Could not copy file.", ex
            );
        }
    }

    private CopyFileUtil() {
        instanceNotAllowed(CopyFileUtil.class);
    }
}
