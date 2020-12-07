package com.bergerkiller.bukkit.tc.properties.api;

import com.bergerkiller.bukkit.tc.Localization;

/**
 * The result of trying to parse a value of a property
 */
public class PropertyParseResult<T> {
    private final IProperty<T> property;
    private final T value;
    private final Reason reason;
    private final String message;

    private PropertyParseResult(IProperty<T> property, T value, Reason reason, String message) {
        this.property = property;
        this.value = value;
        this.reason = reason;
        this.message = message;
    }

    /**
     * Gets whether parsing the property was successful
     * 
     * @return True if successful
     */
    public boolean isSuccessful() {
        return this.reason == Reason.NONE;
    }

    /**
     * Gets the reason why parsing the property failed
     * 
     * @return fail reason
     */
    public Reason getReason() {
        return this.reason;
    }

    /**
     * Gets a human-readable localized message explaining why
     * parsing failed. If successful, returns an empty String.
     * 
     * @return fail reason message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets the property that was parsed. Returns null if
     * {@link #getReason()} is {@link Reason#PROPERTY_NOT_FOUND}.
     * 
     * @return property that was parsed
     */
    public IProperty<T> getProperty() {
        return this.property;
    }

    /**
     * Gets the value that was parsed. Returns null if
     * {@link #isSuccessful()} is false, or if a null
     * value was the result.
     * 
     * @return parsed value
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Creates a new property parse result for when a property by
     * a name could not be found
     * 
     * @param <T> Type of property value
     * @param name Name of the property that could not be found
     * @return new property parse result
     */
    public static <T> PropertyParseResult<T> failPropertyNotFound(String name) {
        return new PropertyParseResult<T>(null, null, Reason.PROPERTY_NOT_FOUND,
                Localization.PROPERTY_NOTFOUND.get(name));
    }

    /**
     * Creates a new property parse result for when the provided
     * input is invalid
     * 
     * @param <T> Type of property value
     * @param property The property that was parsed
     * @param message The message accompanying the invalid input failure
     * @return new property parse result
     */
    public static <T> PropertyParseResult<T> failInvalidInput(IProperty<T> property, String message) {
        return new PropertyParseResult<T>(property, null, Reason.INVALID_INPUT, message);
    }

    /**
     * Creates a new property parse result for when an error occurs
     * while parsing
     * 
     * @param <T> Type of property value
     * @param property The property that was parsed
     * @param name Name of the property that could not be found
     * @param input The input text that was parsed that caused the error
     * @return new property parse result
     */
    public static <T> PropertyParseResult<T> failError(IProperty<T> property, String name, String input) {
        return new PropertyParseResult<T>(property, null, Reason.ERROR,
                Localization.PROPERTY_ERROR.get(name, input));
    }

    /**
     * Creates a new property parse result that is successful
     * 
     * @param <T> Type of property value
     * @param property Property that was parsed
     * @param value Value result
     * @return new property parse result
     */
    public static <T> PropertyParseResult<T> success(IProperty<T> property, T value) {
        return new PropertyParseResult<T>(property, value, Reason.NONE, "");
    }

    /**
     * Reason for a property parse result failing.
     * Is {@link #NONE} when successful.
     */
    public static enum Reason {
        /** No fail reason, parsing was successful */
        NONE,
        /** Property by this name was not found */
        PROPERTY_NOT_FOUND,
        /** Input value is invalid and cannot be parsed to a value for the property */
        INVALID_INPUT,
        /** An error occurred while parsing the value */
        ERROR
    }
}
