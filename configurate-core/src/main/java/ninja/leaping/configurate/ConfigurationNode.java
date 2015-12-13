/**
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
package ninja.leaping.configurate;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * A node in the configuration tree. This is more or less the main class of configurate, providing the methods to
 * navigate through the configuration tree and get values
 */
public interface ConfigurationNode {
    /**
     * The key for this node.
     * If this node is currently virtual, this method's result may be inaccurate.
     *
     * @return The key for this node
     */
    Object getKey();

    /**
     * The full path from the root to this node.
     *
     * Node implementations may keep a full path for each node, so this method may involve some object churn.
     *
     * @return An array compiled from the keys for each node up the hierarchy
     */
    Object[] getPath();

    /**
     * Returns the current parent for this node.
     * If this node is currently virtual, this method's result may be inaccurate.
     * @return The appropriate parent
     */
    ConfigurationNode getParent();

    /**
     * Return the options that currently apply to this node
     *
     * @return The ConfigurationOptions instance that governs the functionality of this node
     */
    ConfigurationOptions getOptions();

    /**
     * Get the current value associated with this node.
     * If this node has children, this method will recursively unwrap them to construct a List or a Map
     *
     * @return This configuration's current value, or an empty optional if there is none.
     */
    Optional<Object> getValue();

    /**
     * If this node has list values, this function unwraps them and converts them to an appropriate type based on the
     * provided function.
     * If this node has a scalar value, this function treats it as a list with one value
     *
     * @param transformer The transformation function
     * @param <T> The expected type
     * @return An immutable copy of the values contained, or empty if no values are present
     */
    <T> Optional<List<T>> getList(Function<Object, T> transformer);

    /**
     * If this node has list values, this function unwraps them and converts them to an appropriate type based on the
     * provided type.
     * If this node has a scalar value, this function treats it as a list with one value
     *
     * @param type The expected type
     * @param <T> The expected type
     * @return An immutable copy of the values contained, or null if no value present
     */
    <T> Optional<List<T>> getList(TypeToken<T> type) throws ObjectMappingException;

    /**
     * Gets the value typed using the appropriate type conversion from {@link Types}
     *
     * @see #getValue()
     * @return The appropriate type conversion, null if no appropriate value is available
     */
    default Optional<String> getString() {
        return getValue().map(Types::asString);
    }

    /**
     * Gets the value typed using the appropriate type conversion from {@link Types}
     *
     * @see #getValue()
     * @return The appropriate type conversion, 0 if no appropriate value is available
     */
    default Optional<Float> getFloat() {
        return getValue().map(Types::asFloat);
    }

    /**
     * Gets the value typed using the appropriate type conversion from {@link Types}
     *
     * @see #getValue()
     * @return The appropriate type conversion, 0 if no appropriate value is available
     */
    default Optional<Double> getDouble() {
        return getValue().map(Types::asDouble);
    }

    /**
     * Gets the value typed using the appropriate type conversion from {@link Types}
     *
     * @see #getValue()
     * @return The appropriate type conversion, 0 if no appropriate value is available
     */
    default Optional<Integer> getInt() {
        return getValue().map(Types::asInt);
    }

    /**
     * Gets the value typed using the appropriate type conversion from {@link Types}
     *
     * @see #getValue()
     * @return The appropriate type conversion, 0 if no appropriate value is available
     */
    default Optional<Long> getLong() {
        return getValue().map(Types::asLong);
    }

    /**
     * Gets the value typed using the appropriate type conversion from {@link Types}
     *
     * @see #getValue()
     * @return The appropriate type conversion, false if no appropriate value is available
     */
    default Optional<Boolean> getBoolean() {
        return getValue().map(Types::asBoolean);
    }

    /**
     * Set this node's value to the given value.
     * If the provided value is a {@link java.util.Collection} or a {@link java.util.Map}, it will be unwrapped into
     * the appropriate configuration node structure
     *
     * @param value The value to set
     * @return this
     */
    ConfigurationNode setValue(Object value);

    /**
     * Get the current value associated with this node.
     * If this node has children, this method will recursively unwrap them to construct a List or a Map.
     * This method will also perform deserialization using the appropriate TypeSerializer for the given type, or casting if no type serializer is found.
     *
     * @param type The type to deserialize to
     * @param <T> the type to get
     * @return the value if present and of the proper type, else {@link Optional#empty()}
     */
    <T> Optional<T> getValue(TypeToken<T> type) throws ObjectMappingException;

    /**
     * Set this node's value to the given value.
     * If the provided value is a {@link java.util.Collection} or a {@link java.util.Map}, it will be unwrapped into
     * the appropriate configuration node structure.
     * This method will also perform serialization using the appropriate TypeSerializer for the given type, or casting if no type serializer is found.
     *
     * @param type The type to use for serialization type information
     * @param value The value to set
     * @param <T> The type to serialize to
     * @return this
     */
    <T> ConfigurationNode setValue(TypeToken<T> type, T value) throws ObjectMappingException;

    /**
     * Set all the values from the given node that are not present in this node
     * to their values in the provided node.
     *
     * Map keys will be merged. Lists and scalar values will be replaced.
     *
     * @param other The node to merge values from
     * @return this
     */
    ConfigurationNode mergeValuesFrom(ConfigurationNode other);

    /**
     * @return if this node has children in the form of a list
     */
    boolean hasListChildren();

    /**
     * @return if this node has children in the form of a map
     */
    boolean hasMapChildren();

    /**
     * Return an immutable copy of the list of children this node is aware of
     *
     * @return The children currently attached to this node
     */
    List<? extends ConfigurationNode> getChildrenList();

    /**
     * Return an immutable copy of the mapping from key to node of every child this node is aware of
     *
     * @return Child nodes currently attached
     */
    Map<Object, ? extends ConfigurationNode> getChildrenMap();

    /**
     * Removes a direct child of this node
     *
     * @param key The key of the node to remove
     * @return if an actual node was removed
     */
    boolean removeChild(Object key);

    /**
     * @return a new child created as the next entry in the list when it is attached
     */
    ConfigurationNode getAppendedNode();


    /**
     * Gets the node at the given (relative) path, possibly traversing multiple levels of nodes
     *
     * @param path The path to fetch the node at
     * @return The node at the given path, possibly virtual
     */
    ConfigurationNode getNode(Object... path);

    /**
     * Whether this node does not currently exist in the configuration structure.
     *
     * @return true if this node is not attached (this occurs primarily when the node has no set value)
     */
    boolean isVirtual();
}
