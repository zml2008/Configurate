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

import java.util.function.Predicate;

/**
 * A calculated collection of {@link TypeSerializer}s
 */
public interface TypeSerializerCollection {
    static Builder builder() {
      return new TypeSerializerCollectionBuilder();
    }

    static TypeSerializerCollection defaults() {
      return DefaultTypeSerializerCollection.INSTANCE;
    }

    <T> TypeSerializer<T> get(final TypeToken<T> type);

    interface Builder {
        Builder with(final TypeSerializerCollection that);

        default <T> Builder put(final Class<T> type, final TypeSerializer<? super T> serializer) {
            return this.put(TypeToken.of(type), serializer);
        }

        <T> Builder put(final TypeToken<T> type, final TypeSerializer<? super T> serializer);

        <T> Builder put(final Predicate<TypeToken<T>> type, final TypeSerializer<? super T> serializer);

        TypeSerializerCollection build();
    }
}
