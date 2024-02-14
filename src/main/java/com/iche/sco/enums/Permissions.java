package com.iche.sco.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permissions {
    USER_BUY("User buys on system","This allows user to buy on the system"),
    USER_SUBSCRIBE("User subscribe to you","This allow user to subscribe"),
    USER_PAYMENT("User be able to make payment","This allows users make payment"),
    SUPER_ADMIN_DELETE_USER("Super Admin Delete User","This allows the super admin to delete users account"),
    SUPER_ADMIN_BLOCK_USER("Super Admin Block User","This allows the super admin to block users account"),
    SUPER_ADMIN_UNBLOCK_USER("Super Admin Unblock User","This allows the super admin to unblock users account"),
    SUPER_ADMIN_DEACTIVATE_USER("Super Admin Deactivate User","This allows the super admin to deactivate users account"),
    MERCHANT_ADMIN_CREATE_ROLE("Merchant Admin Create Role", "This allows the merchant admin to create staff roles"),
    MERCHANT_ADMIN_UPDATE_ROLE("Merchant Admin Update Role", "This allows the merchant admin to update staff roles"),
    MERCHANT_ADMIN_ASSIGN_PERMISSIONS("Merchant Admin Assign Permissions", "This allows the merchant admin to assign permission to existing roles"),
    MERCHANT_ADMIN_CREATE_STAFF("Merchant Admin Create Staff", "This allows the merchMerchant loan accessant admin to create staff"),
    MERCHANT_HIGHER_PAYMENT("Merchant payment ranger", "This allows the merchant make make multiple transaction in higher rate"),
    MERCHANT_HIGHER_RECEIVE("Merchant higher amount", "This allows the merchant admin to receive more funds"),
    MERCHANT_LOAN("Merchant loan access", "This allows the merchant to access more loan"),
    MANAGE_HR_RECORDS("Manage HR Records", "This allow the user to manage hr records in the merchant organization"),
    USER_COMPLAIN_REQUEST("User can make complains", "This allows the users to email their complains");
    private final String name;
    private final String description;

}
