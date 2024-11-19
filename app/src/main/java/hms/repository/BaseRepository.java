package hms.repository;

import java.io.Serializable;

/**
 * Represents a base repository that provides a common structure for all repository classes. This
 * class implements the {@link Serializable} interface, allowing instances to be serialized.
 */
public abstract class BaseRepository implements Serializable {

    /**
     * The serialization version unique identifier for this class. This identifier is used during
     * the deserialization process to verify that the sender and receiver of a serialized object
     * have loaded classes for that object that are compatible with respect to serialization.
     */
    private static final long serialVersionUID = 1L;
}
