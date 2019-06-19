/*
 * Configurate
 * Copyright (C) zml and Configurate contributors
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
package org.spongepowered.configurate.objectmapping.serialize;

import com.google.common.reflect.TypeToken;

import java.util.ArrayList;
import java.util.function.Function;

final class SerializerList extends ArrayList<RegisteredSerializer> implements Function<TypeToken<?>, TypeSerializer<?>> {
    SerializerList() {
    }

    SerializerList(final SerializerList that) {
        super(that);
    }

    @Override
    public TypeSerializer<?> apply(final TypeToken<?> type) {
        for (final RegisteredSerializer serializer : this) {
            if (serializer.predicate.test(type)) {
                return serializer.serializer;
            }
        }
        return null;
    }
}
