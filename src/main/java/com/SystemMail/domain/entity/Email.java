package com.SystemMail.domain.entity;


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

    private String name;

    protected Email() {/*empty*/}

    protected Email(String address, String name) {
        checkArgument(isNotEmpty(address), "address must be provided");
        checkArgument(
                address.length() >= 4 && address.length() <= 50,
                "address length must be between 4 and 50 characters"
        );
        checkArgument(checkAddress(address), "Invalid email address: " + address);
        this.name = name;
        this.address = address;
    }

    public static Email of(String address, String name) {
        return new Email(address, name);
    }

    private static boolean checkAddress(String address) {
        return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", address);
    }

    public String getId() {
        checkArgument(isNotEmpty(address), "address must be provided");
        String[] tokens = address.split("@");
        if (tokens.length == 2) {
            return tokens[0];
        }
        throw new IllegalStateException("Invalid email format: " + address);
    }

    public String getName() {
        checkArgument(isNotEmpty(name));
        return name;
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
        return "\""+name+"\"<"+address+">";
    }
}
