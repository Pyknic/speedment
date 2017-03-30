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
package com.speedment.runtime.core.internal.manager;

import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.ManagerConfigurator;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> Entity type
 *
 * @since 3.0.1
 */
public class ManagerConfiguratorImpl<ENTITY> implements ManagerConfigurator<ENTITY> {

    private final StreamSupplierComponent streamSupplierComponent;
    private final Manager<ENTITY> manager;
    //
    private ParallelStrategy parallelStrategy;

    public ManagerConfiguratorImpl(StreamSupplierComponent streamSupplierComponent, Manager<ENTITY> manager) {
        this.streamSupplierComponent = requireNonNull(streamSupplierComponent);
        this.manager = requireNonNull(manager);
        this.parallelStrategy = ParallelStrategy.computeIntensityDefault();
    }

    @Override
    public ManagerConfigurator<ENTITY> withParallelStrategy(ParallelStrategy parallelStrategy) {
        this.parallelStrategy = requireNonNull(parallelStrategy);
        return this;
    }

    @Override
    public Manager<ENTITY> build() {
        return new ConfiguredManager<>(streamSupplierComponent, manager, parallelStrategy);
    }

}
