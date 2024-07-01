package com.codewithharsh.mycontacts.feature_contact.domain.util

sealed class ContactOrder(
    val orderType: OrderType
) {
    class OrderByFirstName(orderType: OrderType): ContactOrder(orderType)
    class OrderByLastName(orderType: OrderType): ContactOrder(orderType)
}