/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.jtalks.common.model.permissions;

import com.google.common.collect.Lists;
import ru.javatalks.utils.general.Assert;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * There are the restrictions that related to common entities.
 *
 * @author Ancient_Mariner
 */
public enum ProfilePermission implements JtalksPermission {
    /**
     * The ability of user group or user to send private messages.
     */
    SEND_PRIVATE_MESSAGES("1110", "SEND_PRIVATE_MESSAGES"),
    /**
     * The ability of user group or user to create frequently asked questions
     */
    CREATE_FORUM_FAQ("10100", "CREATE_FORUM_FAQ"),
    /**
     * The ability of user group or user to edit user profile.
     */
    EDIT_OWN_PROFILE("1111", "EDIT_OWN_PROFILE"),
    /**
     * The ability of user group or user to edit profiles of other users.
     */
    EDIT_OTHERS_PROFILE("10111", "EDIT_OTHERS_PROFILE");

    private final String name;
    private final int mask;

    /**
     * Constructs the whole object without symbol.
     *
     * @param mask a bit mask that represents the permission, can be negative only for restrictions (look at the class
     *             description). The integer representation of it is saved to the ACL tables of Spring Security.
     * @param name a textual representation of the permission (usually the same as the constant name), though the
     *             restriction usually starts with the 'RESTRICTION_' word
     */
    ProfilePermission(int mask, @Nonnull String name) {
        this.mask = mask;
        throwIfNameNotValid(name);
        this.name = name;
    }

    /**
     * Takes a string bit mask.
     *
     * @param mask a bit mask that represents the permission. It's parsed into integer and saved into the ACL tables of
     *             Spring Security.
     * @param name a textual representation of the permission (usually the same as the constant name)
     * @throws NumberFormatException look at {@link Integer#parseInt(String, int)} for details on this as this method is
     *                               used underneath
     * @see BranchPermission#BranchPermission(int, String)
     * @see org.springframework.security.acls.domain.BasePermission
     */
    ProfilePermission(@Nonnull String mask, @Nonnull String name) {
        throwIfNameNotValid(name);
        this.mask = Integer.parseInt(mask, 2);
        this.name = name;
    }

    /**
     * Gets the human readable textual representation of the restriction(usually the same as the constant name).
     *
     * @return the human readable textual representation of the restriction (usually the same as the constant name)
     */
    @Override
    public String getName() {
        return name;
    }

    private void throwIfNameNotValid(String name) {
        Assert.throwIfNull(name, "The name can't be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMask() {
        return mask;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPattern() {
        return null;
    }

    public static ProfilePermission findByMask(int mask) {
        for (ProfilePermission nextPermission : values()) {
            if (mask == nextPermission.getMask()) {
                return nextPermission;
            }
        }
        return null;
    }

    public static List<ProfilePermission> getAllAsList() {
        return Lists.newArrayList(values());
    }
}
