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

final class TypeSerializerCollectionBuilder implements TypeSerializerCollection.Builder {
    private final SerializerList serializers = new SerializerList();

    @Override
    public TypeSerializerCollection.Builder with(final TypeSerializerCollection that) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> TypeSerializerCollection.Builder put(final TypeToken<T> type, final TypeSerializer<? super T> serializer) {
        this.serializers.add(new RegisteredSerializer(type, serializer));
        return this;
    }

    @Override
    public <T> TypeSerializerCollection.Builder put(final Predicate<TypeToken<T>> type, final TypeSerializer<? super T> serializer) {
        this.serializers.add(new RegisteredSerializer((Predicate) type, serializer));
        return this;
    }

    @Override
    public TypeSerializerCollection build() {
        return new TypeSerializerCollectionImpl(new SerializerList(this.serializers));
    }
}
