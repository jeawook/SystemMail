package com.SystemMail.entity;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Embeddable
public class Email {

    @Column(name = "email")
    private String address;

    protected Email() {/*empty*/}

    protected Email(String address) {
        checkArgument(isNotEmpty(address), "address must be provided");
        checkArgument(
                address.length() >= 4 && address.length() <= 50,
                "address length must be between 4 and 50 characters"
        );
        checkArgument(checkAddress(address), "Invalid email address: " + address);

        this.address = address;
    }

    public static Email of(String address) {
        return new Email(address);
    }

    private static boolean checkAddress(String address) {
        return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", address);
    }

    public String getName() {
        checkArgument(isNotEmpty(address), "address must be provided");
        String[] tokens = address.split("@");
        if (tokens.length == 2) {
            return tokens[0];
        }
        throw new IllegalStateException("Invalid email format: " + address);
    }

    public String getDomain() {
        checkArgument(isNotEmpty(address), "address must be provided");
        String[] tokens = address.split("@");
        if (tokens.length == 2) {
            return tokens[1];
        }
        throw new IllegalStateException("Invalid email format: " + address);
    }

    public String getAddress() {
        checkArgument(isNotEmpty(address), "address must be provided");
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("address", address)
                .toString();
    }

}
