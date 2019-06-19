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

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class DefaultTypeSerializerCollection {
  static TypeSerializerCollection INSTANCE = TypeSerializerCollection.builder()
      .put(URI.class, new URISerializer())
      .put(URL.class, new URLSerializer())
      .put(UUID.class, new UUIDSerializer())
      .put(input -> input.getRawType().isAnnotationPresent(ConfigSerializable.class), new AnnotatedObjectSerializer())
      .put(NumberSerializer.getPredicate(), new NumberSerializer())
      .put(String.class, new StringSerializer())
      .put(Boolean.class, new BooleanSerializer())
      .put(new TypeToken<Map<?, ?>>() {}, new MapSerializer())
      .put(new TypeToken<List<?>>() {}, new ListSerializer())
      .put(new TypeToken<Enum<?>>() {}, new EnumValueSerializer())
      .put(Pattern.class, new PatternSerializer())
      .build();
}
