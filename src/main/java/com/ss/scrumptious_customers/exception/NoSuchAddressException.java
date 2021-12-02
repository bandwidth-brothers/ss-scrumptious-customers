package com.ss.scrumptious_customers.exception;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * To be thrown when a {@link com.ss.scrumptious.common_entities.entity.Address} cannot be found.
 *
 * <p>Contains the offending ID that can be retrieved with {@link #getAddressId()}.
 */
public class NoSuchAddressException extends NoSuchElementException {

    private final Long addressId;

    public NoSuchAddressException(Long id) {
        super("No address record found for id=" + id);
        this.addressId = id;
    }

    public Optional<Long> getAddressId() {
        return Optional.ofNullable(addressId);
    }

}
