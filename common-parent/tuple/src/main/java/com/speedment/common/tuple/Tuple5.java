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
package com.speedment.common.tuple;

import java.util.stream.Stream;

/**
 * {@inheritDoc}
 *
 * This {@link Tuple} holds four non-null elements.
 *
 * @author pemi
 * @param <T0> Type of 0:th argument
 * @param <T1> Type of 1:st argument
 * @param <T2> Type of 2:nd argument
 * @param <T3> Type of 3:rd argument
 * @param <T4> Type of 4:th argument
 */
public interface Tuple5<T0, T1, T2, T3, T4> extends Tuple {

    T0 get0();

    T1 get1();

    T2 get2();
    
    T3 get3();
    
    T4 get4();
    
    @Override
    default Stream<Object> stream() {
        return Stream.of(get0(), get1(), get2(), get3(), get4());
    }
    
    @Override
    default int length() {
        return 5;
    }

    @Override
    default Object get(int index) {
        switch (index) {
            case 0 : return get0();
            case 1 : return get1();
            case 2 : return get2();
            case 3 : return get3();
            case 4 : return get4();
            default : throw new IllegalArgumentException(String.format(
                "Index %d is outside bounds of tuple of length %s", index, length()
            ));
        }
    }
}